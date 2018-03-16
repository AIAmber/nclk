package cn.com.sparknet.nclk.dao;

import java.util.Date;
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
	 * data of total
	 * @param errorType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> mapErrorType(String errorType) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT H.ERROR_TYPE FROM T_NCLK_EXAMROOM_LOG_HIS H ");
		return baseDao.findMapBySql(sql.toString(), new Object[]{errorType});
	}
	
	public String jsonErrorType(Map<String, Object> mapErrorType){
		return JsonUtil.mapToJson(mapErrorType);
	}
	
}
