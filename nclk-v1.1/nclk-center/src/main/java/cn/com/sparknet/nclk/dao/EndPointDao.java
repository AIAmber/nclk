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
 * 考点管理
 * @author luogang
 *
 */
@Repository
public class EndPointDao {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 根据用户名查询用户所能查看的考点信息
	 * @param districtId 区县id
	 * @return
	 */
	public List<Map<String, Object>> findEndPointByDistrictId(String districtId,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT E.ENDPOINT_NAME,E.ENDPOINT_ID ");
		sb.append(" FROM T_NCLK_ENDPOINT E,T_NCLK_USER_ENDPOINT UE,T_NCLK_USER U ");
		sb.append(" WHERE UE.USER_ID = U.USER_ID ");
		sb.append(" AND UE.ENDPOINT_ID = E.ENDPOINT_ID ");
		sb.append(" AND E.AREA_ID = ? ");
		sb.append(" AND U.USERNAME = ? ");
		sb.append(" AND E.STATE = 'A' ");
		return baseDao.findListBySql(sb.toString(), new Object[]{districtId,userName});
	}
	/**
	 * 查询考点列表信息
	 * @return
	 */
	public Page findEndPoints(String start ,String limit,String searchText,String areaId,String state,String areaType){
		List<String> params=new ArrayList<>();
		StringBuffer buffer =new StringBuffer();
		buffer.append("SELECT e.ENDPOINT_ID ,e.ENDPOINT_NO ,e.ENDPOINT_NAME,e.ENDPOINT_ADDR,e.ENDPOINT_PERSON,e.ENDPOINT_TEL,e.ENDPOINT_IP ,e.ENDPOINT_PORT ,e.STATE ,e.ORD FROM T_NCLK_ENDPOINT e ");
		buffer.append("INNER JOIN T_NCLK_AREA a ");
		buffer.append("ON a.AREA_ID=e.AREA_ID WHERE a.STATE='A' ");
		if(StringUtils.isNotBlank(searchText)) {
			buffer.append("AND (e.ENDPOINT_NAME LIKE ? OR e.ENDPOINT_NO LIKE ? OR e.ENDPOINT_IP LIKE ?)");
			params.add("%" + searchText + "%");
	        params.add("%" + searchText + "%");
	        params.add("%" + searchText + "%");
		}
		if(StringUtils.isNotBlank(areaId) && StringUtils.isNotBlank(areaType)) {
			if("3".equals(areaType)){//区id
				buffer.append("AND e.AREA_ID = ? ");
				params.add(areaId);
			}else if("2".equals(areaType)){//市id
				buffer.append(" AND e.AREA_ID IN (SELECT AREA_ID FROM T_NCLK_AREA WHERE PARENT_AREA_ID = ? ) ");
				params.add(areaId);
			}else if("1".equals(areaType)){//省id
				buffer.append(" AND e.AREA_ID IN (SELECT AREA_ID FROM T_NCLK_AREA WHERE PARENT_AREA_ID IN (SELECT AREA_ID FROM T_NCLK_AREA WHERE PARENT_AREA_ID = ? )) ");
				params.add(areaId);
			}
		}
		if(StringUtils.isNotBlank(state)){
			buffer.append(" AND e.STATE = ? ");
			params.add(state);
		}
		buffer.append(" ORDER BY e.STATE,e.ORD,e.ENDPOINT_NO ");
		Page page =baseDao.findPageAllBySql(buffer.toString(), start, limit,params.toArray());
		for (Map<String, Object> map : page.getList()) {
			map.put("ENDPOINT_SERVER", map.get("ENDPOINT_IP").toString()+"&nbsp:&nbsp"+map.get("ENDPOINT_PORT").toString());
		}
		return page;
		
	}
	/**
	 * 根据考点名称查询考点id
	 * @return
	 */
	public Map<String,Object> findEndPointIdByName(String endPointName){
		String sql=" SELECT ENDPOINT_ID FROM T_NCLK_ENDPOINT A WHERE A.ENDPOINT_NAME = ? AND A.STATE = 'A' ";
		return baseDao.findMapBySql(sql, new Object[]{endPointName});
	}
	/**
	 * 根据考点名称查询考点id
	 * @return
	 */
	public Map<String,Object> findEndPointIdByNameANDAreaId(String endPointName,String areaId){
		String sql=" SELECT ENDPOINT_ID FROM T_NCLK_ENDPOINT A WHERE A.ENDPOINT_NAME = ? AND A.STATE = 'A' AND AREA_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{endPointName,areaId});
	}
	/**
	 * 新增考点管理
	 * @param endPointId
	 * @param endPointNum
	 * @param endPointName
	 * @param endPointAreaId
	 * @param endPointAddr
	 * @param endPointPerson
	 * @param endPointTel
	 * @param endPointIPAddr
	 * @param endPointPort
	 * @param ord
	 * @param state
	 * @return
	 */
	public int addEndPointInfo(String endPointId, String endPointNum, String endPointName, String endPointAreaId,
			String endPointAddr, String endPointPerson, String endPointTel, String endPointIPAddr, String endPointPort,
			String ord,String state) {
		String sql="INSERT INTO T_NCLK_ENDPOINT (ENDPOINT_ID,ENDPOINT_NO,ENDPOINT_NAME,ENDPOINT_ADDR,AREA_ID,ENDPOINT_PERSON,ENDPOINT_TEL,ENDPOINT_IP,ENDPOINT_PORT,CREATE_DATE,STATE,ORD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtil.isNotEmpty(ord)) {
			int ordId=Integer.parseInt(ord);
			return baseDao.updateBySql(sql, new Object[] {endPointId,endPointNum,endPointName,endPointAddr,endPointAreaId,endPointPerson,endPointTel,endPointIPAddr,endPointPort,new Date(),state,ordId});
		}else {
			return baseDao.updateBySql(sql, new Object[] {endPointId,endPointNum,endPointName,endPointAddr,endPointAreaId,endPointPerson,endPointTel,endPointIPAddr,endPointPort,new Date(),state,null});
		}
		
	}
	
	/**
	 * 检查考点编号是否存在
	 */
	public int findEndPointNo(String endPointNum) throws Exception {
			String sql="SELECT COUNT(*) FROM T_NCLK_ENDPOINT WHERE ENDPOINT_NO=?";
			return baseDao.findIntBySql(sql, new Object[]{endPointNum});
	}
	
	/**
	 * 根据所属区域id查询考点id
	 * @return
	 */
	public Map<String,Object> findAreaIdByEndPointId(String endPointId){
		String sql=" SELECT AREA_ID FROM T_NCLK_ENDPOINT A WHERE A.ENDPOINT_ID = ? AND A.STATE = 'A' ";
		return baseDao.findMapBySql(sql, new Object[]{endPointId});
	}
	
	/**
	 * 根据考点id查询考点服务器ip和端口
	 * @return
	 */
	public Map<String,Object> findEndPointIPAndPortByEndPointId(String endPointId){
		String sql=" SELECT ENDPOINT_IP,ENDPOINT_PORT FROM T_NCLK_ENDPOINT A WHERE A.ENDPOINT_ID = ? AND A.STATE = 'A' ";
		return baseDao.findMapBySql(sql, new Object[]{endPointId});
	}
	/**
	 * 根据考点ID获取考点信息
	 * @param endPointId
	 * @return
	 */
	public Map<String, Object> getEndPointByEndPointId(String endPointId) {
		String sql ="SELECT ENDPOINT_ID, ENDPOINT_NAME,ENDPOINT_NO,AREA_ID,ENDPOINT_ADDR,ENDPOINT_PERSON, ENDPOINT_TEL,ENDPOINT_IP,ENDPOINT_PORT,ORD,STATE FROM T_NCLK_ENDPOINT WHERE ENDPOINT_ID=?";
		return baseDao.findMapBySql(sql, new Object[] {endPointId});
	}
	/**
	 * 根据区县ID获取考点列表信息
	 * @param districtId
	 * @return
	 */
	public List<Map<String, Object>> getEndPointInfoByDistrictId( String districtId) {
		String sql=" SELECT ENDPOINT_ID,ENDPOINT_NO,ENDPOINT_NAME,ENDPOINT_ADDR,ENDPOINT_PERSON,ENDPOINT_TEL,ENDPOINT_IP,ENDPOINT_PORT FROM T_NCLK_ENDPOINT WHERE AREA_ID=? ";
		return baseDao.findListBySql(sql, new Object[] {districtId});
	}
	
	/**
	 * 根据考点Id删除考点信息
	 * @param endPointId
	 */
	public void deleteEndPointInfoByEndPointId(String[] endPointIdList) {
		String flag="";
		for(String id:endPointIdList){
			flag+="?,";
		}
		if(flag.length()>0){
			flag=flag.substring(0, flag.length()-1);
		}
		String sql="UPDATE T_NCLK_ENDPOINT SET STATE ='X' WHERE ENDPOINT_ID IN ("+flag+")";
		baseDao.updateBySql(sql, endPointIdList);
	}
	
	/**
	 * 修改考点管理
	 * @param endPointId
	 * @param endPointName
	 * @param endPointAreaId
	 * @param endPointAddr
	 * @param endPointPerson
	 * @param endPointTel
	 * @param endPointIPAddr
	 * @param endPointPort
	 * @param ord
	 * @param state
	 * @return
	 */
	public int updateEndPointInfo(String endPointId, String endpointNum, String endPointName, String endPointAreaId,
			String endPointAddr, String endPointPerson, String endPointTel, String endPointIPAddr, String endPointPort,
			String ord,String state) {
		StringBuffer buffer =new StringBuffer();
		buffer.append("UPDATE T_NCLK_ENDPOINT SET  ENDPOINT_NAME=?,ENDPOINT_NO =?, ENDPOINT_ADDR=?, AREA_ID=?, ENDPOINT_PERSON=?, ENDPOINT_TEL=?,ENDPOINT_IP=?, ENDPOINT_PORT=?, ORD=?,STATE=? WHERE ENDPOINT_ID=? ");
		if(StringUtil.isNotEmpty(ord) ) {
			int ordId= Integer.parseInt(ord);
			return baseDao.updateBySql(buffer.toString(), new Object[] {endPointName,endpointNum,endPointAddr,endPointAreaId,endPointPerson,endPointTel,endPointIPAddr,endPointPort,ordId,state,endPointId});
		}else {
			return baseDao.updateBySql(buffer.toString(), new Object[] {endPointName,endpointNum,endPointAddr,endPointAreaId,endPointPerson,endPointTel,endPointIPAddr,endPointPort,null,state,endPointId});
		}
	}
	
	public String identityAuthentication(String userId,String moduleId){
		String sql="SELECT COUNT(*) FROM T_NCLK_MODULE B ,T_NCLK_USER_AUTH A WHERE A.MODULE_ID=B.MODULE_ID AND  A.USER_ID=? AND A.MODULE_ID=?";
		int count =baseDao.findIntBySql(sql, new String[]{userId,moduleId});
		if(count>0){
			return "yes";
		}else{
			return "no";
		}
	
	}
}
