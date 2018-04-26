package cn.com.sparknet.nclk.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.AreaService;
import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 区域管理
 * @author luogang
 *
 */
@Controller
@RequestMapping("/AreaController")
public class AreaController {
	
	@Resource
	private AreaService areaService;
	
	/**
	 * 查询所有省信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getProvince")
	@ResponseBody
	public String getProvince(HttpServletRequest request,HttpServletResponse response){
		Object obj = request.getSession().getAttribute("areaList");
		return areaService.findProvince(obj);
	}
	
	/**
	 * 查询所有地级市信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCity")
	@ResponseBody
	public String getCity(HttpServletRequest request,HttpServletResponse response){
		//父节点id
		String parentAreaId = StringUtil.nullToEmpty(request.getParameter("parentAreaId"));
		Object obj = request.getSession().getAttribute("areaList");
		return areaService.findChildrenByParentAreaId(parentAreaId, obj);
	}
	
	/**
	 * 查询所有区县信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDistrict")
	public String getDistrict(HttpServletRequest request,HttpServletResponse response){
		//父节点id
		String parentAreaId = StringUtil.nullToEmpty(request.getParameter("parentAreaId"));
		Object obj = request.getSession().getAttribute("areaList");
		return areaService.findChildrenByParentAreaId(parentAreaId, obj);
	}
	
	/**
	 * 获取区域管理树的信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTreeInfo")
	public ListJson getTreeInfo(HttpServletRequest request,HttpServletResponse response) {
		String areaId=StringUtil.nullToEmpty(request.getParameter("id"));
		return areaService.findAllArea(areaId);
	}
	/**
	 * 获取区域到考点四级树信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTreeEndPointInfo")
	public String getTreeEndPointInfo(HttpServletRequest request,HttpServletResponse response) {
		return areaService.findAllAreaEndPoint();
	}
	/**
	 * 根据区域ID获取父级区域ID和区域名称
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAreaInfoByAreaId")
	public Map<String, Object> getCityInfoByDistrictId(HttpServletRequest request,HttpServletResponse response) {
		String areaId=StringUtil.nullToEmpty(request.getParameter("areaId"));
		return areaService.getAreaNameByareaId(areaId);
	}
	
	/**
	 * 根据父级区域ID获取区域ID和区域名称
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getChildAreaInfoByParentAreaId")
	public EditJson getChildAreaInfoByParentAreaId(HttpServletRequest request,HttpServletResponse response) {
		String parentId=StringUtil.nullToEmpty(request.getParameter("parentId"));
		return areaService.getChildAreaInfoByParentAreaId(parentId);
	}
	/**
	 * 保存区域信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveAreaInfo")
	public EditJson addAreaInfo(HttpServletRequest request,HttpServletResponse response) {
		String areaId=StringUtil.nullToEmpty(request.getParameter("areaId"));
		String areaName=StringUtil.nullToEmpty(request.getParameter("areaName"));
		String areaType=StringUtil.nullToEmpty(request.getParameter("areaType"));
		String parentId=StringUtil.nullToEmpty(request.getParameter("parentId"));
		String state = StringUtil.nullToEmpty(request.getParameter("state"));
		String ord=StringUtil.nullToEmpty(request.getParameter("ord"));
		return areaService.saveAreaInfo(areaId,areaName,areaType,parentId,state,ord);
	}
	
	/**
	 * 根据区域ID查找区域信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAreaInfoByAreaId")
	public Map<String, Object> findAreaInfoByAreaId(HttpServletRequest request,HttpServletResponse response){
		String areaId=StringUtil.nullToEmpty(request.getParameter("areaId"));
		return areaService.findAreaInfoByAreaId(areaId);
	}
	/**
	 * 根据区域ID删除区域信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteAreaInfoByAreaId")
	public EditJson deleteAreaInfoByAreaId(HttpServletRequest request,HttpServletResponse response){
		EditJson editJson =new EditJson();
		String[] areaId=request.getParameter("areaId").split(",");
		for (int i = 0; i < areaId.length; i++) {
			editJson=areaService.deleteAreaInfoByAreaId(areaId[i]);
		}
		return  editJson;
	}
	/**
	 * 根据区域ID恢复区域信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateAreaInfoByAreaId")
	public EditJson updateAreaInfoByAreaId(HttpServletRequest request,HttpServletResponse response){
		EditJson editJson =new EditJson();
		String[] areaId=request.getParameter("areaId").split(",");
		for (int i = 0; i < areaId.length; i++) {
			editJson=areaService.updateAreaInfoByAreaId(areaId[i]);
		}
		return  editJson;
	}
	/**
	 * 查询区域列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAreaInfo")
	public ListJson findAreaInfo(HttpServletRequest request,HttpServletResponse response){
		String start=StringUtil.nullToEmpty(request.getParameter("start"));
		String limit=StringUtil.nullToEmpty(request.getParameter("limit"));
		String searchText=StringUtil.nullToEmpty(request.getParameter("searchText"));
		String areaState =StringUtil.nullToEmpty(request.getParameter("state"));
		String areaId=StringUtil.nullToEmpty(request.getParameter("areaId"));
		String areaType=StringUtil.nullToEmpty(request.getParameter("areaType"));
		return areaService.findAreaInfo(start,limit,searchText,areaId,areaType,areaState);
	}
	
	/**
	 * 根据区域类型获取区域信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAreaInfoByAreaType")
	public List<Map<String, Object>> findAreaInfoByAreaType(HttpServletRequest request,HttpServletResponse response){
		String areaType=StringUtil.nullToEmpty(request.getParameter("areaType"));
		return  areaService.findAreaInfoByAreaType(areaType);
	}
}
