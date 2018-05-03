package cn.com.sparknet.nclk.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.com.sparknet.nclk.config.Config;
import cn.com.sparknet.nclk.dao.LoginDao;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.service.LoginService;
import cn.com.sparknet.nclk.util.MD5Util;

/**
 * 系统登录
 * @author chenxy
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	@Resource
    private LoginDao loginDao;
	
	/**初始化时获取是否进行错误次数校验**/
	private long errorCount=0;
	private boolean errorCountValid=false;
	public LoginServiceImpl(){
		String loginErrorCount = Config.getInstance().getProperty("center.login.error.count");
		if(null!=loginErrorCount&&!"".equals(loginErrorCount)&&!"0".equals(loginErrorCount)){
			errorCount=Long.parseLong(loginErrorCount);
			errorCountValid=true;
		}
	}
	
	/**
	 * 登录系统
	 */
	@Override
	public EditJson loginSystem(HttpServletRequest request, HttpSession session, String username, String password){
		EditJson editJson=new EditJson();
        try{
        	Map<String,Object> map = loginDao.findUser(username);
        	if(map.size() > 0){
        		//登录错误次数校验
        		int currentErrorCount= map.get("LOGIN_ERROR_COUNT") ==null?0: (int)map.get("LOGIN_ERROR_COUNT");
        		if(errorCountValid==true){
        			if(map.get("STATE").toString().equals("L")){
        				Map<String,Object> dataBaseTime = loginDao.selectDataBaseCurrentTime();
        				if(dataBaseTime.size() > 0){
        					//当前数据库时间
        					String dbTime = dataBaseTime.get("DATABASETIME").toString();
        					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        					String time = sdf.format(sdf.parse(dbTime));
        					//读取配置文件 
        					String unlockWait = Config.getInstance().getProperty("center.login.error.unlockWaitTime");
        					//用户锁定时间
        					if(null != map.get("LOCK_DATE")){
        						String lockDate = map.get("LOCK_DATE").toString();
            					Date lock = sdf.parse(lockDate);
            					Date dbTimeParse = sdf.parse(time);
            					//账户锁定时间与数据库时间对比
            					//用户账户被锁定超过多长时间 才可自动解锁
            					long result = (dbTimeParse.getTime() -lock.getTime())/(60*1000);
            					//获取配置文件配置的账户锁定时间 单位:分钟
            					Long unlock = Long.parseLong(unlockWait);
            					//用户锁定时间 超过了配置文件配置的时间
            					if(unlock < result){
            						//解锁用户 登录系统
            						map.put("LOGIN_ERROR_COUNT", 0);
            						map.put("LOCK_DATE", null);
            						map.put("STATE", "A");
            						//用户密码正确性校验
                        			this.validCorrectness(editJson, request, session, map, password, errorCountValid, errorCount, currentErrorCount);
            					}else{
            						editJson.setBean("该账户已被锁定，请等待"+unlockWait+"分钟之后重新登录！");
                                	editJson.setSuccess(false);
            					}
        					}else{
        						editJson.setBean("系统错误");
        			        	editJson.setSuccess(false);
        					}
        				}else{
        					editJson.setBean("系统错误");
        		        	editJson.setSuccess(false);
        				}
        			}else{
        				//用户密码正确性校验
            			this.validCorrectness(editJson, request, session, map, password, errorCountValid, errorCount, currentErrorCount);
        			}
        		}else{
        			//用户密码正确性校验
        			this.validCorrectness(editJson, request, session, map, password, errorCountValid, errorCount, currentErrorCount);
        		}
        	}else{
        		editJson.setBean("用户名不存在，请重新输入");
            	editJson.setSuccess(false);
        	}
        }catch(Exception e){
        	editJson.setBean("系统错误");
        	editJson.setSuccess(false);
        	throw new RuntimeException(e.getMessage(),e);
        }
        return editJson;
    }
	
	/**
	 * 校验正确性
	 */
	private void validCorrectness(EditJson editJson,HttpServletRequest request,HttpSession session,
			Map<String,Object> map,String password,boolean errorCountValid,long errorCount,long currentErrorCount) {
		try{
			if(MD5Util.encrypt(password).equals(map.get("PASSWORD"))){
				//更新用户登录时间、登录错误次数
				loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),0,"A",null);
				//将用户姓名存入session中
				session.setAttribute("NAME", map.get("NAME").toString());
				//将系统用户名存入session中
				session.setAttribute("USERNAME", map.get("USERNAME").toString());
				//将用户ID存入session中
				session.setAttribute("USER_ID", map.get("USER_ID").toString());
				//上次登录时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(null != map.get("LOGIN_DATE")){
					String loginDate = map.get("LOGIN_DATE").toString();
					session.setAttribute("LOGIN_DATE", sdf.format(sdf.parse(loginDate)));
				}
				editJson.setSuccess(true);
			}else{
				if(errorCountValid==true){
					//更新用户登录时间、登录错误次数
					if(currentErrorCount==errorCount){
						loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),errorCount,"L",new Date());
						editJson.setBean("该账户已被锁定");
			        	editJson.setSuccess(false);
					}else{
						loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),(currentErrorCount+1),"A",null);
						editJson.setBean("密码错误，请重新输入");
			        	editJson.setSuccess(false);
					}
				}else{
					//更新用户登录时间、登录错误次数
					loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),0,"A",null);
					editJson.setBean("密码错误，请重新输入");
		        	editJson.setSuccess(false);
				}
			}
		}catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
	}
	
}
