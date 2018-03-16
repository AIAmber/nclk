package cn.com.sparknet.common.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置信息
 * @author chenxy
 */
public class Config {
	
	private static Config config;
	private static Properties properties;
	
	private Config() {
	}

	public static Config getInstance() {
		if (config == null) {
			synchronized(Config.class){
				if (config == null) {
					config = new Config();
					InputStream is = null;
			        try {
			            is = Config.class.getClassLoader().getResourceAsStream("config.properties");
			            properties = new Properties();
			            properties.load(is);
			        }catch (Exception e) {
			            throw new RuntimeException(e.getMessage(),e);
			        }finally{
			            try{
			                if(null!=is){
			                    is.close();
			                    is=null;
			                }
			            }catch(Exception e){
			                throw new RuntimeException(e.getMessage(),e);
			            }
			        }
				}
			}
		}
		return config;
	}
	
	/**
	 * 获取属性
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
