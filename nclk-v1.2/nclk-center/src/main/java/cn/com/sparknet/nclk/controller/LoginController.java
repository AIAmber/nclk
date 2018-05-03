package cn.com.sparknet.nclk.controller;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octo.captcha.service.CaptchaServiceException;

import cn.com.sparknet.nclk.captcha.CaptchaServiceSingleton;
import cn.com.sparknet.nclk.config.Config;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.service.LoginService;
import cn.com.sparknet.nclk.service.ModuleService;
import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 系统登录
 * @author chenxy
 *
 */
@Controller
@RequestMapping("/LoginController")
public class LoginController {
	
	@Resource
	private LoginService loginService;
	@Resource
	private ModuleService moduleService;
	
	/**
	 * 获取验证码
	 */
	@RequestMapping("/getCaptcha")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletOutputStream out = null;
		try {
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			out = response.getOutputStream();
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId,request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			throw new CaptchaServiceException(e.getMessage(),e);
		} finally {
			out.close();
			out=null;
		}
	}
	
	/**
	 * 登录系统
	 */
	@ResponseBody
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		if (!"POST".equals(request.getMethod())) {
			this.sendRedirect(false, "来自不安全的网站登录", session, request, response);
		}else{
			//校验验证码
			Boolean isCorrect = true;
			String systemCaptcha = Config.getInstance().getProperty("center.system.captcha");
			if("true".equals(systemCaptcha)){
				String captcha=StringUtil.nullToEmpty(request.getParameter("c"));
				try{
					isCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(session.getId(), captcha);
				} catch (CaptchaServiceException e){
					this.sendRedirect(false, "验证码已过期，请重新输入", session, request, response);
				}
			}
			if(isCorrect){
				//解决会话标识未更新漏洞
				if(null!=session){
					session.invalidate();
				}
				Cookie[] cookies=request.getCookies();
				if(null!=cookies){
					cookies[0].setMaxAge(0);
				}
				session=request.getSession(true);
				//账号校验
				String username=StringUtil.nullToEmpty(request.getParameter("u"));
				String password=StringUtil.nullToEmpty(request.getParameter("p"));
				
				EditJson editJson=loginService.loginSystem(request, session, username, password);
				//从数据库查询用户所具体的权限
				List<Map<String,Object>> moduleList = moduleService.findModuleByUserName(username);
				session.setAttribute("moduleTreeList", moduleList);
				String userId = (String) request.getSession().getAttribute("USER_ID");
				//查询用户所具有的区域权限信息
				List<Map<String,Object>> areaList = moduleService.getAreaByUserId(userId);
				session.setAttribute("areaList", areaList);
				
				if(editJson.isSuccess()){
					this.sendRedirect(true, null, session, request, response);
				}else{
					this.sendRedirect(false, editJson.getBean().toString(), session, request, response);
				}
			}else{
				this.sendRedirect(false, "验证码错误，请重新输入", session, request, response);
			}
		}
	}
	
	/**
	 * 注销系统
	 * @param request
	 * @param response
	 */
	@RequestMapping("/loginOut")
	public void loginOut(HttpServletRequest request, HttpServletResponse response){
		try{
			HttpSession session=request.getSession();
			if(null!=session){
				session.removeAttribute("sessionBean");
				session.invalidate();
			}
			response.sendRedirect(request.getContextPath()+"/login/index.jsp");
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 页面跳转
	 */
	private void sendRedirect(boolean success,String errorinfo,HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(success){
			//跳转到首页
			session.removeAttribute("errorinfo");
			response.sendRedirect(request.getContextPath()+"/index.jsp#/home");
		}else{
			//跳转到登录页
			session.setAttribute("errorinfo", "<img src=\""+request.getContextPath()+"/login/images/error.png\"/> "+errorinfo);
			response.sendRedirect(request.getContextPath()+"/login/index.jsp");
		}
	}

}
