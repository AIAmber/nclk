package cn.com.sparknet.nclk.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.stereotype.Service;

import cn.com.sparknet.common.dao.Page;
import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.MD5Util;
import cn.com.sparknet.common.util.UUIDUtil;
import cn.com.sparknet.nclk.dao.UserDao;
import cn.com.sparknet.nclk.service.FileService;
import cn.com.sparknet.nclk.service.UserService;

/**
 * 用户管理
 * @author wuyl
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	@Resource
    private FileService fileService;
	
	/**
	 * 修改用户密码
	 */
	@Override
	public EditJson updatePassword(String userid, String password, String newpassword) {
		EditJson editJson=new EditJson();
        try{
        	//根据用户id查询用户密码
        	Map<String,Object> map = userDao.findPasswordByUserId(userid);
        	if(map.size() > 0){
        		//用户输入的旧密码和数据库存储的密码进行校验
        		if(map.get("PASSWORD").equals(MD5Util.encrypt(password))){
        			//根据用户id更新密码
        			userDao.updatePassword(userid,MD5Util.encrypt(newpassword));
        			editJson.setSuccess(true);
        		}else{
        			editJson.setSuccess(false);
        			editJson.setBean("用户输入的旧密码不正确,请重新输入!");
        		}
        	}
        	
        }catch(Exception e){
        	editJson.setSuccess(false);
        	throw new RuntimeException(e.getMessage(),e);
        }
        return editJson;
	}

	
	/**
	 * 查询用户头像
	 */
	@Override
	public void findUserPhotoById(String id,HttpServletResponse response){
        try{
        	List<InputStream> list=userDao.findUserPhotoById(id);
        	for(InputStream is:list){
    			fileService.showPic(is, response);
        	}
        }catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
    }
	
	/**
	 * 删除用户头像
	 */
	@Override
	public void deleteUserPhotoById(String userId) {
		
	}
	
	@Override
	public List<Map<String, Object>> findAreaByUserId(String userId) {
		return null;
	}

	@Override
	public List<Map<String, Object>> findEndPointByUserId(String userId) {
		return null;
	}

	/**
	 *查询用户信息
	 */
	@Override
	public ListJson findUsers(String start, String limit,String searchText)  {
			ListJson listJson =new ListJson();
				Page page=userDao.findUsers(start,limit,searchText);
				listJson.setRows(page.getList());
	        	listJson.setTotal(page.getCount());
			return listJson;
		
	}


	/**
	 *新增用户
	 */
	@Override
	public EditJson addUser(String perName, String userName, String password) {
		EditJson editJson=new EditJson();
		try {
			String userId=UUIDUtil.getNextValue();
			userDao.addUser(userId,perName,userName,password);
			editJson.setSuccess(true);
			editJson.setBean("新增用户成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("新增用户失败");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
		
	}


	
}
