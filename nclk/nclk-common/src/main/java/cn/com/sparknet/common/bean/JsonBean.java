package cn.com.sparknet.common.bean;

import cn.com.sparknet.common.util.JsonUtil;

/**
 * JsonBean抽象类
 * @author chenxy
 *
 */
public class JsonBean{

	private String dateFormat;

	public JsonBean() {
		dateFormat = "yyyy-MM-dd";
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String toJsonString() {
		return JsonUtil.objectToJson(this);
	}

}