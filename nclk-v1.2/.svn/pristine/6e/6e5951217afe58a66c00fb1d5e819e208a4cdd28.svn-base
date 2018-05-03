package cn.com.sparknet.nclk.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
/**
 * 首页echarts数据
 * @author lirj
 */

@Repository
public class DashboardDao {
	
	@Resource
	private BaseDao baseDao;

	/**
	 * data of pie, error num， 已接入时钟总数 （总的时间，暂不启用）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> cardTotal(String stateError, String stateDisable, String stateNormal) {
		// data - pie
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT E.STATE FROM T_NCLK_EXAMROOM E WHERE E.STATE in (?, ?, ?) "); // 列举考场时钟状态，->时钟总数，异常数，正常数，停用数

		return baseDao.findListBySql(sql.toString(), new Object[]{stateError, stateDisable, stateNormal});
	}

	/**
	 * 查询用户拥有的所有区域 （采用monitor中Dao层的findAreaNodes方法）
	 *
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> findAreaNodes(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.AREA_NAME \"name\",t.AREA_ID \"id\",t.PARENT_AREA_ID \"pId\" from T_NCLK_AREA t ");
		sql.append("INNER JOIN T_NCLK_USER_AREA ua on ua.AREA_ID = t.AREA_ID ");
		sql.append("WHERE ua.USER_ID = ? and t.STATE = 'A' ORDER BY t.ORD asc");
		return baseDao.findListBySql(sql.toString(), new Object[]{userId});
	}

	/**
	 * 权限下所有考点的异常及正常的时钟数量 （采用monitor中Dao层的getClockCount方法）
	 *
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getClockCount(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("COUNT(CASE WHEN r.STATE = 'normal' THEN 'normal' END) 'normalCount',");
		sql.append("COUNT(CASE WHEN r.STATE = 'error' THEN 'error' END) 'errorCount', ");
		sql.append("COUNT(CASE WHEN r.STATE = 'disable' THEN 'disable' END) 'disableTotal' ");
		sql.append("FROM T_NCLK_EXAMROOM r ");
		sql.append("INNER JOIN T_NCLK_ENDPOINT e ON e.ENDPOINT_ID = r.ENDPOINT_ID ");
		sql.append("INNER JOIN T_NCLK_USER_ENDPOINT ue ON ue.ENDPOINT_ID = e.ENDPOINT_ID ");
		sql.append("INNER JOIN T_NCLK_AREA qu ON qu.AREA_ID = e.AREA_ID ");
		sql.append("WHERE e.STATE = 'A' AND qu.STATE = 'A' AND ue.USER_ID = ? ");
		return baseDao.findMapBySql(sql.toString(), new Object[]{userId});
	}
	
	/**
	 * data of pie, error num， 便于统计错误类型的数量， area省级
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listErrorType(String areaPid, String startTime, String endTime) throws ParseException {
		// data - pie
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT ERROR_TYPE ");
		sql.append(" FROM NCLK.T_NCLK_EXAMROOM_LOG_HIS H, NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P, " +
				" NCLK.T_NCLK_AREA A, NCLK.T_NCLK_AREA B, NCLK.T_NCLK_AREA C ");
		sql.append(" WHERE H.EXAMROOM_ID = E.EXAMROOM_ID AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID " +
				" AND A.PARENT_AREA_ID = B.AREA_ID AND B.PARENT_AREA_ID = C.AREA_ID AND C.AREA_ID = ? ");
		params.add(areaPid);
		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			sql.append(" and H.ERROR_DATE > ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 3);
			String dashTime = calendar.getTime().toString();
			params.add(calendar.getTime());
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			sql.append(" and H.ERROR_DATE between ? and ? ");
			params.add(dateFormat.parse(startTime));
			params.add(dateFormat.parse(endTime));
		}
		sql.append(" GROUP BY H.ERROR_TYPE, H.EXAMROOM_ID; "); // 同一时钟出现过的错误类型, 省级

		List list = baseDao.findListBySql(sql.toString(), params.toArray());

		return list;
	}

	/**
	 * data of pie, error num， 便于统计错误类型的数量， area 市级
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listErrorTypeB(String areaId, String startTime, String endTime) throws ParseException {
		// data - pie
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT H.ERROR_TYPE ");
		sql.append(" FROM NCLK.T_NCLK_EXAMROOM_LOG_HIS H, NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P, NCLK.T_NCLK_AREA A, " +
				" NCLK.T_NCLK_AREA B ");
		sql.append(" WHERE H.EXAMROOM_ID = E.EXAMROOM_ID AND P.ENDPOINT_ID = E.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID AND " +
				" A.PARENT_AREA_ID = B.AREA_ID AND A.STATE = 'A' AND B.STATE = 'A' AND P.STATE = 'A' AND B.AREA_ID = ? ");
		params.add(areaId);
		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			sql.append(" and H.ERROR_DATE > ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 3);
			params.add(calendar.getTime());
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			sql.append(" and H.ERROR_DATE between ? and ? ");
			params.add(dateFormat.parse(startTime));
			params.add(dateFormat.parse(endTime));
		}
		sql.append(" GROUP BY H.ERROR_TYPE, E.EXAMROOM_ID "); // 同一时钟出现过的错误类型, 市级

		List list = baseDao.findListBySql(sql.toString(), params.toArray());

		return list;
	}

	/**
	 * data of pie, error num， 便于统计错误类型的数量， endpoint 区县级
	 * @param
	 * @return List
	 * @throws ParseException
	 */
	public List<Map<String, Object>> listErrorTypeC(String areaId, String startTime, String endTime) throws ParseException{
		// data - pie
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT H.ERROR_TYPE ");
		sql.append(" FROM NCLK.T_NCLK_EXAMROOM_LOG_HIS H, NCLK.T_NCLK_EXAMROOM E, NCLK.T_NCLK_ENDPOINT P, NCLK.T_NCLK_AREA A ");
		sql.append(" WHERE H.EXAMROOM_ID = E.EXAMROOM_ID AND P.ENDPOINT_ID = E.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID AND " +
				" A.STATE = 'A' AND P.STATE = 'A' AND A.AREA_ID = ? ");
		params.add(areaId);
		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			sql.append("and H.ERROR_DATE > ? ");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 3);
			params.add(calendar.getTime());
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			sql.append("and H.ERROR_DATE between ? and ? ");
			params.add(dateFormat.parse(startTime));
			params.add(dateFormat.parse(endTime));
		}
		sql.append(" GROUP BY H.ERROR_TYPE, E.EXAMROOM_ID "); // 同一时钟出现过的错误类型, 区县级
		List list = baseDao.findListBySql(sql.toString(), params.toArray());

		return list;
	}

	/**
	 * data of line, 便于统计异常出现的次数, {normal:xxx; error:xxx} （亦可使用monitor方法）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listLine(String userId) {
		// data - pie
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT E.STATE ");
		sql.append(" FROM T_NCLK_EXAMROOM E, T_NCLK_USER_ENDPOINT UP ");
		sql.append(" WHERE E.ENDPOINT_ID = UP.ENDPOINT_ID AND UP.USER_ID = ?; ");

		return baseDao.findListBySql(sql.toString(), new Object[]{userId});
	}

	/**
	 * data of total, 城市名称, cities
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeA(String areaPid, String userId) {
		// data - total
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.AREA_NAME ");
		sql.append(" FROM T_NCLK_AREA A, T_NCLK_AREA B, T_NCLK_ENDPOINT P, T_NCLK_EXAMROOM E, T_NCLK_USER_ENDPOINT UP ");
		sql.append(" WHERE A.AREA_TYPE = 2 AND B.AREA_TYPE = 3 AND B.PARENT_AREA_ID = A.AREA_ID AND P.AREA_ID = B.AREA_ID " +
				" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND E.ENDPOINT_ID = UP.ENDPOINT_ID AND A.STATE = 'A' AND P.STATE = 'A'" +
				" AND A.PARENT_AREA_ID = ? AND UP.USER_ID = ? GROUP BY A.AREA_NAME; "); // 便于获取下拉框下级菜单的id

		return baseDao.findListBySql(sql.toString(), new Object[]{areaPid, userId});
	}

	/**
	 * data of total, 区域名称, district - name （查询某市有哪些区域，用于展示在echarts中作X轴）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeBName(String areaPid, String userId) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.AREA_NAME ");
		sql.append(" FROM T_NCLK_AREA A, T_NCLK_ENDPOINT P, T_NCLK_EXAMROOM E, T_NCLK_USER_ENDPOINT UP ");
		sql.append(" WHERE A.AREA_TYPE = 3 AND P.AREA_ID = A.AREA_ID AND E.ENDPOINT_ID = P.ENDPOINT_ID AND E.ENDPOINT_ID = " +
				" UP.ENDPOINT_ID AND A.STATE = 'A' AND P.STATE = 'A' AND A.PARENT_AREA_ID = ? AND UP.USER_ID = ? " +
				" GROUP BY A.AREA_NAME; " ); // 通过当前城市id（areaPid）获取区县级name

		return baseDao.findListBySql(sql.toString(), new Object[]{areaPid, userId});
	}

	/**
	 * data of total, 每级区域id, district - id （查询某市有哪些区域，用于展示在echarts中作X轴）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeId(String areaPid) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT AREA_ID ");
		sql.append(" FROM T_NCLK_AREA A ");
		sql.append(" WHERE A.STATE = 'A' AND A.PARENT_AREA_ID = ?");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaPid}); // 便于获取下拉框下级菜单的id
	}

	/**
	 * data of total, 区域id, district - id （出现异常考场所在考点的区域id）， {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeBNum() {
		// data - total, num
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT AREA_ID ");
		sql.append(" FROM T_NCLK_ENDPOINT P ");
		sql.append(" WHERE P.ENDPOINT_ID ");
		sql.append(" IN (SELECT ENDPOINT_ID FROM T_NCLK_EXAMROOM E WHERE E.STATE = \"error\") ");
		sql.append(" AND P.STATE = 'A' ");

		return baseDao.findListBySql(sql.toString(), new Object[]{});
	}

	/**
	 * data of total, 父级区域id, district - id （出现异常考场所在考点的父级区域id），
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeBFatherNum() {
		// data - total, num
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.PARENT_AREA_ID ");
		sql.append(" FROM T_NCLK_AREA A, T_NCLK_ENDPOINT P, T_NCLK_EXAMROOM E ");
		sql.append(" WHERE E.STATE = \"error\" AND P.ENDPOINT_ID = E.ENDPOINT_ID AND A.AREA_ID = P.AREA_ID AND " +
				" A.STATE = 'A' AND P.STATE = 'A'; "); // 查询出错的父级区域id及数量

		return baseDao.findListBySql(sql.toString(), new Object[]{});
	}

	/**
	 * data of total, 考点id, endpoint - in （查询某区县有哪些考点，收集id用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeCName(String areaId, String userId) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT P.ENDPOINT_NAME ");
		sql.append(" FROM T_NCLK_ENDPOINT P, T_NCLK_EXAMROOM E, T_NCLK_USER_ENDPOINT UP ");
		sql.append(" WHERE E.ENDPOINT_ID = P.ENDPOINT_ID AND E.ENDPOINT_ID = UP.ENDPOINT_ID AND P.STATE = 'A' " +
				" AND P.AREA_ID = ? AND UP.USER_ID = ? GROUP BY P.ENDPOINT_NAME");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaId, userId});
	}

	/**
	 * data of total, 考点id, endpoint - in （查询某区县有哪些考点，收集id用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeCId(String areaId) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ENDPOINT_ID FROM T_NCLK_ENDPOINT P WHERE P.STATE = 'A' AND P.AREA_ID = ? ");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaId});
	}

	/**
	 * data of total, 异常省name, endpoint - in （查询某省有哪些考点异常，收集name用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeBErrname(String areaName) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT B.AREA_NAME ");
		sql.append(" FROM T_NCLK_EXAMROOM E, T_NCLK_ENDPOINT P, T_NCLK_AREA A, T_NCLK_AREA B ");
		sql.append(" WHERE E.STATE = \"error\" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID AND " +
				" A.PARENT_AREA_ID = B.AREA_ID AND A.STATE = 'A' AND B.STATE = 'A' AND P.STATE = 'A' AND B.AREA_NAME = ? ");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaName});
	}

	/**
	 * data of total, 异常市name, endpoint - in （查询某市有哪些考点异常，收集name用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeCErrname(String areaName, String areaPid) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.AREA_NAME ");
		sql.append("FROM T_NCLK_EXAMROOM E, T_NCLK_ENDPOINT P, T_NCLK_AREA A ");
		sql.append(" WHERE E.STATE = \"error\" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.AREA_ID = A.AREA_ID AND A.AREA_NAME = ? " +
				" AND A.PARENT_AREA_ID= ? AND A.STATE = 'A' AND P.STATE = 'A'; ");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaName, areaPid});
	}

	/**
	 * data of total, 异常考点name, endpoint - in （查询某区县有哪些考场异常，收集name用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeDErrname(String endpointName) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT P.ENDPOINT_NAME ");
		sql.append(" FROM T_NCLK_EXAMROOM E, T_NCLK_ENDPOINT P ");
		sql.append(" WHERE E.STATE = \"error\" AND E.ENDPOINT_ID = P.ENDPOINT_ID AND P.STATE = 'A' AND P.ENDPOINT_NAME = ? ");

		return baseDao.findListBySql(sql.toString(), new Object[]{endpointName});
	}

	
}
