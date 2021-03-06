package cn.com.sparknet.nclk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.nclk.service.AreaService;

/**
 * 区域管理
 * @author wuyl
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
		//获取getJSON方法传递的参数callBack
		String callBack = StringUtil.nullToEmpty(request.getParameter("callBack"));
		//查询所有省份信息
		return areaService.findAllProvince(callBack);
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
		//获取getJSON方法传递的参数callBack
		String callBack = StringUtil.nullToEmpty(request.getParameter("callBack"));
		//父节点id
		String parentAreaId = StringUtil.nullToEmpty(request.getParameter("parentAreaId"));
		//查询所有省份信息
		return areaService.findChildrenByParentAreaId(callBack,parentAreaId);
	}
	
	/**
	 * 查询所有区县信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getDistrict")
	@ResponseBody
	public String getDistrict(HttpServletRequest request,HttpServletResponse response){
		//获取getJSON方法传递的参数callBack
		String callBack = StringUtil.nullToEmpty(request.getParameter("callBack"));
		//父节点id
		String parentAreaId = StringUtil.nullToEmpty(request.getParameter("parentAreaId"));
		//查询所有省份信息
		return areaService.findChildrenByParentAreaId(callBack,parentAreaId);
	}
	
	
	/**
	 * 获取区域管理树的信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTreeInfo")
	public String getTreeInfo(HttpServletRequest request,HttpServletResponse response) {
		return areaService.findAllArea();
	}
}
