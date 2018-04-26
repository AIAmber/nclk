package cn.com.sparknet.nclk.dao;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;

/**
 * 系统登录
 * @author wuyl
 *
 */
@Repository
public class LoginDao {
	
	@Resource
    private BaseDao baseDao;
	
	/**
	 * 查询用户
	 */
	public Map<String,Object> findUser(String username) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT USER_ID,NAME,USERNAME,PASSWORD,LOGIN_ERROR_COUNT,STATE FROM T_NCLK_USER WHERE USERNAME = ? ");
		return baseDao.findMapBySql(sql.toString(),new Object[]{username});
	}
	
	/**
	 * 更新用户登录信息
	 */
	public void updateUserLoginInfo(String userId,long loginErrorCount,String state) throws Exception {
		String hql="UPDATE T_NCLK_USER SET LOGIN_DATE = ?,LOGIN_ERROR_COUNT=?,STATE=? WHERE USER_ID=?";
		baseDao.updateBySql(hql, new Object[]{new Date(),loginErrorCount,state,userId});
	}

}