package cn.com.sparknet.nclk.filter;

import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session过滤器
 * @author chenxy
 *
 */
/*@WebFilter(
		filterName = "SessionFilter", 
		urlPatterns = { "/*" }, 
		initParams = {
				@WebInitParam(name = "needFilterSuffix", value = "Controller;.jsp;.html;"),
				@WebInitParam(name = "unNeedFilterUrl", value = "/login/index.jsp;/LoginController;")
		}
)*/
public class SessionFilter implements Filter {

	public FilterConfig config;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String unNeedFilterUrl = config.getInitParameter("unNeedFilterUrl");
		String needFilterSuffix = config.getInitParameter("needFilterSuffix");

		String[] unNeedFilterUrlList = unNeedFilterUrl.split(";");
		String[] needFilterSuffixList = needFilterSuffix.split(";");
		String uri = httpServletRequest.getRequestURI();
		Object sessionBean = httpServletRequest.getSession().getAttribute("user");
		if (sessionBean == null) {
			if (!this.isContains(uri, needFilterSuffixList)) {
				chain.doFilter(request, response);
				return;
			}
			if (this.isContains(uri, unNeedFilterUrlList)) {
				chain.doFilter(request, response);
				return;
			}
			// 判断URI中是否包含部分页面后缀请求，解决“直接访问管理页面”漏洞
			if (isContains(uri, new String[]{".jsp",".html",".asp",".aspx",".php",".cfm",".cgi"})) {
				String context = httpServletRequest.getContextPath();
				String path=getClass().getClassLoader().getResource("").getPath().replace("/WEB-INF/classes/", "");
				path=path.replaceAll("%20", " ");//空格问题
				String filePath = path+uri.replaceFirst(context, "");
				File file = null;
				if(filePath != null){
					  file = new File(filePath);
				}
				if(file!=null && file.exists()){
					// 如存在，重定向到登录页
					this.redirect(httpServletRequest, httpServletResponse);
				}else{
					// 如不存在，直接访问目标地址，并自动进入404页面
					chain.doFilter(request, response);
				}
			}else{
				// 重定向到登录页
				this.redirect(httpServletRequest, httpServletResponse);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		this.config = null;
	}

	/**
	 * 是否包含
	 */
	private boolean isContains(String container, String[] regx) {
		boolean result = false;
		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i].trim()) != -1) {
				return true;
			}
		}
		return result;
	}
	
	/**
	 * 重定向
	 */
	private void redirect(HttpServletRequest request,HttpServletResponse response) {
		try {
			String requestType=request.getHeader("X-Requested-With");
			if(null!=requestType&&requestType.equalsIgnoreCase("XMLHttpRequest")){
				// Ajax请求，抛出超时的响应头
				response.addHeader("Session-Status", "timeout");
			}else{
				// Http请求，重定向到登陆页
				request.getSession().setAttribute("errorinfo", "");
				response.sendRedirect(request.getContextPath() + "/login/index.jsp");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

}
