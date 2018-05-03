package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;

/**
 * 区域管理
 * @author wuyl
 *
 */
public interface AreaService {

	/**
	 * 查询用户所能查看的省信息
	 * @param request
	 * @return
	 */
	public String findProvince(Object obj);
	/**
	 * 查询用户有权限查看的市，县区信息
	 * @param parentAreaId
	 * @param request
	 * @return
	 */
	public String findChildrenByParentAreaId(String parentAreaId,Object obj);
	/**
	 * 获取区域管理树的信息
	 * @param areaId
	 * @return
	 */
	public ListJson findAllArea(String areaId);
	/**
	 * 获取区域到考点四级树信息
	 * @return
	 */
	public String findAllAreaEndPoint();
	/**
	 * 根据区域ID获取父级区域ID和名称
	 * @param areaId
	 * @return
	 */
	public Map<String, Object> getAreaNameByareaId(String areaId);
	/**
	 * 根据父级区域ID获取区域ID和区域名称
	 * @param parentId
	 * @return
	 */
	public EditJson getChildAreaInfoByParentAreaId(String parentId);
	
	/**
	 * 保存区域信息
	 * @param areaId
	 * @param areaName
	 * @param areaType
	 * @param parentId
	 * @param state
	 * @param ord
	 * @return
	 */
	public EditJson saveAreaInfo(String areaId,String areaName, String areaType, String parentId,String state ,String ord);
	/**
	 * 根据区域ID查找区域信息
	 * @param areaId
	 * @return
	 */
	public Map<String, Object> findAreaInfoByAreaId(String areaId);
	
	/**
	 * 根据区域ID删除区域信息
	 * @param areaId
	 * @return
	 */
	public EditJson deleteAreaInfoByAreaId(String areaId);
	
	/**
	 * 根据区域ID恢复区域信息
	 * @param areaId
	 * @return
	 */
	public EditJson updateAreaInfoByAreaId(String areaId);
	
	
	/**
	 * 查询区域列表信息
	 * @param start
	 * @param limit
	 * @param searchText
	 * @param areaId
	 * @param areaType
	 * @param areaState
	 * @return
	 */
	public ListJson findAreaInfo(String start, String limit, String searchText,String areaId,String areaType,String areaState);
	/**
	 * 根据区域类型获取区域信息
	 * @param areaType
	 * @return
	 */
	public List<Map<String, Object>> findAreaInfoByAreaType(String areaType);
	
	/**
	 * 获取所有省数据
	 * @return
	 */
	public String getProvinceAll();
	
	/**
	 * 获取传入的区域下一级区域信息
	 */
	public String getChildAreaByParentAreaId(String parentAreaId);
	
}
