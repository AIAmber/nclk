package cn.com.sparknet.common.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties属性文件操作
 * @author chenxy
 * 
 */
public final class PropertiesUtil {
	
	private Properties properties=null;
	
	/**
	 * 构造方法，实例化时加载属性文件
	 */
	public PropertiesUtil(InputStream is){
		try {
			properties = new Properties();
			properties.load(is);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 获取属性
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * 获取所有属性
	 * @return
	 */
	public Map getAllProperty() {
		Map map = new HashMap();
        try {
            Enumeration en = properties.propertyNames();
            String key = "";
            while (en.hasMoreElements()) {
            	key = (String) en.nextElement();
            	map.put(key, properties.getProperty (key));
            }
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage(),e);
        } 
        return map;
    }
	
	/**
	 * 写入属性文件
	 * @param filePath
	 * @param key
	 * @param value
	 */
	public void writeProperties(String filePath,String key,String value) {
	    OutputStream fos = null;
	    try {
            fos = new FileOutputStream(filePath);
            properties.setProperty(key, value);
            properties.store(fos, "Update '" + key + "' value");
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage(),e);
        } finally {
        	try{
				if(fos!=null){
					fos.flush();
					fos.close();
					fos=null;
				}
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
        }
    }

}
