package cn.com.sparknet.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context工具类
 * @author chenxy
 * 
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	/**
	 * 设置ApplicationContext
	 * 容器加载后由Spring自动注入applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	/**
	 * 获取ApplicationContext
	 */
	public ApplicationContext getApplicationContext() throws BeansException {
		return applicationContext;
	}

	/**
	 * 获取Bean
	 */
	public Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

}
