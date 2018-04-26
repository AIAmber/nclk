package cn.com.sparknet.common.json;

import java.io.Serializable;
import cn.com.sparknet.common.bean.JsonBean;

/**
 * 编辑操作时的Json
 * @author chenxy
 *
 */
public class EditJson extends JsonBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Object bean;

	private boolean success;

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
