package cn.com.sparknet.nclk.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;

/**
 * 区域管理
 * @author wuyl
 *
 */
@Repository
public class AreaDao {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 查询所有省信息
	 * @return
	 */
	public List<Map<String, Object>> findAllProvince(){
		String sql = " SELECT AREA_ID,AREA_NAME,AREA_TYPE FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' AND TNA.PARENT_AREA_ID IS NULL ";
		return baseDao.findListBySql(sql, new Object[]{});
	}
	
	/**
	 * 查询所有地级市信息
	 * @return
	 */
	public List<Map<String, Object>> findChildrenByParentAreaId(String parentAreaId){
		String sql = " SELECT AREA_ID,AREA_NAME,AREA_TYPE FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' AND TNA.PARENT_AREA_ID = ? ";
		return baseDao.findListBySql(sql, new Object[]{parentAreaId});
	}
	
	/**
	 * 根据区域名称查询区域id值
	 * @return
	 */
	public Map<String,Object> findAreaIdByAreaName(String districtId){
		String sql = " SELECT AREA_ID FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' AND TNA.AREA_NAME = ? ";
		return baseDao.findMapBySql(sql, new Object[]{districtId});
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
	public List<Map<String,Object>> findAllAreaInfo(){
		String sql = " SELECT TNA.AREA_ID,TNA.PARENT_AREA_ID,TNA.AREA_NAME,TNA.AREA_TYPE FROM T_NCLK_AREA TNA WHERE TNA.STATE = 'A' ";
		return baseDao.findListBySql(sql, new Object[]{});
	}
}
