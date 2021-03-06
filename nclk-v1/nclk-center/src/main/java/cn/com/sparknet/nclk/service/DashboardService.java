package cn.com.sparknet.nclk.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 首页echarts数据
 * @author lirj
 */
public interface DashboardService {
	
	public String jsonErrorType(String errorType);
	public String jsonErrorTypeForEcharts(String errorType);
	public String jsonLine(String errorType);
	public String jsonTotalTypeA(String errorNum);
	public String jsonTotalTypeBName(String errorNum, String areaPid);
	public String jsonTotalTypeId( String areaPid );
	public String jsonTotalTypeBNum(String errorNum, String areaPid);
	public String jsonTotalTypeBFatherNum(String areaId);
	public String jsonTotalTypeCName(String areaId);
	public String jsonTotalTypeCNum(String areaId);
}
