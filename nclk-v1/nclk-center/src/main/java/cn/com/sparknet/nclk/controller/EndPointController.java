package cn.com.sparknet.nclk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.nclk.service.EndPointService;

/**
 * 考点管理
 * @author wuyl
 *
 */
@Controller
@RequestMapping("/EndPointController")
public class EndPointController {

	@Resource
	private EndPointService endPointService;

	/**
	 * 查询所有地级市信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getEndPointByDistrictId")
	@ResponseBody
	public String getEndPointByDistrictId(HttpServletRequest request,HttpServletResponse response){
		//获取getJSON方法传递的参数callBack
		String callBack = StringUtil.nullToEmpty(request.getParameter("callBack"));
		//父节点id
		String districtId = StringUtil.nullToEmpty(request.getParameter("districtId"));
		//查询所有省份信息
		return endPointService.getEndPointByDistrictId(callBack,districtId);
	}
	/**
	 * 查询考点列表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/findEndPoints")
	@ResponseBody
	public ListJson findEndPoints(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=StringUtil.nullToEmpty(request.getParameter("start"));
		String limit=StringUtil.nullToEmpty(request.getParameter("limit"));
		String searchText=StringUtil.nullToEmpty(request.getParameter("searchText"));
		return endPointService.findEndPoints(start , limit,searchText);
	}

}
