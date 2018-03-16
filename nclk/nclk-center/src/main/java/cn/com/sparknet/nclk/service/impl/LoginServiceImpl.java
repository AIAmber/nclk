package cn.com.sparknet.nclk.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.util.MD5Util;
import cn.com.sparknet.nclk.dao.LoginDao;
import cn.com.sparknet.nclk.service.LoginService;

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
        	if(null!=map){
        		//登录错误次数校验
        		int currentErrorCount= map.get("LOGIN_ERROR_COUNT") ==null?0: (int)map.get("LOGIN_ERROR_COUNT");
        		if(errorCountValid==true){
        			if(map.get("STATE").equals("L")){
        				editJson.setBean("该账户已被锁定");
                    	editJson.setSuccess(false);
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
				session.setAttribute("user", map);
				//更新用户登录时间、登录错误次数
				loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),0,"A");
				editJson.setSuccess(true);
			}else{
				if(errorCountValid==true){
					//更新用户登录时间、登录错误次数
					if(currentErrorCount==errorCount){
						loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),errorCount,"L");
						editJson.setBean("该账户已被锁定");
			        	editJson.setSuccess(false);
					}else{
						loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),(currentErrorCount+1),"A");
						editJson.setBean("密码错误，请重新输入");
			        	editJson.setSuccess(false);
					}
				}else{
					//更新用户登录时间、登录错误次数
					loginDao.updateUserLoginInfo(map.get("USER_ID").toString(),0,"A");
					editJson.setBean("密码错误，请重新输入");
		        	editJson.setSuccess(false);
				}
			}
		}catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
	}
	
}
