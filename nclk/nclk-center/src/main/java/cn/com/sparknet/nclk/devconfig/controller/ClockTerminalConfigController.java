package cn.com.sparknet.nclk.devconfig.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.nclk.devconfig.service.ClockTerminalConfigService;

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
	public Map findEndPointClockState(HttpServletRequest request) throws Exception {
		String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
		Map clockInfo = clockTerminalConfigService.findEndPointClockState(endPointId);
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
		String result = clockTerminalConfigService.autoSearchExamRoomInfo(endPointId);
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
	@RequestMapping("/addExamRoomInfo")
	public EditJson addExamRoomInfo(HttpServletRequest request) throws Exception {
		String examRoomIp = StringUtil.nullToEmpty(request.getParameter("examRoomIp"));
		String examRoomName = StringUtil.nullToEmpty(request.getParameter("examRoomName"));
		String examRoomAddr = StringUtil.nullToEmpty(request.getParameter("examRoomAddr"));
		String examRoomPerson = StringUtil.nullToEmpty(request.getParameter("examRoomPerson"));
		String examRoomTel = StringUtil.nullToEmpty(request.getParameter("examRoomTel"));
		String endpointId = StringUtil.nullToEmpty(request.getParameter("endpointId"));
		String ord = StringUtil.nullToEmpty(request.getParameter("ord"));
		EditJson editJson = clockTerminalConfigService.addExamRoomInfo(examRoomIp, examRoomName, examRoomAddr, examRoomPerson, examRoomTel, endpointId, ord);
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
	@ResponseBody
	@RequestMapping("/uploadTemplet")
	public EditJson uploadTemplet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return clockTerminalConfigService.submitUpload(request, response);
	}
}
