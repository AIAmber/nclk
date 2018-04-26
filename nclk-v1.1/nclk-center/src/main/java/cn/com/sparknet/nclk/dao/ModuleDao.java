package cn.com.sparknet.nclk.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

/**
 * 模块管理
 * @author wuyl
 *
 */
@Repository
public class ModuleDao {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 根据用户名查询用户权限
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findUserIdByName(String username) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT M.MODULE_ID,M.PARENT_MODULE_ID,M.MODULE_NAME,M.MODULE_URL,M.MODULE_ICON,M.ORD ");
		sql.append(" FROM T_NCLK_MODULE M,T_NCLK_USER_AUTH A,T_NCLK_USER U ");
		sql.append(" WHERE M.MODULE_ID = A.MODULE_ID AND A.USER_ID = U.USER_ID AND M.STATE = 'A' ");
		sql.append(" AND U.USERNAME = ? ORDER BY M.ORD ASC ");
		return baseDao.findListBySql(sql.toString(), new Object[]{username});
	}
	
	/**
	 * 权限模块获取
	 * @return
	 */
	public List<Map<String,Object>> getModuleInfo(){
		String sql="SELECT MODULE_ID \"id\",PARENT_MODULE_ID \"pId\",MODULE_NAME \"name\" FROM T_NCLK_MODULE WHERE STATE='A' ORDER BY ORD";
		return baseDao.findListBySql(sql, new Object[] {});
	} 
	
	/**
	 * 查询用户所具有的区域权限
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getAreaByUserId(String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.AREA_ID,A.AREA_NAME,A.AREA_TYPE,A.PARENT_AREA_ID ");
		sb.append(" FROM T_NCLK_AREA A,T_NCLK_USER U,T_NCLK_USER_AREA UA ");
		sb.append(" WHERE A.AREA_ID = UA.AREA_ID ");
		sb.append(" AND U.USER_ID = UA.USER_ID ");
		sb.append(" AND A.STATE = 'A' ");
		sb.append(" AND U.USER_ID = ? ");
		return baseDao.findListBySql(sb.toString(), new Object[] {userId});
	}

	/**
	 * 查询用户所具有的考点权限
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getEndPointByUserId(String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT E.ENDPOINT_ID,E.ENDPOINT_NAME,E.AREA_ID FROM T_NCLK_ENDPOINT E,T_NCLK_USER_ENDPOINT EU ");
		sb.append(" WHERE E.ENDPOINT_ID = EU.ENDPOINT_ID ");
		sb.append(" AND E.STATE = 'A' ");
		sb.append(" AND EU.USER_ID = ? ");
		return baseDao.findListBySql(sb.toString(),new Object[] {userId});
	}
}
