package cn.com.sparknet.nclk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.nclk.dao.AreaDao;
import cn.com.sparknet.nclk.dao.Page;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.AreaService;
import cn.com.sparknet.nclk.util.JsonUtil;
import cn.com.sparknet.nclk.util.StringUtil;
import cn.com.sparknet.nclk.util.UUIDUtil;

/**
 * 区域管理
 * @author luogang
 *
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaDao areaDao;
	
	/**
	 * 查询用户有权限管理的省市区信息
	 * @param parentAreaId 父级区域id
	 * @return
	 */
	@Override
	@ResponseBody
	public String findProvince(Object obj) {
		try {
			List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
			if(null != obj){
				List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
				for (int i = 0; i < list.size(); i++) {
					String parentId = (String)list.get(i).get("PARENT_AREA_ID");
					if(null == parentId || "".equals(parentId)){
						provinceList.add(list.get(i));
					}
				}
			}
			return JsonUtil.listToJson(provinceList);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 查询用户有权限管理的省市区信息
	 * @param parentAreaId 父级区域id
	 * @return
	 */
	@Override
	@ResponseBody
	public String findChildrenByParentAreaId(String parentAreaId,Object obj) {
		try {
			List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
			if(null != obj){
				List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
				for (int i = 0; i < list.size(); i++) {
					String parentId = (String)list.get(i).get("PARENT_AREA_ID");
					if(StringUtil.isNotEmpty(parentId) && StringUtil.isNotEmpty(parentAreaId) && parentAreaId.equals(parentId)){
						cityList.add(list.get(i));
					}
				}
			}
			return JsonUtil.listToJson(cityList);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 *获取区域管理树的信息
	 */
	@Override
	public ListJson findAllArea(String areaId) {
		ListJson listJson =new ListJson();
		List<Map<String, Object>> list =areaDao.findAllAreaInfo(areaId);
		List<Map<String, Object>> cityIdList=areaDao.findAreaIdsByParentId(areaId);
		if(!cityIdList.isEmpty()) {
			for (int i = 0; i < cityIdList.size(); i++) {
				list.addAll(areaDao.findAllAreaInfo(cityIdList.get(i).get("AREA_ID").toString()));  
				}
		}
		listJson.setRows(list);
		return listJson;
	}
	
	
	/**
	 *获取区域到考点四级树信息
	 */
	@Override
	public String findAllAreaEndPoint() {
		List<Map<String,Object>> list = areaDao.findAllArea("A");
		List<Map<String,Object>> listEndPoint = areaDao.findAllAreaEndPoint();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"rows\":[");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("PARENT_AREA_ID")==null||"".equals(list.get(i).get("PARENT_AREA_ID"))) {
				sb.append("{\"id\":\""+list.get(i).get("AREA_ID")+"\" ,\"pId\":\""+list.get(i).get("PARENT_AREA_ID")+"\" ,\"name\":\""+list.get(i).get("AREA_NAME")+"\" ,\"open\":\"true\"},");
			}else {
				sb.append("{\"id\":\""+list.get(i).get("AREA_ID")+"\" ,\"pId\":\""+list.get(i).get("PARENT_AREA_ID")+"\" ,\"name\":\""+list.get(i).get("AREA_NAME")+"\"},");
			}
		}
		for (int i = 0; i < listEndPoint.size(); i++) {
			if (i<listEndPoint.size()-1) {
				sb.append("{\"id\":\""+listEndPoint.get(i).get("ENDPOINT_ID")+"\" ,\"pId\":\""+listEndPoint.get(i).get("AREA_ID")+"\" ,\"name\":\""+listEndPoint.get(i).get("ENDPOINT_NAME")+"\"},");
			}else if (i==listEndPoint.size()-1) {
				sb.append("{\"id\":\""+listEndPoint.get(i).get("ENDPOINT_ID")+"\" ,\"pId\":\""+listEndPoint.get(i).get("AREA_ID")+"\" ,\"name\":\""+listEndPoint.get(i).get("ENDPOINT_NAME")+"\"}");
			}
			
		}
		sb.append("]}");
		return sb.toString();
	}

	/**
	 *根据区域ID获取父级区域ID和名称
	 */
	@Override
	public Map<String, Object> getAreaNameByareaId(String areaId) {
		return areaDao.findAreaNameByAreaId(areaId);
	}

	/**
	 *根据父级区域ID获取区域ID和区域名称
	 */
	@Override
	public EditJson getChildAreaInfoByParentAreaId(String parentId) {
		EditJson editJson =new EditJson();
		editJson.setBean(areaDao.getChildAreaInfoByParentAreaId(parentId));
		editJson.setSuccess(true);
		return editJson;
	}
	
	/**
	 *保存区域信息
	 */
	@Override
	public EditJson saveAreaInfo(String areaId,String areaName, String areaType, String parentId, String state,String ord) { 
		EditJson editJson = new EditJson();
		if(StringUtil.isEmpty(areaId)) {
			try {
				areaId = UUIDUtil.getNextValue();
				if(StringUtil.isNotEmpty(parentId)) {
					areaDao.addAreaInfo(areaId, areaName, areaType, parentId,state ,ord);
					
				}else {
					areaDao.addAreaInfo(areaId, areaName, areaType, null,state ,ord);
				}
				editJson.setSuccess(true);
				editJson.setBean("添加区域信息成功！");
			} catch (Exception e) {
				editJson.setSuccess(false);
				editJson.setBean("添加区域信息失败！");
				throw new RuntimeException(e.getMessage(),e);
			}
			
		}else {
			try {
				if(StringUtil.isNotEmpty(parentId)) {
					areaDao.updateAreaInfo(areaId, areaName, areaType, parentId,state, ord);
				}else {
					areaDao.updateAreaInfo(areaId, areaName, areaType, null,state, ord);
				}
				editJson.setSuccess(true);
				editJson.setBean("修改区域信息成功！");
			} catch (Exception e) {
				editJson.setSuccess(false);
				editJson.setBean("修改区域信息失败！");
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		return editJson;
	}

	/**
	 *根据区域ID查找区域信息
	 */
	@Override
	public Map<String, Object> findAreaInfoByAreaId(String areaId) {
		return areaDao.findAreaInfoByAreaId(areaId);
	}

	/**
	 *根据区域ID删除区域信息
	 */
	@Override
	public EditJson deleteAreaInfoByAreaId(String areaId) {
		EditJson editJson =new EditJson();
		try {
			//根据id将该区域id及其子集修改成无效，并且将该区域下所有子集修改成无效
				areaDao.editAreaInfoByAreaId(areaId,"X");
				areaDao.editEndPointInfoByAreaId(areaId,"X");
//				List<Map<String, Object>> list =areaDao.findAreaIdsByParentId(areaId);
//				if(list!=null && !list.isEmpty()) {
//					for (int i = 0; i < list.size(); i++) {
//						areaId=list.get(i).get("AREA_ID").toString();
//						areaDao.deleteAreaInfoByAreaId(areaId);
//						List<Map<String, Object>> areaList = areaDao.findAreaIdsByParentId(areaId);
//						if (areaList!=null && !areaList.isEmpty()) {
//							for(int j = 0; j < areaList.size(); j++) {
//								areaId=areaList.get(j).get("AREA_ID").toString();
//								areaDao.deleteAreaInfoByAreaId(areaId);
//								areaDao.deleteEndPointInfoByAreaId(areaId);
//							}
//						}else {
//							areaDao.deleteEndPointInfoByAreaId(areaId);
//						}
//					}
			
//					areaDao.editAreaInfoByAreaId(areaId,'X');
					//将该区域下面的考点
//					areaDao.editEndPointInfoByAreaId(areaId,'X');
//				}else {
//					areaDao.deleteEndPointInfoByAreaId(areaId);
//				}
			editJson.setSuccess(true);
			editJson.setBean("删除区域信息成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("删除区域信息失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}
	/**
	 *根据区域ID恢复区域信息
	 */
	@Override
	public EditJson updateAreaInfoByAreaId(String areaId) {
		EditJson editJson =new EditJson();
		try {
//			areaDao.updateAreaInfoByAreaId(areaId);
//			List<Map<String, Object>> list =areaDao.findAreaIdsByParentId(areaId);
//			if(list!=null && !list.isEmpty()) {
//				for (int i = 0; i < list.size(); i++) {
//					areaId=list.get(i).get("AREA_ID").toString();
//					areaDao.updateAreaInfoByAreaId(areaId);
//					List<Map<String, Object>> areaList = areaDao.findAreaIdsByParentId(areaId);
//					if (areaList!=null && !areaList.isEmpty()) {
//						for(int j = 0; j < areaList.size(); j++) {
//							areaId=areaList.get(j).get("AREA_ID").toString();
//							areaDao.updateAreaInfoByAreaId(areaId);
//							areaDao.updateEndPointInfoByAreaId(areaId);
//						}
//					}else {
//						areaDao.updateEndPointInfoByAreaId(areaId);
//					}
//				}
//			}else {
//				areaDao.updateEndPointInfoByAreaId(areaId);
//			}
			//根据id将该区域id及其子集修改成无效，并且将该区域下所有子集修改成无效
			areaDao.editAreaInfoByAreaId(areaId,"A");
			areaDao.editEndPointInfoByAreaId(areaId,"A");
			editJson.setSuccess(true);
			editJson.setBean("删除区域信息成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("删除区域信息失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}

	/**
	 *查询区域列表信息
	 */
	@Override
	public ListJson findAreaInfo(String start, String limit, String searchText,String areaId,String areaType,String areaState) {
		ListJson listJson =new ListJson();
		Page page ;
		page=areaDao.findAreaInfo(start, limit, searchText,areaId,areaType,areaState);
		listJson.setRows(page.getList());
		listJson.setTotal(page.getCount());
		return listJson;
	}

	/**
	 *根据区域类型获取区域信息
	 */
	@Override
	public List<Map<String, Object>> findAreaInfoByAreaType(String areaType) {
		if(Integer.parseInt(areaType)-1>0) {
			String areaParentType=Integer.toString(Integer.parseInt(areaType)-1);
			return areaDao.findAreaInfoByAreaType(areaParentType);
		}else {
			return null;
		}
	}

	/**
	 * 获取所有省的信息
	 */
	@Override
	public String getProvinceAll() {
		List<Map<String, Object>> provinceList= areaDao.getProvinceAll();
		return JsonUtil.listToJson(provinceList);
	}

	@Override
	public String getChildAreaByParentAreaId(String parentAreaId) {
		List<Map<String, Object>> areaList= areaDao.getChildAreaInfoByParentAreaId(parentAreaId);
		return JsonUtil.listToJson(areaList);
	}
	
	
	
	
}
