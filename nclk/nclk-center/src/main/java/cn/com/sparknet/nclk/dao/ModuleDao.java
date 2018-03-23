package cn.com.sparknet.nclk.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;

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
		sql.append(" SELECT M.MODULE_ID,PARENT_MODULE_ID,MODULE_NAME,MODULE_URL,MODULE_ICON ");
		sql.append(" FROM T_NCLK_MODULE M,T_NCLK_USER_AUTH A,T_NCLK_USER U ");
		sql.append(" WHERE M.MODULE_ID = A.MODULE_ID AND A.USER_ID = U.USER_ID AND M.STATE = 'A' ");
		sql.append(" AND U.USERNAME = ? ");
		return baseDao.findListBySql(sql.toString(), new Object[]{username});
	}
	
	/**
	 * 权限模块获取
	 * @return
	 */
	public List<Map<String,Object>> getModuleInfo(){
		String sql="SELECT MODULE_ID,PARENT_MODULE_ID,MODULE_NAME FROM T_NCLK_MODULE";
		return baseDao.findListBySql(sql, new Object[] {});
	} 
}
