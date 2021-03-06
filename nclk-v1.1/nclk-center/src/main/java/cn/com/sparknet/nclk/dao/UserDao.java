package cn.com.sparknet.nclk.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.sparknet.nclk.util.StringUtil;

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
	 * 查询用户列表信息
	 * @param start
	 * @param limit
	 * @param searchText
	 * @param state
	 * @return
	 */
	public Page findUsers(String start, String limit,String searchText,String state)  {
		List<String> params = new ArrayList<>();
		StringBuffer buffer =new StringBuffer();
		buffer.append(" SELECT USER_ID,NAME,USERNAME,PASSWORD,STATE FROM T_NCLK_USER WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchText)) {
			buffer.append(" AND ( NAME LIKE ? OR USERNAME LIKE ? ) ");
			params.add("%" + searchText + "%");
	        params.add("%" + searchText + "%");
		}
		if(StringUtils.isNotBlank(state)) {
			buffer.append("AND STATE= ? ");
			params.add(state);
		}
		buffer.append(" ORDER BY STATE,ORD,USERNAME");
		return baseDao.findPageAllBySql(buffer.toString(), start, limit, params.toArray());
	}

	
	/**
	 * 新增用户信息
	 * @param userId
	 * @param perName
	 * @param userName
	 * @param password
	 * @param state
	 * @param ord
	 * @param in
	 * @return
	 */
	public int addUser(String userId, String perName, String userName, String password,String state,String ord,byte [] in) {
		String sql ="INSERT INTO T_NCLK_USER(USER_ID,NAME,USERNAME,PASSWORD,CREATE_DATE,LOGIN_ERROR_COUNT,STATE,ORD,PHOTO) VALUES(?,?,?,?,?,?,?,?,?)";
		if(StringUtil.isNotEmpty(ord)) {
			int ordId=Integer.parseInt(ord);
			return baseDao.updateBySql(sql, new Object[] {userId,perName,userName,password,new Date(),0,state,ordId,in});
		}else {
			return baseDao.updateBySql(sql, new Object[] {userId,perName,userName,password,new Date(),0,state,null,in});
		}
	}
	
	
	/**
	 * 修改用户信息
	 * @param userId
	 * @param perName
	 * @param userName
	 * @param password
	 * @param state
	 * @param ord
	 * @param in
	 * @return
	 */
	public int updateUser(String userId, String perName, String userName, String password,String state,String ord,byte[] in) {
		String sql ="UPDATE  T_NCLK_USER SET NAME=?,USERNAME=?,PASSWORD=?,CREATE_DATE=?,LOGIN_ERROR_COUNT=?,STATE=?,ORD=?,PHOTO=? WHERE USER_ID=?";
		if(StringUtil.isNotEmpty(ord)) {
			int ordId=Integer.parseInt(ord);
			return baseDao.updateBySql(sql, new Object[] {perName,userName,password,new Date(),0,state,ordId,in,userId});
		}else {
			return baseDao.updateBySql(sql, new Object[] {perName,userName,password,new Date(),0,state,null,in,userId});
		}
		
	}
	
	/**
	 * 检查用户名是否存在
	 */
	public int findUsername(String userId,String userName) throws Exception {
		if(StringUtil.isEmpty(userId)){
			String sql="SELECT COUNT(*) FROM T_NCLK_USER WHERE USERNAME=?";
			return baseDao.findIntBySql(sql, new Object[]{userName});
		}else{
			String sql="SELECT COUNT(*) FROM T_NCLK_USER WHERE USER_ID=? AND USERNAME=?";
			return baseDao.findIntBySql(sql, new Object[]{userId,userName});
		}
	}
	
	/**
	 * 根据用户Id删除用户信息
	 * @param userId
	 */
	public void deleteUserInfoByUserId(String[] userIdList) {
		String flag="";
		for(String id:userIdList){
			flag+="?,";
		}
		if(flag.length()>0){
			flag=flag.substring(0, flag.length()-1);
		}
		String sql="UPDATE T_NCLK_USER SET STATE='X' WHERE USER_ID IN ("+flag+")";
		baseDao.updateBySql(sql, userIdList);
	}
	/**
	 * 根据用户Id查找用户信息
	 * @param userId
	 */
	public Map<String, Object> findUserInfoByUserId(String userId) {
		String sql="SELECT USER_ID,NAME,USERNAME,PASSWORD,STATE,ORD FROM T_NCLK_USER WHERE USER_ID=?";
		return baseDao.findMapBySql(sql, new Object[] {userId});
	}
	
	/**
	 * 根据用户Id查找用户权限
	 * @param userId
	 */
	public List<Map<String, Object>> findUserModuleByUserId(String userId) {
		String sql=" SELECT MODULE_ID FROM T_NCLK_USER_AUTH WHERE USER_ID = ? ";
		return baseDao.findListBySql(sql, new Object[] {userId});
	}
	/**
	 * 根据用户Id删除用户权限
	 * @param userId
	 */
	public void deleteUserModuleByUserId(String userId) {
		String sql=" DELETE  FROM T_NCLK_USER_AUTH WHERE USER_ID = ? ";
		baseDao.updateBySql(sql, new Object[] {userId});
	}

	/**
	 * 根据用户id保存用户模块权限
	 * @param userAuthId
	 * @param userId
	 * @param moduleId
	 */
	public void saveUserModuleByUserId(String userAuthId,String userId, String moduleId) {
		String sql="INSERT INTO T_NCLK_USER_AUTH(USER_AUTH_ID,USER_ID,MODULE_ID) VALUES(?,?,?) ";
		baseDao.updateBySql(sql, new Object[] {userAuthId,userId,moduleId});
	}

	/**
	 * 根据用户id查询用户管理区域
	 * @param userId
	 */
	public List<Map<String, Object>> findUserManageAreaInfo(String userId) {
		String sql=" SELECT  a.AREA_ID  FROM T_NCLK_USER_AREA a INNER JOIN T_NCLK_AREA b  ON a.AREA_ID=b.AREA_ID WHERE  a.USER_ID=? AND b.STATE='A' ";
		return baseDao.findListBySql(sql, new Object[] {userId});
	}
	/**
	 * 根据用户id查询用户管理考点
	 * @param userId
	 */
	public List<Map<String, Object>> findUserManageEndpointInfo(String userId) {
		String sql=" SELECT A.ENDPOINT_ID FROM T_NCLK_USER_ENDPOINT A,T_NCLK_ENDPOINT B WHERE A.ENDPOINT_ID = B.ENDPOINT_ID AND B.STATE = 'A' AND USER_ID = ? ";
		return baseDao.findListBySql(sql, new Object[] {userId});
	}
	
	/**
	 * 根据用户id保存用户管理区域
	 * @param userAreaId
	 * @param userId
	 * @param areaId
	 */
	public void saveUserAreaByUserId(String userAreaId,String userId, String areaId) {
		String sql="INSERT INTO T_NCLK_USER_AREA(USER_AREA_ID,USER_ID,AREA_ID) VALUES(?,?,?) ";
		baseDao.updateBySql(sql, new Object[] {userAreaId,userId,areaId});
	}
	
	/**
	 * 根据用户Id删除用户管理区域
	 * @param userId
	 */
	public void deleteUserEndPointByUserId(String userId) {
		String sql=" DELETE  FROM T_NCLK_USER_ENDPOINT WHERE USER_ID = ? ";
		baseDao.updateBySql(sql, new Object[] {userId});
	}
	/**
	 * 根据用户Id删除用户管理考点
	 * @param userId
	 */
	public void deleteUserAreaByUserId(String userId) {
		String sql=" DELETE  FROM T_NCLK_USER_AREA WHERE USER_ID = ? ";
		baseDao.updateBySql(sql, new Object[] {userId});
	}
	/**
	 * 根据用户id保存用户管理考点
	 * @param userEndPointId
	 * @param userId
	 * @param endPointId
	 */
	public void saveUserEndPointByUserId(String userEndPointId,String userId, String endPointId) {
		String sql="INSERT INTO T_NCLK_USER_ENDPOINT(USER_ENDPOINT_ID,USER_ID,ENDPOINT_ID) VALUES(?,?,?) ";
		baseDao.updateBySql(sql, new Object[] {userEndPointId,userId,endPointId});
	}
	
	/**
	 * 保存或者修改用户头像
	 * @param userId
	 * @param photo
	 * @return
	 * @throws IOException
	 */
	public int saveUserPhoto(String userId,byte[] photo) throws IOException {
		String hql="UPDATE T_NCLK_USER SET PHOTO = ? WHERE USER_ID = ? ";
		return baseDao.updateBySql(hql, new Object[]{photo,userId});
	}
	
	/**
	 * 解锁用户
	 */
	public void updateUserState(String[] idList) throws Exception {
		String flag="";
		for(String id:idList){
			flag+="?,";
		}
		if(flag.length()>0){
			flag=flag.substring(0, flag.length()-1);
		}
		String sql="UPDATE T_NCLK_USER set LOGIN_ERROR_COUNT = 0,STATE='A',LOCK_DATE=NULL WHERE USER_ID in ("+flag+")";
		baseDao.updateBySql(sql, idList);
	}
	
}
