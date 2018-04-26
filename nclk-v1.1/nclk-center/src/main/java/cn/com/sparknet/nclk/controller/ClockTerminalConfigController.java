package cn.com.sparknet.nclk.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.service.ClockTerminalConfigService;
import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 网络时钟终端配置
 * @author wuyl
 *
 */
@Controller
@RequestMapping("/ClockTerminalConfigController")
public class ClockTerminalConfigController {
	
	@Resource
	private ClockTerminalConfigService clockTerminalConfigService;
	
	@ResponseBody
	@RequestMapping("/findExamRoomIPEXISTS")
	public EditJson findExamRoomIPEXISTS(HttpServletRequest request) throws Exception {
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String examRoomIP = StringUtil.nullToEmpty(request.getParameter("examRoomIP"));
		return clockTerminalConfigService.findExamRoomIPEXISTS(endPointId, examRoomIP);
	}
	
	/**
	 * 根据前台接收的参数信息 禁用或者启用考场子钟
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/examRoomNormalOrDisable")
	public EditJson examRoomNormalOrDisable(HttpServletRequest request) throws Exception {
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String examRoomIP = StringUtil.nullToEmpty(request.getParameter("examRoomIP"));
		String state = StringUtil.nullToEmpty(request.getParameter("state"));
		return clockTerminalConfigService.updateExamRoomNormalOrDisable(endPointId, examRoomIP,state);
	}
	
	/**
	 * 获取考点id和子钟ip查询子钟其他信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/examRoomInfoEdit")
	public EditJson examRoomInfoEdit(HttpServletRequest request) throws Exception {
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endpointId"));
		String examRoomIP = StringUtil.nullToEmpty(request.getParameter("examRoomIP"));
		String examRoomName = StringUtil.nullToEmpty(request.getParameter("examRoomName"));
		String examRoomAddr = StringUtil.nullToEmpty(request.getParameter("examRoomAddr"));
		String examRoomPerson = StringUtil.nullToEmpty(request.getParameter("examRoomPerson"));
		String examRoomTel = StringUtil.nullToEmpty(request.getParameter("examRoomTel"));
		return clockTerminalConfigService.updateExamRoomInfo(endPointId, examRoomIP, examRoomName, examRoomAddr, examRoomPerson, examRoomTel);
	}
	
	/**
	 * 获取考点id和子钟ip查询子钟其他信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findExamRoomInfo")
	public Map<String,Object> findExamRoomInfo(HttpServletRequest request) throws Exception {
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String examRoomIP = StringUtil.nullToEmpty(request.getParameter("examRoomIP"));
		Map<String,Object> examRoomInfo = clockTerminalConfigService.findExamRoomInfo(endPointId, examRoomIP);
		return examRoomInfo;
	}
	
	/**
	 * 获取考场子钟状态
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findEndPointClockState")
	public String findEndPointClockState(HttpServletRequest request) throws Exception {
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String clockInfo = clockTerminalConfigService.findEndPointClockState(endPointId);
		return clockInfo;
	}
	
	/**
	 * 自动检索考场子钟信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/autoSearchExamRoomInfo")
	public String autoSearchExamRoomInfo(HttpServletRequest request) throws Exception {
		EditJson editJson = new EditJson();
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String result = clockTerminalConfigService.findAutoSearchClock(endPointId);
		//{"dateFormat":"yyyy-MM-dd","rows":[{"ip":"192.168.0.61","mac":"D8:B2:4C:00:04:D2"},{"ip":"10.66.1.54","mac":"D8:B0:4C:DD:47:86"}],"total":2}
		//{"dateFormat":"yyyy-MM-dd","rows":[{"ip":"10.66.1.54","mac":"D8:B0:4C:DD:47:86"}],"total":1}
		return result;
	}
	
	
	
	/**
	 * 手动添加考场子钟信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addAutoSearchExamRoomInfo")
	public EditJson addAutoSearchExamRoomInfo(HttpServletRequest request) throws Exception {
		String examRoomIp = StringUtil.nullToEmpty(request.getParameter("examRoomIp"));
		String endpointId = StringUtil.nullToEmpty(request.getParameter("endpointId"));
		EditJson editJson = clockTerminalConfigService.addAutoSearchExamRoomInfo(examRoomIp, endpointId);
		return editJson;
	}
	
	/**
	 * 手动添加考场子钟信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addExamRoomInfo")
	public EditJson addExamRoomInfo(HttpServletRequest request) throws Exception {
		String examRoomIp = StringUtil.nullToEmpty(request.getParameter("examRoomIp"));
		String examRoomName = StringUtil.nullToEmpty(request.getParameter("examRoomName"));
		String examRoomAddr = StringUtil.nullToEmpty(request.getParameter("examRoomAddr"));
		String examRoomPerson = StringUtil.nullToEmpty(request.getParameter("examRoomPerson"));
		String examRoomTel = StringUtil.nullToEmpty(request.getParameter("examRoomTel"));
		String endpointId = StringUtil.nullToEmpty(request.getParameter("endpointId"));
		EditJson editJson = clockTerminalConfigService.addExamRoomInfo(examRoomIp, examRoomName, examRoomAddr, examRoomPerson, examRoomTel, endpointId);
		return editJson;
	}
	
	/**
	 * 下载 考场子钟信息模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadTemplet")
	public void downloadTemplet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		clockTerminalConfigService.getDownloadTemplet(response);
	}
	
	/**
	 * 上传考场子钟信息模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadTemplet")
	public void uploadTemplet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		clockTerminalConfigService.submitUpload(request, response);
	}
	
	/**
	 * 上传考场子钟信息模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteClock")
	public EditJson deleteClock(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		String ipStr = StringUtil.nullToEmpty(request.getParameter("ipStr"));
		return clockTerminalConfigService.deleteClock(endPointId, ipStr);
	}
}
