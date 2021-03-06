package cn.com.sparknet.nclk.devconfig.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sparknet.common.json.EditJson;

/**
 * 网络时钟终端配置
 * @author wuyl
 *
 */
public interface ClockTerminalConfigService {

	/**
	 * 根据考点id和考场子钟ip获取子钟其他信息
	 */
	public Map<String,Object> findExamRoomInfo(String endPointId,String examRoomIP); 
	/**
	 * 获取考场子钟状态
	 * @param endPointId
	 * @return
	 */
	public Map<String,Object> findEndPointClockState(String endPointId);
	/**
	 * 自动检索考场子钟信息
	 */
	public String autoSearchExamRoomInfo(String endPointId);
	
	/**
	 * 下载考场子钟信息模板
	 */
	public void getDownloadTemplet(HttpServletResponse response);
	/**
	 * 上传考场子钟信息模板
	 */
	public EditJson submitUpload(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 手动添加考场子钟信息
	 * @param examRoomIp 考场子钟ip
	 * @param examRoomName 考场名称
	 * @param examRoomAddr 考场地址
	 * @param examRoomPerson 考场负责人
	 * @param examRoomTel 考场负责人电话
	 * @param endpointId 考点id
	 * @param ord 顺序号
	 * @return
	 */
	public EditJson addExamRoomInfo(String examRoomIp,String examRoomName,String examRoomAddr,String examRoomPerson,String examRoomTel,String endpointId,String ord);
	
	
}
