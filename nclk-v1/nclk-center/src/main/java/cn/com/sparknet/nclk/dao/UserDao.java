package cn.com.sparknet.nclk.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;
import cn.com.sparknet.common.dao.Page;
import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.util.StringUtil;

/**
 * 用户管理
 * @author wuyl
 *
 */
@Repository
public class UserDao {
	
	@Resource
	private BaseDao baseDao;
	
	/**
	 * 修改用户密码
	 * @param userid 用户id
	 * @param password 密码
	 * @throws Exception
	 */
	public void updatePassword(String userid,String password) throws Exception {
		String sql = " UPDATE T_NCLK_USER SET PASSWORD = ? WHERE USER_ID = ? ";
		baseDao.updateBySql(sql, new Object[]{password,userid});
	}
	
	/**
	 * 根据用户id查询加密之后的用户密码
	 * @param userid 用户名
	 * @return 
	 * @throws Exception
	 */
	public Map<String,Object> findPasswordByUserId(String userid) throws Exception {
		String sql = " SELECT PASSWORD FROM T_NCLK_USER WHERE USER_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{userid});
	}
	
	/**
	 * 查询用户头像
	 * @param id 用户id
	 * @return
	 * @throws Exception
	 */
	public List<InputStream> findUserPhotoById(String id) throws Exception {
		String sql = " SELECT PHOTO FROM T_NCLK_USER WHERE USER_ID = ? ";
		return baseDao.findBlobBySql(sql, new Object[]{id});
	}
	
	/**
	 * 删除用户头像
	 * @param userId
	 */
	/*public Integer deleteUserPhotoById(String userId){
		String sql = "";
		return baseDao.findIntByHql(sql, new Object(){userId});
	}*/
	
	/**
	 * 查询用户所管理的区域信息
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findAreaByUserId(String userId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT C.AREA_ID,PARENT_AREA_ID,AREA_NAME from T_NCLK_AREA A,T_NCLK_USER B,T_NCLK_USER_AREA C ");
		sb.append(" WHERE C.USER_ID = B.USER_ID AND C.AREA_ID = A.AREA_ID AND A.STATE = 'A' ");
		sb.append(" AND B.USER_ID = ? ");
		return baseDao.findListBySql(sb.toString(), new Object[]{userId});
	}
	
	/**
	 * 查询用户所管理的考点信息
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findEndPointByUserId(String userId) throws Exception {
		String sql = "";
		return baseDao.findListBySql(sql, new Object[]{userId});
	}
	
	/**
	 * 查询用户信息
	 * @param limit 
	 * @param start 
	 * @return
	 *
	 */
	public Page findUsers(String start, String limit,String searchText)  {
		List<String> params= new ArrayList<>();
		StringBuilder builder =new StringBuilder();
		builder.append(" SELECT USER_ID,NAME,USERNAME,PASSWORD,LOGIN_ERROR_COUNT FROM T_NCLK_USER ");
		if(StringUtils.isNotBlank(searchText)) {
			builder.append("WHERE NAME LIKE ? OR USERNAME LIKE ?");
			params.add("%"+ searchText +"%");
			params.add("%"+ searchText +"%");
		}
		return baseDao.findPageAllBySql(builder.toString(), start, limit, params.toArray());
	}


	/**
	 * 新增用户
	 * @param userId
	 * @param perName
	 * @param userName
	 * @param password
	 */
	public int addUser(String userId, String perName, String userName, String password) {
		String sql ="INSERT INTO T_NCLK_USER(USER_ID,NAME,USERNAME,PASSWORD,CREATE_DATE,LOGIN_ERROR_COUNT,STATE,ORD) VALUES(?,?,?,?,?,?,?,?)";
		return baseDao.updateBySql(sql, new Object[] {userId,perName,userName,password,new Date(),0,"A",1});
	}

	
}
