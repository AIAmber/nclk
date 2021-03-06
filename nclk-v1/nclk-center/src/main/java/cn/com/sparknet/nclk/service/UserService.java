package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;

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
	 * 查询用户所管理的区域信息
	 * @param userId 用户id
	 * @return 
	 */
	public List<Map<String,Object>> findAreaByUserId(String userId);
	/**
	 * 查询用户所管理的考点信息
	 * @param userId 用户id
	 * @return 
	 */
	public List<Map<String,Object>> findEndPointByUserId(String userId);
	/**
	 * 查询用户头像
	 * @param userId 用户id
	 * @param response
	 */
	public void findUserPhotoById(String userId,HttpServletResponse response);
	/**
	 * 删除用户头像
	 * @param userId 用户id
	 */
	public void deleteUserPhotoById(String userId);
	
	/**
	 * 查询用户信息
	 * @param limit 
	 * @param start 
	 * @param searchText
	 * @return
	 */
	public ListJson findUsers(String start, String limit,String searchText);
	
	/**
	 * 新增用户
	 * @param perName
	 * @param userName
	 * @param password
	 * @return
	 */
	public EditJson addUser(String perName, String userName, String password);
	
	
	
}
