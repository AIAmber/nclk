package cn.com.sparknet.nclk.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import cn.com.sparknet.nclk.dao.Page;
import cn.com.sparknet.nclk.dao.UserDao;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.FileService;
import cn.com.sparknet.nclk.service.UserService;
import cn.com.sparknet.nclk.util.InputStreamUtil;
import cn.com.sparknet.nclk.util.MD5Util;
import cn.com.sparknet.nclk.util.StringUtil;
import cn.com.sparknet.nclk.util.UUIDUtil;

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
        			editJson.setBean("修改密码成功!");
        		}else{
        			editJson.setSuccess(false);
        			editJson.setBean("用户输入的原密码不正确,请重新输入!");
        		}
        	}
        	
        }catch(Exception e){
        	editJson.setSuccess(false);
        	editJson.setBean("修改密码失败!");
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
        	List<InputStream> list =userDao.findUserPhotoById(id);
        	for(InputStream is:list){
        		if(null != is){
        			fileService.showPic(is, response);
        		}
        	}
        }catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
    }
	

	/**
	 *查询用户列表信息
	 */
	@Override
	public ListJson findUsers(String start, String limit,String searchText,String state)  {
		ListJson listJson =new ListJson();
		Page page=userDao.findUsers(start,limit,searchText,state);
		listJson.setRows(page.getList());
    	listJson.setTotal(page.getCount());
		return listJson;
	}

	/**
	 * 检查用户名是否存在
	 */
	@Override
	public int findUsername(String userId,String userName){
		int count=0;
        try{
        	count=userDao.findUsername(userId,userName);
        }catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
        return count;
    }
	/**
	 *保存用户
	 */
	@Override
	public EditJson saveUser(String userId,String perName, String userName, String password,String state,String ord,String photoPath,String moduleId,String areaId,String endPointId) {
		EditJson editJson=new EditJson();
		password=MD5Util.encrypt(password);
		File file = new File(photoPath);
		if(StringUtil.isEmpty(userId)) {
			try {
				userId=UUIDUtil.getNextValue();
            		if(file.exists()){
            			InputStream is = new FileInputStream(file);
            			byte[] in = InputStreamUtil.InputStreamToByte(is);
            			userDao.addUser(userId,perName,userName,password,state,ord,in);
            		}else {
            			userDao.addUser(userId,perName,userName,password,state,ord,null);
					}
					if(StringUtil.isEmpty(moduleId)) {
						editJson.setSuccess(true);
					}else {
						String[] moduleIdList=moduleId.split(",");
						for (int i = 0; i < moduleIdList.length; i++) {
							String userAuthId=UUIDUtil.getNextIntValue();
							userDao.saveUserModuleByUserId(userAuthId,userId,moduleIdList[i]);
						}
					}
					if(StringUtil.isEmpty(areaId)&&StringUtil.isEmpty(endPointId)) {
						editJson.setSuccess(true);
					}else {
						String[] areaIdList=areaId.split(",");
						for (int i = 0; i < areaIdList.length; i++) {
							String userAreaId=UUIDUtil.getNextIntValue();
							userDao.saveUserAreaByUserId(userAreaId,userId,areaIdList[i]);
						}
						if(StringUtil.isNotEmpty(endPointId)) {
							String[] endPointIdList=endPointId.split(",");
							for (int j = 0; j < endPointIdList.length; j++) {
								String userEndPointId=UUIDUtil.getNextIntValue();
								userDao.saveUserEndPointByUserId(userEndPointId,userId,endPointIdList[j]);
						}
						}
					}
					editJson.setSuccess(true);
					editJson.setBean("新增成功！");
			
			} catch (Exception e) {
				editJson.setSuccess(false);
				editJson.setBean("新增失败");
				throw new RuntimeException(e.getMessage(),e);
			}
		}else {
			try {
        		if(file.exists()){
        			InputStream is = new FileInputStream(file);
        			byte[] in = InputStreamUtil.InputStreamToByte(is);
        			userDao.updateUser(userId,perName,userName,password,state,ord,in);
        		}else {
        			userDao.updateUser(userId,perName,userName,password,state,ord,null);
				}
				if(StringUtil.isEmpty(moduleId)) {
					editJson.setSuccess(true);
				}else {
					userDao.deleteUserModuleByUserId(userId);
					String[] moduleIdList=moduleId.split(",");
					for (int i = 0; i < moduleIdList.length; i++) {
						String userAuthId=UUIDUtil.getNextIntValue();
						userDao.saveUserModuleByUserId(userAuthId,userId,moduleIdList[i]);
					}
				}
					if(StringUtil.isEmpty(areaId)&&StringUtil.isEmpty(endPointId)) {
						editJson.setSuccess(true);
					}else {
						userDao.deleteUserAreaByUserId(userId);
						userDao.deleteUserEndPointByUserId(userId);
						String[] areaIdList=areaId.split(",");
						for (int i = 0; i < areaIdList.length; i++) {
							String userAreaId=UUIDUtil.getNextIntValue();
							userDao.saveUserAreaByUserId(userAreaId,userId,areaIdList[i]);
						}
						if(StringUtil.isNotEmpty(endPointId)) {
							String[] endPointIdList=endPointId.split(",");
							for (int j = 0; j < endPointIdList.length; j++) {
								String userEndPointId=UUIDUtil.getNextIntValue();
								userDao.saveUserEndPointByUserId(userEndPointId,userId,endPointIdList[j]);
						}
						}
					}
				editJson.setSuccess(true);
				editJson.setBean("修改成功！");
			} catch (Exception e) {
				editJson.setSuccess(false);
				editJson.setBean("修改失败");
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		return editJson;
		
	}


	/**
	 *根据用户Id删除用户信息
	 */
	@Override
	public EditJson deleteUserInfoByUserId(String userId) {
		EditJson editJson =new EditJson();
		try {
			String[] userIdList=userId.split(",");
			userDao.deleteUserInfoByUserId(userIdList);
			editJson.setSuccess(true);
			editJson.setBean("删除成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("删除失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}


	/**
	 *用户管理密码修改
	 */
	@Override
	public EditJson updateUserPassword(String userId, String newpassword) {
		EditJson editJson =new EditJson();
		try {
			userDao.updatePassword(userId, MD5Util.encrypt(newpassword));
			editJson.setSuccess(true);
			editJson.setBean("密码修改成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("密码修改失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}


	/**
	 *通过用户Id查找用户信息
	 */
	@Override
	public Map<String, Object> findUserInfoByUserId(String userId) {
		return userDao.findUserInfoByUserId(userId);
	}

	/**
	 * 根据用户Id查找用户所具体的权限
	 */
	@Override
	public List<Map<String, Object>> findUserModuleByUserId(String userId) {
		return userDao.findUserModuleByUserId(userId);
	}


	/**
	 *根据用户id保存用户模块权限
	 */
	@Override
	public EditJson saveUserModuleByUserId(String userId, String moduleId) {
		EditJson editJson =new EditJson();
			List<Map<String, Object>> map=userDao.findUserModuleByUserId(userId);
			try {
				if (!map.isEmpty()) {
					userDao.deleteUserModuleByUserId(userId);
				}
					if(StringUtil.isEmpty(moduleId)) {
						userDao.deleteUserModuleByUserId(userId);
						editJson.setSuccess(true);
						editJson.setBean("权限设置成功！");
					}else {
						String[] moduleIdList=moduleId.split(",");
						for (int i = 0; i < moduleIdList.length; i++) {
							String userAuthId=UUIDUtil.getNextIntValue();
							userDao.saveUserModuleByUserId(userAuthId,userId,moduleIdList[i]);
						}
						editJson.setSuccess(true);
						editJson.setBean("权限设置成功！");
					}
				} catch (Exception e) {
					editJson.setSuccess(false);
					editJson.setBean("权限设置失败！");
					throw new RuntimeException(e.getMessage(),e);
				}
		return editJson;
	}


	/**
	 *根据用户id查询用户管理区域
	 */
	@Override
	public List<Map<String, Object>> findUserManageAreaInfo(String userId) {
		return userDao.findUserManageAreaInfo(userId);
	}
	/**
	 *根据用户id查询用户管理考点
	 */
	@Override
	public List<Map<String, Object>> findUserManageEndpointInfo(String userId) {
		return userDao.findUserManageEndpointInfo(userId);
	}


	/**
	 *根据用户id保存用户管辖区域
	 */
	@Override
	public EditJson saveUserAreaByUserId(String userId, String areaId ,String endPointId) {
		EditJson editJson =new EditJson();
			try {
				  List<Map<String, Object>> areaMap=userDao.findUserManageAreaInfo(userId);
					if (!areaMap.isEmpty()) {
						userDao.deleteUserAreaByUserId(userId);
						userDao.deleteUserEndPointByUserId(userId);
					}
						if(StringUtil.isEmpty(areaId)&&StringUtil.isEmpty(endPointId)) {
							userDao.deleteUserAreaByUserId(userId);
							userDao.deleteUserEndPointByUserId(userId);
							editJson.setSuccess(true);
							editJson.setBean("管辖区域设置成功！");
						}else {
							String[] areaIdList=areaId.split(",");
							for (int i = 0; i < areaIdList.length; i++) {
								String userAreaId=UUIDUtil.getNextIntValue();
								userDao.saveUserAreaByUserId(userAreaId,userId,areaIdList[i]);
							}
							if(StringUtil.isNotEmpty(endPointId)) {
								String[] endPointIdList=endPointId.split(",");
								for (int j = 0; j < endPointIdList.length; j++) {
									String userEndPointId=UUIDUtil.getNextIntValue();
									userDao.saveUserEndPointByUserId(userEndPointId,userId,endPointIdList[j]);
							}
							}
							editJson.setSuccess(true);
							editJson.setBean("管辖区域设置成功！");
						}
					} catch (Exception e) {
						editJson.setSuccess(false);
						editJson.setBean("管辖区域设置失败！");
						throw new RuntimeException(e.getMessage(),e);
					}
		return editJson;
	}

	/**
	 * 修改用户头像
	 */
	@Override
	public EditJson saveUserPhoto(String userId, String filePath) {
		EditJson editJson=new EditJson();
		InputStream is = null;
        try{
        	//修改人员头像
        	if(StringUtil.isNotEmpty(userId)){
        		if(StringUtil.isEmpty(filePath)){
            		userDao.saveUserPhoto(userId,null);
            	}else{
        			File file = new File(filePath);
            		if(file.exists()){
            			is = new FileInputStream(file);
            			userDao.saveUserPhoto(userId,InputStreamUtil.InputStreamToByte(is));
            		}
            	}
            	editJson.setSuccess(true);
        	}
        }catch(Exception e){
        	editJson.setSuccess(false);
        	throw new RuntimeException(e.getMessage(),e);
        }finally{
        	if(null != is){
        		try {
					is.close();
					is = null;
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(),e);
				}
        	}
        }
        return editJson;
	}

	/**
	 * 解锁用户
	 */
	@Override
	public EditJson updateUserState(String ids) {
		EditJson editJson=new EditJson();
        try{
        	String[] idList=ids.split(",");
        	userDao.updateUserState(idList);
        	editJson.setSuccess(true);
        }catch(Exception e){
        	editJson.setSuccess(false);
        	throw new RuntimeException(e.getMessage(),e);
        }
        return editJson;
	}
	
}
