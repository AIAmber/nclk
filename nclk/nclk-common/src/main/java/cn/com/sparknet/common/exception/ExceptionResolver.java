package cn.com.sparknet.common.exception;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 统一异常处理
 * @author chenxy
 *
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
	
	private static Logger log = Logger.getLogger(ExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response,Object handler,Exception e) {
		log.error(e.getMessage(),e);
		ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String,Object> map = new HashMap<String,Object>();
		map.put("error", e.getMessage());
		map.put("success", false);
		view.setAttributesMap(map);
        mav.setView(view);
        return mav;
	}

}
