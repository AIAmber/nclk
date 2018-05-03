package cn.com.sparknet.nclk.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 区域管理
 * @author luogang
 *
 */
@Repository
public class AreaDao {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 根据用户名查询用户所能查看的省市区信息
	 * @param parentAreaId 父级区域id
	 * @param userName 用户名
	 * @return
	 */
	public List<Map<String, Object>> findProvince(String userName){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.AREA_ID,A.AREA_NAME,A.AREA_TYPE FROM T_NCLK_AREA A,T_NCLK_USER U,T_NCLK_USER_AREA UA ");
		sb.append(" WHERE A.AREA_ID = UA.AREA_ID ");
		sb.append(" AND U.USER_ID = UA.USER_ID ");
		sb.append(" AND A.STATE = 'A' ");
		sb.append(" AND A.PARENT_AREA_ID IS NULL ");
		sb.append(" AND U.USERNAME = ? ");
		return baseDao.findListBySql(sb.toString(), new Object[]{userName});
	}
	
	/**
	 * 根据用户名查询用户所能查看的省市区信息
	 * @param parentAreaId 父级区域id
	 * @param userName 用户名
	 * @return
	 */
	public List<Map<String, Object>> findChildrenByParentAreaId(String parentAreaId,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.AREA_ID,A.AREA_NAME,A.AREA_TYPE FROM T_NCLK_AREA A,T_NCLK_USER U,T_NCLK_USER_AREA UA ");
		sb.append(" WHERE A.AREA_ID = UA.AREA_ID ");
		sb.append(" AND U.USER_ID = UA.USER_ID ");
		sb.append(" AND A.STATE = 'A' ");
		sb.append(" AND A.PARENT_AREA_ID = ? ");
		sb.append(" AND U.USERNAME = ? ");
		return baseDao.findListBySql(sb.toString(), new Object[]{parentAreaId,userName});
	}
	
	/**
	 * 根据区域名称查询区域id值
	 * @return
	 */
	public Map<String,Object> findAreaIdByAreaName(String areaName){
		String sql = " SELECT AREA_ID FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' AND TNA.AREA_NAME = ? ";
		return baseDao.findMapBySql(sql, new Object[]{areaName});
	}
	
	/**
	 * 根据区域名称查询区域id值
	 * @return
	 */
	public Map<String,Object> findAreaIdByAreaNameANDParentAreaId(String areaName,String parentId){
		String sql = " SELECT AREA_ID FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' AND TNA.AREA_NAME = ? AND TNA.PARENT_AREA_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{areaName,parentId});
	}
	
	/**
	 * 根据区域id查询所属父级区域id
	 * @return
	 */
	public Map<String,Object> findParentAreaIdByAreaId(String areaId){
		String sql = " SELECT PARENT_AREA_ID FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' AND TNA.AREA_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{areaId});
	}
	
	/**
	 * 获取区域管理树的信息
	 * @return
	 */
	public List<Map<String,Object>> findAllAreaInfo(String areaId){
		StringBuffer buffer =new StringBuffer();
		if(StringUtil.isEmpty(areaId)) {
			buffer.append("SELECT AREA_NAME 'name',AREA_ID 'id',PARENT_AREA_ID 'pId' FROM T_NCLK_AREA ");
	        buffer.append("WHERE STATE = 'A' ORDER BY ORD ");
	        return baseDao.findListBySql(buffer.toString(), new Object[] {});
		}else {
			buffer.append("SELECT AREA_NAME 'name',AREA_ID 'id',PARENT_AREA_ID 'pId' FROM T_NCLK_AREA ");
	        buffer.append("WHERE STATE = 'A' AND PARENT_AREA_ID=? ORDER BY ORD");
	        return baseDao.findListBySql(buffer.toString(), new Object[] {areaId});
		}
		
        
	}
	/**
	 * 获取区域信息
	 * @return
	 */
	public List<Map<String,Object>> findAllArea(String state){
			String sql = " SELECT TNA.AREA_ID,TNA.PARENT_AREA_ID,TNA.AREA_NAME,TNA.AREA_TYPE FROM T_NCLK_AREA TNA WHERE TNA.STATE = ? ";
			return baseDao.findListBySql(sql, new Object[]{state});
	}
	/**
	 * 获取区域到考点四级树信息
	 * @return
	 */
	public List<Map<String,Object>> findAllAreaEndPoint(){
		StringBuffer buffer =new StringBuffer();
		buffer.append( "SELECT ENDPOINT_ID,ENDPOINT_NAME,AREA_ID FROM T_NCLK_ENDPOINT WHERE STATE='A'");
		return baseDao.findListBySql(buffer.toString(), new Object[]{});
	}
	
	/**
	 * 新增区域根据上级区域名称查找上级区域ID
	 * @return
	 */
	public Map<String,Object> findParentAreaIdByParentAreaName(String parentArea){
		String sql = " SELECT AREA_ID,AREA_NAME FROM T_NCLK_AREA  WHERE STATE = 'A' AND AREA_NAME= ? ";
		return baseDao.findMapBySql(sql, new Object[]{parentArea});
	}
	/**
	 * 根据区域id查询区域名称和所属父级区域id
	 * @return
	 */
	public Map<String,Object> findAreaNameByAreaId(String areaId){
		String sql = " SELECT AREA_ID,AREA_NAME,PARENT_AREA_ID FROM T_NCLK_AREA  WHERE STATE='A' AND AREA_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{areaId});
	}
	
	/**
	 *  根据父级区域ID获取区域ID和区域名称
	 * @return
	 */
	public List<Map<String, Object>> getChildAreaInfoByParentAreaId(String parentId){
		String sql = " SELECT AREA_NAME,AREA_ID,PARENT_AREA_ID FROM T_NCLK_AREA  WHERE STATE='A' AND PARENT_AREA_ID = ? ";
		return baseDao.findListBySql(sql, new Object[]{parentId});
	}

	/**
	 * 新增区域信息
	 * @param areaId
	 * @param areaName
	 * @param areaType
	 * @param ordId 
	 * @param parentArea
	 * @param ord
	 */
	
	public void addAreaInfo(String areaId, String areaName, String areaType, String parentAreaId, String state,String ord) {
		String sql ="INSERT INTO T_NCLK_AREA (AREA_ID,PARENT_AREA_ID,AREA_NAME,AREA_TYPE,CREATE_DATE,STATE,ORD) VALUES(?,?,?,?,?,?,?)";
		if (StringUtil.isNotEmpty(ord)) {
			int ordId=Integer.parseInt(ord);
			baseDao.updateBySql(sql, new Object[] {areaId,parentAreaId,areaName,areaType,new Date(),state,ordId});
		}else {
			baseDao.updateBySql(sql, new Object[] {areaId,parentAreaId,areaName,areaType,new Date(),state,null});
		}
	}
	/**
	 * 修改区域信息
	 * @param areaId
	 * @param areaName
	 * @param areaType
	 * @param ordId 
	 * @param parentArea
	 * @param ord
	 */
	
	public void updateAreaInfo(String areaId, String areaName, String areaType, String parentAreaId, String state,String ord) {
		String sql ="UPDATE T_NCLK_AREA SET AREA_NAME=?,PARENT_AREA_ID=?,AREA_TYPE=?,CREATE_DATE=?,STATE=?,ORD=? WHERE AREA_ID=?";
		if (StringUtil.isNotEmpty(ord)) {
			int ordId=Integer.parseInt(ord);
			baseDao.updateBySql(sql, new Object[] {areaName,parentAreaId,areaType,new Date(),state,ordId,areaId});
		}else {
			baseDao.updateBySql(sql, new Object[] {areaName,parentAreaId,areaType,new Date(),state,null,areaId});
		}
	}
	
	/**
	 *根据区域ID查找区域信息 
	 * @param areaId
	 * @return
	 */
	public Map<String, Object> findAreaInfoByAreaId(String areaId){
		String sql="SELECT AREA_ID,AREA_NAME, AREA_TYPE,PARENT_AREA_ID,ORD,STATE FROM T_NCLK_AREA WHERE AREA_ID=? ";
		return baseDao.findMapBySql(sql, new Object[] {areaId});
	}
	/**
	 *根据父级区域ID查找同级区域ID集
	 * @param areaId
	 * @return
	 */
	public List<Map<String, Object>> findAreaIdsByParentId(String parentId){
		String sql="SELECT AREA_ID FROM T_NCLK_AREA WHERE PARENT_AREA_ID=?";
		 return baseDao.findListBySql(sql, new Object[] {parentId});
	}
	
	
	/**
	 *根据区域ID删除区域信息 
	 * @param areaId
	 * @return
	 */
	public void deleteAreaInfoByAreaId(String areaId){
		String sql="UPDATE T_NCLK_AREA SET STATE='X' WHERE AREA_ID=? ";
		baseDao.updateBySql(sql, new Object[] {areaId});
	}
	/**
	 *根据区域ID删除考点信息 
	 * @param areaId
	 * @return
	 */
	public void deleteEndPointInfoByAreaId(String areaId){
		String sql="UPDATE T_NCLK_ENDPOINT  SET STATE='X' WHERE AREA_ID=? ";
		baseDao.updateBySql(sql, new Object[] {areaId});
	}
	/**
	 *根据区域ID恢复区域信息 
	 * @param areaId
	 * @return
	 */
	public void updateAreaInfoByAreaId(String areaId){
		String sql="UPDATE T_NCLK_AREA SET STATE='A' WHERE AREA_ID=? ";
		baseDao.updateBySql(sql, new Object[] {areaId});
	}
	/**
	 *根据区域ID恢复考点信息 
	 * @param areaId
	 * @return
	 */
	public void updateEndPointInfoByAreaId(String areaId){
		String sql="UPDATE T_NCLK_ENDPOINT  SET STATE='A' WHERE AREA_ID=? ";
		baseDao.updateBySql(sql, new Object[] {areaId});
	}
	/**
	 *根据区域ID查找区域类型 
	 * @param areaId
	 * @return
	 */
	public Map<String, Object> findAreaTypeByAreaId(String areaId){
		String sql="SELECT AREA_TYPE FROM T_NCLK_AREA WHERE AREA_ID=?";
		return baseDao.findMapBySql(sql, new Object[] {areaId});
	}
	
	/**
	 * 查询区域列表信息
	 * @return
	 */
	public Page findAreaInfo(String start ,String limit,String searchText,String areaId,String areaType,String areaState){
		List<String> params=new ArrayList<>();
		StringBuffer buffer =new StringBuffer();
		buffer.append(" SELECT AREA_ID,AREA_NAME,PARENT_AREA_ID,AREA_TYPE,ORD,STATE ");
		buffer.append(" FROM T_NCLK_AREA WHERE 1 = 1  ");
		if(StringUtils.isNotBlank(areaType)&&StringUtils.isNotBlank(areaId)) {
			buffer.append(" AND ((PARENT_AREA_ID = ? ");
			buffer.append(" AND AREA_TYPE= ? ) ");
			buffer.append(" OR (AREA_ID= ? ");
			buffer.append(" AND AREA_TYPE= ? )) ");
			params.add(areaId);
			params.add(areaType);
			params.add(areaId);
			String parentAreaType=Integer.toString((Integer.parseInt(areaType)-1));
			params.add(parentAreaType);
		}
		if(StringUtils.isNotBlank(searchText)) {
			buffer.append(" AND AREA_NAME LIKE ?  ");
			params.add("%"+searchText+"%");
		}
		if(StringUtils.isNotBlank(areaState)) {
			buffer.append(" AND STATE = ? ");
			params.add(areaState);
		}
		buffer.append(" ORDER BY AREA_TYPE,ORD");
		Page page= baseDao.findPageAllBySql(buffer.toString(), start, limit,params.toArray());
		return page;
	}

	/**
	 * 根据区域类型获取区域信息
	 * @param areaType
	 * @return
	 */
	public List<Map<String, Object>> findAreaInfoByAreaType(String areaType) {
		String sql="SELECT AREA_ID,AREA_NAME,AREA_TYPE FROM T_NCLK_AREA WHERE AREA_TYPE=? AND STATE='A'";
		return baseDao.findListBySql(sql, new Object[] {areaType});
	}
	
	/**
	 * 根据区域id将该区域及该区域子区域状态同步修改成有效/无效
	 * @param areaId 区域ID
	 * @param state 需要修改成的状态A有效，X无效
	 */
	public void editAreaInfoByAreaId(String areaId,String state){
		String sql="update T_NCLK_AREA set state=? where area_Id =? or parent_area_id in (select area_id from (select area_id  from T_NCLK_AREA where parent_area_id =?) as tmp) or parent_area_id =?";
		baseDao.updateBySql(sql, new Object[] {state,areaId,areaId,areaId});
	}
	
	/**
	 * 根据区域id将该区域下的所有考点标记为有效/无效
	 * @param areaId 区域id
	 * @param state 需要修改成的状态A有效，X无效
	 */
	public void editEndPointInfoByAreaId(String areaId,String state){
		String sql="UPDATE T_NCLK_ENDPOINT SET state = ? WHERE area_id IN (SELECT area_id FROM T_NCLK_AREA WHERE(area_Id = ? OR parent_area_id IN "
				+ "(SELECT area_id FROM T_NCLK_AREA WHERE parent_area_id = ?) OR parent_area_id = ?) AND area_type = '3' )";
		baseDao.updateBySql(sql, new Object[]{state,areaId,areaId,areaId});
	}
	
	/**
	 *根据省市县名称查询省市县ID并且验证上下级是否正确
	 * @param areanames
	 * @return
	 */
	public List<Map<String, Object>> findAreaIdAndCheck(String provinceName,String cityName,String districtName ){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT AREA_ID,AREA_TYPE,AREA_NAME FROM T_NCLK_AREA WHERE (AREA_ID IN(SELECT PARENT_AREA_ID FROM T_NCLK_AREA WHERE  ");
		sql.append("AREA_ID IN(SELECT PARENT_AREA_ID FROM T_NCLK_AREA  WHERE AREA_NAME=?))  ");
		sql.append("OR AREA_ID =(SELECT PARENT_AREA_ID FROM T_NCLK_AREA  WHERE AREA_NAME=?) OR AREA_NAME=?) ");
		sql.append("AND AREA_NAME IN(?,?,?) ORDER BY AREA_TYPE  ");
		return   baseDao.findListBySql(sql.toString(), new Object[] {districtName,districtName,districtName,districtName,cityName,provinceName});
	}
	
	
	/**
	 *  查询所有省
	 * @return
	 */
	public List<Map<String, Object>> getProvinceAll(){
		String sql = " SELECT AREA_NAME,AREA_ID,PARENT_AREA_ID FROM T_NCLK_AREA  WHERE STATE='A' AND AREA_TYPE='1' ";
		return baseDao.findListBySql(sql);
	}
	
	public Map<String,Object> findAreaIdInport(String cityId,String districtName){
		String sql="SELECT AREA_ID FROM T_NCLK_AREA WHERE PARENT_AERA_ID=? AND AREA_NAME=?";
		return baseDao.findMapBySql(sql, new String[]{cityId,districtName});
	}
	
}
