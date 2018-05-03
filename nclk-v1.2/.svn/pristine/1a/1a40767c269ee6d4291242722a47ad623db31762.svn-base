package cn.com.sparknet.nclk.service;

import cn.com.sparknet.nclk.json.ListJson;

import java.text.ParseException;
import java.util.Map;

/**
 * 首页echarts数据
 * @author lirj
 */
public interface DashboardService {

	/**
	 * data of title, card 首页小卡片数据加载
	 * @param stateError
	 * @param stateDisable
	 * @param stateNormal
	 * @return
	 */
	public String cardTotal(String stateError, String stateDisable, String stateNormal);

	/**
	 * data of title, card 首页小卡片数据加载 （采用monitor中的方法）
	 * @param userId
	 * @return
	 */
	public ListJson findAllArea(String userId);
	/**
	 * data of title, card 首页小卡片数据加载 （采用monitor中的方法）
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getClockCount(String userId);
	/**
	 * data of pie， 首页echarts - pie 省级数据加载
	 * @param areaPid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String jsonErrorTypeForEcharts(String areaPid, String startTime, String endTime) throws ParseException;
	/**
	 * data of pie， 首页echarts - pie 市级数据加载
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String jsonErrorTypeForEchartsB(String areaId, String startTime, String endTime) throws ParseException;
	/**
	 * data of pie， 首页echarts - pie 县区级数据加载
	 * @param areaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String jsonErrorTypeForEchartsC(String areaId, String startTime, String endTime) throws ParseException;
	/**
	 * data of line， 首页echarts - line 省级数据加载
	 * @param userId
	 * @return
	 */
	public String jsonLine(String userId);
	/**
	 * data of total, citys， 首页echarts - total 省级数据加载（num - y轴）
	 * @param areaPid, userId
	 * @return
	 */
	public String jsonTotalTypeA(String areaPid, String userId);
	/**
	 * data of total, border， 首页echarts - total 市级数据加载（name - x轴）
	 * @param areaPid, userId
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeBName(String areaPid, String userId);
	/**
	 * data of total, border， 首页echarts - total 数据加载（获取当前区域的id 或 parentId）
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeId( String areaPid );
	/**
	 * data of total, border - num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
	 * 首页echarts - total 市级数据加载（num - y轴）
	 * @param errorNum
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeBNum(String errorNum, String areaPid);
	/**
	 * data of total, border - father num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
	 * 首页echarts - total 市级数据加载，出错的父级区域id及数量
	 * @param areaId
	 * @return
	 */
	public String jsonTotalTypeBFatherNum(String areaId);
	/**
	 * data of total, border，首页echarts - total 市级数据加载（name - x轴）
	 * @param areaId
	 * @return
	 */
	public String jsonTotalTypeCName(String areaId, String userId);
	/**
	 * data of total, border，首页echarts - total 县区级数据加载（id） -> num - y轴
	 * @param areaId
	 * @return
	 */
	public String jsonTotalTypeCId(String areaId);
	/**
	 * data of total, border，首页echarts - total 省级数据加载（name - x轴）
	 * @param areaName
	 * @return
	 */
	public String jsonTotalTypeBErrname(String areaName);
	/**
	 * data of total, border，首页echarts - total 市级数据加载（异常区域name，x轴）
	 * @param areaName
	 * @param areaPid
	 * @return
	 */
	public String jsonTotalTypeCErrname(String areaName, String areaPid);
	/**
	 * data of total, border，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 * @param endpointName
	 * @return
	 */
	public String jsonTotalTypeDErrname(String endpointName);
}
