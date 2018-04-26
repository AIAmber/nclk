package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;

/**
 * 用户管理
 * @author wuyl
 *
 */
public interface UserService {

	/**
	 * 修改用户密码 
	 * @param userid 用户名
	 * @param password 密码
	 * @param newpassword 确认密码
	 * @return
	 */
	public EditJson updatePassword(String userid,String password,String newpassword);
	/**
	 *用户管理密码修改 
	 * @param userId 用户名
	 * @param newpassword 新密码
	 * @return
	 */
	public EditJson updateUserPassword(String userId,String newpassword);
	/**
	 * 查询用户头像
	 * @param userId 用户id
	 * @param response
	 */
	public void findUserPhotoById(String userId,HttpServletResponse response);
	
	/**
	 * 查询用户列表信息
	 * @param start
	 * @param limit
	 * @param searchText
	 * @param state
	 * @return
	 */
	public ListJson findUsers(String start, String limit,String searchText,String state);
	
	/**
	 * 检查用户名是否存在
	 * @param userId
	 * @param userName
	 * @return
	 */
	public int findUsername(String userId,String userName);
	/**
	 * 保存用户管理
	 * @param userId
	 * @param perName
	 * @param userName
	 * @param password
	 * @param state
	 * @param ord
	 * @param photoPath
	 * @param moduleId
	 * @param areaId
	 * @param endPointId
	 * @return
	 */
	public EditJson saveUser(String userId,String perName, String userName, String password,String state,String ord, String photoPath,String moduleId,String areaId,String endPointId);
	
	/**
	 *  根据用户Id删除用户信息
	 * @param userId
	 * @return
	 */
	public EditJson deleteUserInfoByUserId(String userId);
	
	/**
	 * 根据用户Id查找用户信息
	 * @param userId
	 * @return
	 */
	public Map<String, Object> findUserInfoByUserId(String userId);
	/**
	 * 根据用户Id查找用户权限
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> findUserModuleByUserId(String userId);
	/**
	 * 根据用户id保存用户模块权限
	 * @param userId
	 * @param moduleId
	 * @return
	 */
	public EditJson saveUserModuleByUserId(String userId, String moduleId);
	/**
	 * 根据用户id查询用户管理区域
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> findUserManageAreaInfo(String userId); 	
	/**
	 * 根据用户id查询用户管理考点
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> findUserManageEndpointInfo(String userId);
	
	
	/**
	 * 根据用户id保存用户管理区域
	 * @param userId
	 * @param areaId
	 * @param endPointId
	 * @return
	 */
	public EditJson saveUserAreaByUserId(String userId, String areaId,String endPointId); 
	/**
	 * 修改用户头像
	 * @param userId
	 * @param filePath
	 * @return
	 */
	public EditJson saveUserPhoto(String userId,String filePath); 	
	/**
	 * 解锁用户
	 * @param ids
	 * @return
	 */
	public EditJson updateUserState(String ids);
}
