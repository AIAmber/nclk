package cn.com.sparknet.nclk.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.ModuleService;
import cn.com.sparknet.nclk.service.UserService;
import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 用户管理
 * @author wuyl
 *
 */
@Controller
@RequestMapping("/UserController")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private ModuleService moduleService;
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public EditJson changePassword(HttpServletRequest request,HttpServletResponse response){
		String userid = StringUtil.nullToEmpty(request.getParameter("userId"));
		String pwd = StringUtil.nullToEmpty(request.getParameter("password"));
		String newPassword = StringUtil.nullToEmpty(request.getParameter("newPassword"));
		EditJson editjson = userService.updatePassword(userid, pwd, newPassword);
		return editjson;
	} 
	/**
	 * 用户管理密码修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	public EditJson updatePassword(HttpServletRequest request,HttpServletResponse response){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		String newPassword = StringUtil.nullToEmpty(request.getParameter("newPassword"));
		EditJson editjson = userService.updateUserPassword(userId, newPassword);
		return editjson;
	} 
	
	/**
	 * 查询用户头像
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findUserPhotoById")
	public void findUserPhotoById(HttpServletRequest request,HttpServletResponse response){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		userService.findUserPhotoById(userId, response);
	}
	
	/**
	 * 查询用户列表信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUsers")
	public ListJson findUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String start=StringUtil.nullToEmpty(request.getParameter("start"));
		String limit=StringUtil.nullToEmpty(request.getParameter("limit"));
		String searchText = StringUtil.nullToEmpty(request.getParameter("searchText"));
		String state = StringUtil.nullToEmpty(request.getParameter("state"));
		return userService.findUsers(start , limit,searchText,state);
	}
	
	/**
	 * 检查用户名是否存在
	 */
	@ResponseBody
	@RequestMapping("/findUsername")
	public int findUsername(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId=request.getParameter("userId");
		String userName=request.getParameter("userName");
		return userService.findUsername(userId,userName);
	}
	
	/**
	 * 保存用户
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveUser")
	public EditJson addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId=StringUtil.nullToEmpty(request.getParameter("userId"));
		String perName=StringUtil.nullToEmpty(request.getParameter("perName"));
		String userName=StringUtil.nullToEmpty(request.getParameter("userName"));
		String password=StringUtil.nullToEmpty(request.getParameter("password"));
		String state=StringUtil.nullToEmpty(request.getParameter("state"));
		String ord=StringUtil.nullToEmpty(request.getParameter("ord"));
		String photoUploadPath=StringUtil.nullToEmpty(request.getParameter("photoPath"));
		String photoPath = URLDecoder.decode(URLDecoder.decode(photoUploadPath,"UTF-8"),"UTF-8");
		String moduleId=StringUtil.nullToEmpty(request.getParameter("moduleId"));
		String areaId=StringUtil.nullToEmpty(request.getParameter("areaId"));
		String endPointId=StringUtil.nullToEmpty(request.getParameter("endPointId"));
		EditJson editJson=userService.saveUser(userId,perName,userName,password,state,ord,photoPath,moduleId,areaId,endPointId);
		return editJson;
	}
	
	/**
	 * 权限模块获取
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getModuleInfo")
	@ResponseBody
	public ListJson getModuleInfo(HttpServletRequest request ,HttpServletResponse response) {
		return moduleService.getModuleInfo();
	}
	
	/**
	 * 根据用户Id删除用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteUserInfoByUserId")
	public EditJson deleteUserInfoByUserId(HttpServletRequest request,HttpServletResponse response){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		return userService.deleteUserInfoByUserId(userId);
	}
	/**
	 * 根据用户Id查找用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUserInfoByUserId")
	public Map<String, Object> findUserInfoByUserId(HttpServletRequest request,HttpServletResponse response){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		return userService.findUserInfoByUserId(userId);
	}
	
	/**
	 * 根据用户id查询用户所具体的权限
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserModuleInfo")
	public List<Map<String, Object>> getUserModuleInfo(HttpServletRequest request){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		return userService.findUserModuleByUserId(userId);
	}
	
	/**
	 * 根据用户id保存用户模块权限
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveUserModuleInfo")
	public EditJson saveUserModuleInfo(HttpServletRequest request){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		String moduleId=StringUtil.nullToEmpty(request.getParameter("moduleId"));
		return userService.saveUserModuleByUserId(userId,moduleId);
	}
	
	/**
	 * 根据用户id查询用户管理区域
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserManageAreaInfo")
	public List<Map<String, Object>> getUserManageAreaInfo(HttpServletRequest request){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		return userService.findUserManageAreaInfo(userId);
	}
	/**
	 * 根据用户id查询用户管理考点
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserManageEndpointInfo")
	public List<Map<String, Object>> getUserManageEndpointInfo(HttpServletRequest request){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		return userService.findUserManageEndpointInfo(userId);
	}
	
	/**
	 * 根据用户id保存用户管理区域
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveUserAreaInfo")
	public EditJson saveUserAreaInfo(HttpServletRequest request){
		String userId = StringUtil.nullToEmpty(request.getParameter("userId"));
		String areaId=StringUtil.nullToEmpty(request.getParameter("areaId"));
		String endPointId=StringUtil.nullToEmpty(request.getParameter("endPointId"));
		return userService.saveUserAreaByUserId(userId,areaId,endPointId);
	}
	
	/**
	 * 根据用户id保存用户头像
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveUserPhoto")
	public EditJson saveUserPhoto(HttpServletRequest request){
		try {
			String userPhotoPath = StringUtil.nullToEmpty(request.getParameter("filePath"));
			String filePath = URLDecoder.decode(URLDecoder.decode(userPhotoPath,"UTF-8"),"UTF-8");
			String userID = (String) request.getSession().getAttribute("USER_ID");
			return userService.saveUserPhoto(userID, filePath);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 解锁用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateUserState")
	public EditJson updateUserState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids=StringUtil.nullToEmpty(request.getParameter("userId"));
		return userService.updateUserState(ids);
	}
	
}
