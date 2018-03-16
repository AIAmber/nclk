package cn.com.sparknet.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring Bean动态注册工具类
 * @author chenxy
 *
 */
@Component
public class SpringDynamicBeanUtil {
	
	@Resource
	private SpringContextUtil springContextUtil;
	
	/**
	 * 创建Bean工厂
	 */
	private DefaultListableBeanFactory createBeanFactory() throws Exception {
		ConfigurableApplicationContext applicationContext=(ConfigurableApplicationContext)springContextUtil.getApplicationContext();
        return (DefaultListableBeanFactory)applicationContext.getBeanFactory();
	}
	
	/**
	 * 注册Bean
	 * @param beanName Bean名称
	 * @param beanClass Bean类
	 * @param beanProperty Bean属性
	 * @param destroyMethodName 销毁方法名，可为空
	 * @throws Exception
	 */
	public void registerBean(String beanName,Class<?> beanClass,Map<String,String> beanProperty,String destroyMethodName) throws Exception {
        DefaultListableBeanFactory beanFactory = this.createBeanFactory();
        //判断Bean工厂中是否已注册了同名的Bean
		if(!beanFactory.containsBean(beanName)){
			//创建Bean构建器
	        BeanDefinitionBuilder builder=BeanDefinitionBuilder.genericBeanDefinition(beanClass);
	        Iterator iterator = beanProperty.entrySet().iterator();
	        while(iterator.hasNext()){
	        	Entry mapEntry = (Entry) iterator.next();
	            String key=mapEntry.getKey().toString();
	            builder.addPropertyValue(key, beanProperty.get(key));
	        }
	        if(StringUtil.isNotEmpty(destroyMethodName)){
	        	builder.setDestroyMethodName(destroyMethodName);
	        }
			//注册Bean
			beanFactory.registerBeanDefinition(beanName, builder.getBeanDefinition());
		}
	}
	
	/**
	 * 移除Bean
	 * @param beanName Bean名称
	 * @throws Exception
	 */
	public void removeBean(String beanName) throws Exception {
		DefaultListableBeanFactory beanFactory = this.createBeanFactory();
		beanFactory.removeBeanDefinition(beanName);
	}

}
