package cn.com.sparknet.nclk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sparknet.nclk.dao.DashboardDao;
import cn.com.sparknet.nclk.service.DashboardService;
import cn.com.sparknet.common.util.JsonUtil;

/**
 * 首页echarts数据
 * @author lirj
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService{
	
	@Resource
	private DashboardDao dashboardDao;
	
	@Override
	public String jsonErrorType(String errorType){
		// Json test
		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.listToJson(dashboardDao.listErrorType());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * data of pie
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonErrorTypeForEcharts(String errorType){
		// Json - pie
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listErrorType()) {
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
	 * data of line
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonLine(String errorType) {
		// Json - line
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 计算同一错误出现的次数
		for (Map<String, Object> obj:dashboardDao.listLine()) {
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
	 * data of total, citys
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeA(String errorNum) {
		// Json total
		String json = null;
		// 将数据转换成Json格式
		try {
			json = JsonUtil.listToJson(dashboardDao.listTotalTypeA());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * data of total, border - name
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeBName(String errorNum, String areaPid) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeBName(areaPid));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}

	/**
	 * data of total, border - name
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
	 * data of total, border - name
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeCName(String areaId) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeCName(areaId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}

	/**
	 * data of total, border - name
	 * @param
	 * @return json
	 * @throws Exception
	 */
	@Override
	public String jsonTotalTypeCNum(String areaId) {
		// Json total
		String jsonName = null;

		// 将数据转换成Json格式
		try {
			jsonName = JsonUtil.listToJson(dashboardDao.listTotalTypeCNum(areaId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return jsonName;
	}
}
