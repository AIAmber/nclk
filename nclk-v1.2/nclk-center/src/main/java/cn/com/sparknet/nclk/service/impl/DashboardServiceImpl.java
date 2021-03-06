package cn.com.sparknet.nclk.service.impl;

import cn.com.sparknet.nclk.dao.DashboardDao;
import cn.com.sparknet.nclk.service.DashboardService;
import cn.com.sparknet.nclk.util.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页echarts数据
 * @author lirj
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Resource
	private DashboardDao dashboardDao;

	@Override
	public Map<String, Object> getClockCount(String userId) {
		Map<String, Object> map = dashboardDao.getClockCount(userId);
		map.put("enableTotal", Integer.parseInt(map.get("normalCount").toString()) + Integer.parseInt(map.get("errorCount").toString()));
		return map;
	}

	/**
	 * change
	 * change
	 * change
	 * data of pie， 首页echarts - pie 省级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> jsonErrorTypeForEcharts(String userId, String areaPid, String startTime, String endTime) throws ParseException{
		// Json - pie
		List<Map<String, Object>> list = dashboardDao.listErrorType(userId, areaPid, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of pie， 首页echarts - pie 市级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> jsonErrorTypeForEchartsB(String userId, String areaId, String startTime, String endTime) throws ParseException{
		// Json - pie
		List<Map<String, Object>> list = dashboardDao.listErrorTypeB(userId, areaId, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of pie， 首页echarts - pie 县区级数据加载
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> jsonErrorTypeForEchartsC(String userId, String areaId, String startTime, String endTime) throws ParseException{
		// Json - pie
		List<Map<String, Object>> list = dashboardDao.listErrorTypeC(userId, areaId, startTime, endTime);
		return list;
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
	 * change
	 * change
	 * change
	 * data of total, border - name，首页echarts - total 省级数据加载（name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> jsonTotalTypeBErrname(String userId, String areaId, String startTime, String endTime) throws ParseException {
		// Json - total
		List<Map<String, Object>> list = dashboardDao.listTotalTypeBErrname(userId, areaId, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of total, border - name，首页echarts - total 省级数据加载（name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> jsonTotalTypeBErrInfo(String userId, String areaId, String startTime, String endTime) throws ParseException {
		// Json - total
		List<Map<String, Object>> list = dashboardDao.listTotalTypeBErrInfo(userId, areaId, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of total, border - name，首页echarts - total 市级数据加载（异常区域name，x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> jsonTotalTypeCErrname(String userId, String areaId, String startTime, String endTime) throws ParseException {
		// Json - total
		List<Map<String, Object>> list = dashboardDao.listTotalTypeCErrname(userId, areaId, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of total, border - name，首页echarts - total 市级数据加载（异常区域name，x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> jsonTotalTypeCErrInfo(String userId, String areaId, String startTime, String endTime) throws ParseException {
		// Json - total
		List<Map<String, Object>> list = dashboardDao.listTotalTypeCErrInfo(userId, areaId, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of total, border - name，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> jsonTotalTypeDErrname(String userId, String areaId, String startTime, String endTime) throws ParseException {
		// Json - total
		List<Map<String, Object>> list = dashboardDao.listTotalTypeDErrname(userId, areaId, startTime, endTime);
		return list;
	}

	/**
	 * change
	 * change
	 * change
	 * data of total, border - name，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> jsonTotalTypeDErrInfo(String userId, String areaId, String startTime, String endTime) throws ParseException {
		// Json - total
		List<Map<String, Object>> list = dashboardDao.listTotalTypeDErrInfo(userId, areaId, startTime, endTime);
		return list;
	}

}
