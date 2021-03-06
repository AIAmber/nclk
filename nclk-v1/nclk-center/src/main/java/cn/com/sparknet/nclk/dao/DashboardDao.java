package cn.com.sparknet.nclk.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;
import cn.com.sparknet.common.util.JsonUtil;

/**
 * 首页echarts数据
 * @author lirj
 */

@Repository
public class DashboardDao {
	
	@Resource
	private BaseDao baseDao;
	private JsonUtil JsonUtil;
	
	/**
	 * data of pie, error num， 便于统计错误类型的数量
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listErrorType() {
		// data - pie
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ERROR_TYPE FROM T_NCLK_EXAMROOM_LOG_HIS GROUP BY ERROR_TYPE, EXAMROOM_ID"); // 同一时钟出现过的错误类型
//		List<Map<String, Object>> result = baseDao.findListBySql(sql.toString(), new Object[]{});

		return baseDao.findListBySql(sql.toString(), new Object[]{});
	}

	/**
	 * data of line, 便于统计异常出现的次数, {normal:xxx; error:xxx}
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listLine() {
		// data - pie
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT STATE FROM T_NCLK_EXAMROOM ");

		return baseDao.findListBySql(sql.toString(), new Object[]{});
	}

	/**
	 * data of total, 城市名称, cities
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeA() {
		// data - total
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT AREA_NAME FROM T_NCLK_AREA WHERE AREA_TYPE = 2");

		return baseDao.findListBySql(sql.toString(), new Object[]{});
	}

	/**
	 * data of total, 区域名称, district - name （查询某市有哪些区域，用于展示在echarts中作X轴）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeBName(String areaPid) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT AREA_NAME FROM T_NCLK_AREA WHERE AREA_TYPE = 3 and PARENT_AREA_ID = ?");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaPid});
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
		sql.append(" SELECT AREA_ID FROM T_NCLK_AREA WHERE PARENT_AREA_ID = ?");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaPid});
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
		sql.append(" SELECT AREA_ID FROM T_NCLK_ENDPOINT WHERE ENDPOINT_ID IN " +
				"(SELECT ENDPOINT_ID FROM T_NCLK_EXAMROOM where STATE = \"error\") ");

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
		sql.append(" SELECT A.PARENT_AREA_ID FROM T_NCLK_AREA A, T_NCLK_ENDPOINT P, T_NCLK_EXAMROOM E where " +
				"E.STATE = \"error\" and P.ENDPOINT_ID = E.ENDPOINT_ID and A.AREA_ID = P.AREA_ID; "); // 查询出错的父级区域数量

		return baseDao.findListBySql(sql.toString(), new Object[]{});
	}

	/**
	 * data of total, 考点id, endpoint - in （查询某区县有哪些考点，收集id用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeCName(String areaId) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ENDPOINT_NAME FROM NCLK.T_NCLK_ENDPOINT P WHERE AREA_ID = ? ");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaId});
	}

	/**
	 * data of total, 考点id, endpoint - in （查询某区县有哪些考点，收集id用于数据查询）
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTotalTypeCNum(String areaId) {
		// data - total, name
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ENDPOINT_ID FROM NCLK.T_NCLK_ENDPOINT P WHERE AREA_ID = ? ");

		return baseDao.findListBySql(sql.toString(), new Object[]{areaId});
	}
	
}
