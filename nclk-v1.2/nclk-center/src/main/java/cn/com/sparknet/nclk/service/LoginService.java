package cn.com.sparknet.nclk.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.com.sparknet.nclk.json.EditJson;

/**
 * 系统登录
 * @author chenxy
 *
 */
public interface LoginService {
	
	/**
	 * 登录系统
	 * @param request request对象
	 * @param session session对象
	 * @param username 用户名
	 * @param password 密码
	 * @return 
	 */
	public EditJson loginSystem(HttpServletRequest request, HttpSession session, String username, String password);
	
}
