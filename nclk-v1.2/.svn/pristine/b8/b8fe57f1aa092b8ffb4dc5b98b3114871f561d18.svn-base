package cn.com.sparknet.nclk.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 首页echarts数据
 * @author lirj
 */
public interface DashboardService {

	/**
	 * data of title, card 首页小卡片数据加载 （采用monitor中的方法）
	 *
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getClockCount(String userId);

	/**
	 * data of pie， 首页echarts - pie 省级数据加载
	 *
	 * @param areaPid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonErrorTypeForEcharts(String userId, String areaPid, String startTime, String endTime) throws ParseException;

	/**
	 * data of pie， 首页echarts - pie 市级数据加载
	 *
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonErrorTypeForEchartsB(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of pie， 首页echarts - pie 县区级数据加载
	 *
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonErrorTypeForEchartsC(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of total, citys， 首页echarts - total 省级数据加载（num - y轴）
	 *
	 * @param areaPid, userId
	 * @return
	 */
	public String jsonTotalTypeA(String areaPid, String userId);

	/**
	 * data of total, border， 首页echarts - total 市级数据加载（name - x轴）
	 *
	 * @param areaPid, userId
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeBName(String areaPid, String userId);

	/**
	 * data of total, border， 首页echarts - total 数据加载（获取当前区域的id 或 parentId）
	 *
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeId(String areaPid);

	/**
	 * data of total, border - num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
	 * 首页echarts - total 市级数据加载（num - y轴）
	 *
	 * @param errorNum
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeBNum(String errorNum, String areaPid);

	/**
	 * data of total, border，首页echarts - total 市级数据加载（name - x轴）
	 *
	 * @param areaId
	 * @return
	 */
	public String jsonTotalTypeCName(String areaId, String userId);

	/**
	 * data of total, border，首页echarts - total 县区级数据加载（id） -> num - y轴
	 *
	 * @param areaId
	 * @return
	 */
	public String jsonTotalTypeCId(String areaId);

	/**
	 * data of total, border，首页echarts - total 省级数据加载（name - x轴）
	 *
	 * @param userId
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonTotalTypeBErrname(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of total, border，首页echarts - total 省级数据加载（name - x轴）
	 *
	 * @param userId
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonTotalTypeBErrInfo(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of total, border，首页echarts - total 市级数据加载（异常区域name，x轴）
	 *
	 * @param userId
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonTotalTypeCErrname(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of total, border，首页echarts - total 市级数据加载（异常区域name，x轴）
	 *
	 * @param userId
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonTotalTypeCErrInfo(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of total, border，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 *
	 * @param userId
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonTotalTypeDErrname(String userId, String areaId, String startTime, String endTime) throws ParseException;

	/**
	 * data of total, border，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 *
	 * @param userId
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> jsonTotalTypeDErrInfo(String userId, String areaId, String startTime, String endTime) throws ParseException;
}