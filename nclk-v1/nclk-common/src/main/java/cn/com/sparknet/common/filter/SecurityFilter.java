package cn.com.sparknet.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全漏洞过滤器
 * @author chenxy
 *
 */
public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		//解决缺少跨帧脚本编制防御漏洞(表示页面可以在相同域名下的frame中展示)
		httpServletResponse.addHeader("X-Frame-Options","SAMEORIGIN");
		
		// 解决跨站点请求伪造漏洞
		String referer = ((HttpServletRequest) request).getHeader("Referer");
		String serverName = request.getServerName();
		if (null != referer && referer.indexOf(serverName) < 0) {
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
