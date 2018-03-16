package cn.com.sparknet.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON工具类
 * @author chenxy
 */
public final class JsonUtil {

	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
	}
	
	private static final SerializerFeature[] features = {
			// 设置Map字段为null时输出空值
			SerializerFeature.WriteMapNullValue,
			// 设置List字段为null时输出为[]
			SerializerFeature.WriteNullListAsEmpty,
			// 设置String字段为null时输出为空字符
			SerializerFeature.WriteNullStringAsEmpty
	};
	
	/**
	 * Json字符串转Java对象
	 * @param text
	 * @return
	 */
	public static Object jsonToObject(String text) {
		return JSON.parseObject(text);
	}
	
	/**
	 * Json字符串转Java对象
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	/**
	 * Json字符串转Map
	 * @param text
	 * @return
	 */
	public static Map<?,?> jsonToMap(String text) {
		return JSON.parseObject(text, Map.class);
	}

	/**
	 * Json字符串转数组
	 * @param text
	 * @return
	 */
	public static Object[] jsonToArray(String text) {
		return JSON.parseArray(text).toArray();
	}
	
	/**
	 * Json字符串转数组
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> Object[] jsonToArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	/**
	 * Json字符串转List
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	/**
	 * Java对象转Json字符串
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		return JSON.toJSONString(object,config,features);
	}

	/**
	 * List转Json字符串
	 * @param list
	 * @return
	 */
	public static String listToJson(List<?> list) {
		return JSON.toJSONString(list,config,features);
	}

	/**
	 * 数组转Json字符串
	 * @param array
	 * @return
	 */
	public static String arrayToJson(Object[] array) {
		return JSON.toJSONString(array,config,features);
	}

	/**
	 * Map转Json字符串
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<?, ?> map) {
		return JSON.toJSONString(map,config,features);
	}

	/**
	 * Set转Json字符串
	 * @param set
	 * @return
	 */
	public static String setToJson(Set<?> set) {
		return JSON.toJSONString(set,config,features);
	}

}
