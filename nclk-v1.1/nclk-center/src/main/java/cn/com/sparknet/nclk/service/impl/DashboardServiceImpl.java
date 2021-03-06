package cn.com.sparknet.nclk.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.sparknet.nclk.json.ListJson;
import org.springframework.stereotype.Service;

import cn.com.sparknet.nclk.dao.DashboardDao;
import cn.com.sparknet.nclk.service.DashboardService;
import cn.com.sparknet.nclk.util.JsonUtil;

/**
 * 首页echarts数据
 * @author lirj
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService{
	
	@Resource
	private DashboardDao dashboardDao;

	/**
	 * data of title, card 首页小卡片数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String cardTotal(String stateError, String stateDisable, String stateNormal){
		// Json - pie
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.cardTotal(stateError, stateDisable, stateNormal)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	@Override
	public ListJson findAllArea(String userId) {
		List<Map<String, Object>> list = dashboardDao.findAreaNodes(userId);
		ListJson listJson = new ListJson();
		listJson.setRows(list);
		return listJson;
	}

	@Override
	public Map<String, Object> getClockCount(String userId) {
		Map<String, Object> map = dashboardDao.getClockCount(userId);
		map.put("enableTotal", Integer.parseInt(map.get("normalCount").toString()) + Integer.parseInt(map.get("errorCount").toString()));
		return map;
	}

	/**
	 * data of pie， 首页echarts - pie 省级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonErrorTypeForEcharts(String areaPid, String startTime, String endTime) throws ParseException{
		// Json - pie
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listErrorType(areaPid, startTime, endTime)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * data of pie， 首页echarts - pie 市级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonErrorTypeForEchartsB(String areaId, String startTime, String endTime) throws ParseException{
		// Json - pie
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listErrorTypeB(areaId, startTime, endTime)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);
			
			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}
		
		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * data of pie， 首页echarts - pie 县区级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonErrorTypeForEchartsC(String areaId, String startTime, String endTime) throws ParseException{
		// Json - pie
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listErrorTypeC(areaId, startTime, endTime)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * data of line， 首页echarts - line 省级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonLine(String userId) {
		// Json - line
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listLine(userId)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);
			
			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}
		
		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		return json;
	}

	/**
	 * data of total, citys， 首页echarts - total 省级数据加载（num - y轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeA(String areaPid, String userId) {
		// Json total
		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.listToJson(dashboardDao.listTotalTypeA(areaPid, userId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * data of total, border - name， 首页echarts - total 市级数据加载（name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeBName(String areaPid, String userId) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeBName(areaPid, userId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}

	/**
	 * data of total, border - name， 首页echarts - total 数据加载（获取当前区域的id 或 parentId）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeId( String areaPid ) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeId(areaPid));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}

	/**
	 * data of total, border - num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
	 * 首页echarts - total 市级数据加载（num - y轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeBNum(String errorNum, String areaPid) {
		// Json - total
		String jsonNum = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listTotalTypeBNum()) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		try {
			jsonNum = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonNum;
	}

	/**
	 * data of total, border - father num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
     * 首页echarts - total 市级数据加载，出错的父级区域id及数量
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeBFatherNum(String areaId) {
		// Json - total
		String jsonNum = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listTotalTypeBFatherNum()) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		try {
			jsonNum = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonNum;
	}

	/**
	 * data of total, border - name，首页echarts - total 市级数据加载（name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeCName(String areaId, String userId) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeCName(areaId, userId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}

	/**
	 * data of total, border - name，首页echarts - total 县区级数据加载（id） -> num - y轴
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeCId(String areaId) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeCId(areaId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}

	/**
	 * data of total, border - name，首页echarts - total 省级数据加载（name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeBErrname(String areaName) {
		// Json - total
		String jsonErrname = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listTotalTypeBErrname(areaName)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		// 将数据转换成Json格式
		try {
			jsonErrname = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonErrname;
	}

	/**
	 * data of total, border - name，首页echarts - total 市级数据加载（异常区域name，x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeCErrname(String areaName, String areaPid) {
		// Json - total
		String jsonErrname = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listTotalTypeCErrname(areaName, areaPid)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		// 将数据转换成Json格式
		try {
			jsonErrname = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonErrname;
	}

	/**
	 * data of total, border - name，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeDErrname(String endpointName) {
		// Json - total
		String jsonErrname = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listTotalTypeDErrname(endpointName)) {
			// obj.values()和obj.values().toString() -> "[String]"
			// objString -> "String"
			String objString = obj.values().toString();
			objString = objString.substring(1, objString.length()-1);

			if (map.containsKey(objString)) {
				map.put(objString, map.get(objString).intValue() + 1);
			} else {
				map.put(objString, 1);
			}
		}

		// 将数据转换成Json格式
		try {
			jsonErrname = JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonErrname;
	}
}
