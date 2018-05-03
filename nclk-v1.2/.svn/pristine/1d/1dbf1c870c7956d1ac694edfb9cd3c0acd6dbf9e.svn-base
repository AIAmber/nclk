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
import cn.com.sparknet.nclk.service.EndPointService;
import cn.com.sparknet.nclk.util.HttpClientUtils;
import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 考点管理
 * @author luogang
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
		//父节点id
		String districtId = StringUtil.nullToEmpty(request.getParameter("districtId"));
		//查询所有省份信息
		return endPointService.getEndPointByDistrictId(districtId,request);
	}

	/**
	 * 查询考点列表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findEndPoints")
	public ListJson findEndPoints(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String start=StringUtil.nullToEmpty(request.getParameter("start"));
		String limit=StringUtil.nullToEmpty(request.getParameter("limit"));
		String searchText=StringUtil.nullToEmpty(request.getParameter("searchText"));
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String state = StringUtil.nullToEmpty(request.getParameter("state"));
		String areaType = StringUtil.nullToEmpty(request.getParameter("areaType"));
		return endPointService.findEndPoints(start,limit,searchText,areaId,state,areaType);
	}
	
	
	/**
	 * 检查考点编号是否存在
	 */
	@ResponseBody
	@RequestMapping("/findEndPointNo")
	public int findEndPointNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String endPointNum=request.getParameter("endPointNum");
		return endPointService.findEndPointNo(endPointNum);
	}
	
	
	/**
	 * 保存考点管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveEndPointInfo")
	public EditJson saveEndPointInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String endPointId=StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String endPointName=StringUtil.nullToEmpty(request.getParameter("endPointName"));
		String endPointNum=StringUtil.nullToEmpty(request.getParameter("endPointNum"));
		String endPointAreaId=StringUtil.nullToEmpty(request.getParameter("endPointAreaId"));
		String endPointAddr=StringUtil.nullToEmpty(request.getParameter("endPointAddr"));
		String endPointPerson=StringUtil.nullToEmpty(request.getParameter("endPointPerson"));
		String endPointTel=StringUtil.nullToEmpty(request.getParameter("endPointTel"));
		String endPointIPAddr=StringUtil.nullToEmpty(request.getParameter("endPointIPAddr"));
		String endPointPort=StringUtil.nullToEmpty(request.getParameter("endPointPort"));
		String state=StringUtil.nullToEmpty(request.getParameter("state"));
		String ord=StringUtil.nullToEmpty(request.getParameter("ord"));
		return endPointService.saveEndPointInfo(endPointId,endPointNum,endPointName,endPointAreaId,endPointAddr,endPointPerson,endPointTel,endPointIPAddr,endPointPort,ord,state);
	}
	
	/**
	 * 根据考点ID获取考点信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getEndPointByEndPointId")
	public Map<String, Object> getEndPointByEndPointId(HttpServletRequest request,HttpServletResponse response){
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		return endPointService.getEndPointByEndPointId(endPointId);
	}
	/**
	 * 根据区县ID获取考点列表信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getEndPointInfoByDistrictId")
	public List<Map<String,Object>> getEndPointInfoByDistrictId(HttpServletRequest request,HttpServletResponse response){
		String districtId = StringUtil.nullToEmpty(request.getParameter("districtId"));
		return endPointService.getEndPointInfoByDistrictId( districtId);
	}
	
	/**
	 * 根据考点Id删除考点信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteEndPointInfoByEndPointId")
	public EditJson deleteEndPointInfoByEndPointId(HttpServletRequest request,HttpServletResponse response){
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		return endPointService.deleteEndPointInfoByEndPointId( endPointId);
	}
	
	/**
	 * 考点信息excel导入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadTemplet")
	public void uploadTemplet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		endPointService.submitUpload(request, response);
	}
	/**
	 * 下载考点信息模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadTemplet")
	public void downloadTemplet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		endPointService.getDownloadTemplet(response);
	}
	
	/**
	 * 获取考点编号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEndPointNo")
	@ResponseBody
	public String getEndPointNo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String ip=StringUtil.nullToEmpty(request.getParameter("endPointIPAddr"));
		String port= StringUtil.nullToEmpty(request.getParameter("endPointPort"));
		String json = HttpClientUtils.get("http://"+ip+":"+port+"/nclk-endpoint/EndPointController/getEndPointNo");
		return json;
	}
	
	/**
	 * 验证用户是否有权限操作该页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/identityAuthentication")
	@ResponseBody
	public String identityAuthentication(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return endPointService.identityAuthentication(request,response);
	}
}
