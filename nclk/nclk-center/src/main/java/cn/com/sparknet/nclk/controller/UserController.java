package cn.com.sparknet.nclk.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.ls.LSInput;

import com.fasterxml.jackson.databind.Module;

import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.nclk.service.FileService;
import cn.com.sparknet.nclk.service.ModuleService;
import cn.com.sparknet.nclk.service.UserService;

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
	 * 查询用户信息
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
		return userService.findUsers(start , limit,searchText);
	}
	/**
	 * 新增用户
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public EditJson addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String perName=StringUtil.nullToEmpty(request.getParameter("perName"));
		String userName=StringUtil.nullToEmpty(request.getParameter("userName"));
		String password=StringUtil.nullToEmpty(request.getParameter("password"));
		EditJson editJson=userService.addUser(perName,userName,password);
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
	public String getModuleInfo(HttpServletRequest request ,HttpServletResponse response) {
		return moduleService.getModuleInfo();
	}
	
}
