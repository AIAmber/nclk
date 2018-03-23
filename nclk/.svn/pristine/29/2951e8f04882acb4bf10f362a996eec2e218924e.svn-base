package cn.com.sparknet.nclk.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;
import cn.com.sparknet.common.dao.Page;
import cn.com.sparknet.common.json.ListJson;

/**
 * 考点管理
 * @author wuyl
 *
 */
@Repository
public class EndPointDao {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 查询所有考点信息
	 * @param districtId 区县id
	 * @return
	 */
	public List<Map<String, Object>> findEndPointByDistrictId(String districtId){
		String sql = " SELECT ENDPOINT_NAME,ENDPOINT_ID FROM T_NCLK_ENDPOINT A WHERE A.AREA_ID = ? ";
		return baseDao.findListBySql(sql, new Object[]{districtId});
	}
	/**
	 * 查询考点列表信息
	 * @return
	 */
	public Page findEndPoints(String start ,String limit,String searchText){
		List<String> params=new ArrayList<>();
		StringBuilder builder =new StringBuilder();
		builder.append(" SELECT ENDPOINT_ID,ENDPOINT_NO,ENDPOINT_NAME,ENDPOINT_ADDR,ENDPOINT_PERSON,ENDPOINT_TEL,ENDPOINT_IP,ENDPOINT_PORT ");
		builder.append("FROM T_NCLK_ENDPOINT");
		/*if(StringUtils.isNotBlank(searchText)) {
			builder.append("WHERE ");
		}*/
		return baseDao.findPageAllBySql(builder.toString(), start, limit,params.toArray());
		
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
	 */
	public int addEndPointInfo(String endPointId, String endPointNum, String endPointName, String endPointAreaId,
			String endPointAddr, String endPointPerson, String endPointTel, String endPointIPAddr, String endPointPort,
			int ord) {
		String sql="INSERT INTO T_NCLK_ENDPOINT (ENDPOINT_ID,ENDPOINT_NO,ENDPOINT_NAME,ENDPOINT_ADDR,AREA_ID,ENDPOINT_PERSON,ENDPOINT_TEL,ENDPOINT_IP,ENDPOINT_PORT,CREATE_DATE,STATE,ORD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		return baseDao.updateBySql(sql, new Object[] {endPointId,endPointNum,endPointName,endPointAddr,endPointAreaId,endPointPerson,endPointTel,endPointIPAddr,endPointPort,new Date(),"A",ord});
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
}
