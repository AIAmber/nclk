package cn.com.sparknet.common.json;

import java.io.Serializable;
import java.util.List;
import cn.com.sparknet.common.bean.JsonBean;

/**
 * 列表查询时的Json
 * @author chenxy
 *
 */
public class ListJson extends JsonBean implements Serializable{
    
	private static final long serialVersionUID = 1L;

	private List rows;
    
	private int total;

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
