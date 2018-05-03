package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sparknet.nclk.json.EditJson;

/**
 * 网络时钟终端配置
 * @author wuyl
 *
 */
public interface ClockTerminalConfigService {

	/**
	 * 查询考场中的ip是否已经存在
	 * @param endPointId
	 * @param examRoomIP
	 * @return
	 */
	public EditJson findExamRoomIPEXISTS(String endPointId, String examRoomIP);
	
	/**
	 * 根据考点id和考场子钟ip获取子钟其他信息
	 * @param endPointId
	 * @param examRoomIP
	 * @return
	 */
	public Map<String,Object> findExamRoomInfo(String endPointId,String examRoomIP); 
	/**
	 * 获取考场子钟状态
	 * @param endPointId
	 * @return
	 */
	public String findEndPointClockState(String endPointId);
	/**
	 * 根据考点id查询考场子钟IP
	 * @param endPointId
	 * @return
	 */
	public List<Map<String,Object>> findExamRoomIPByEndPointId(String endPointId);
	/**
	 * 根据考场ip，子钟ip和状态参数启用或者停用考场子钟
	 * @param endPointId 考点id
	 * @param examRoomIP 考场子钟ip
	 * @param state 状态 normal 或者disable
	 * @return
	 */
	public EditJson updateExamRoomNormalOrDisable(String endPointId, String examRoomIP,String state);
	/**
	 * 根据考点id和ip地址 修改考点子钟信息
	 * @param endPointId
	 * @param examRoomIP
	 * @param examRoomName
	 * @param examRoomAddr
	 * @param examRoomPerson
	 * @param examRoomTel
	 * @return
	 */
	public EditJson updateExamRoomInfo(String endPointId,String examRoomIP,String examRoomName,String examRoomAddr,String examRoomPerson,String examRoomTel);
	/**
	 * 自动检索考场子钟信息
	 * @param endPointId
	 * @return
	 */
	public String findAutoSearchClock(String endPointId);
	/**
	 * 下载考场子钟信息模板
	 * @param response
	 */
	public void getDownloadTemplet(HttpServletResponse response);
	/**
	 * 上传考场子钟信息模板
	 * @param request
	 * @param response
	 * @return
	 */
	public void submitUpload(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 手动添加考场子钟信息
	 * @param examRoomIP 考点子钟IP
	 * @param examRoomName 考场名称
	 * @param examRoomAddr 考场地址
	 * @param examRoomPerson 考场负责人
	 * @param examRoomTel 考场负责人联系电话
	 * @param endpointId 考点id
	 * @return
	 */
	public EditJson addExamRoomInfo(String examRoomIP,String examRoomName,String examRoomAddr,String examRoomPerson,String examRoomTel,String endpointId);
	/**
	 * 将考点自动扫描的子钟ip入库
	 * @param examRoomIp 考场子钟ip
	 * @param endpointId 考点id
	 * @return
	 */
	public EditJson addAutoSearchExamRoomInfo(String examRoomIp,String endpointId);
	/**
	 * 删除考场子钟ip配置
	 * @param endPointId
	 * @param ipStr
	 * @return
	 */
	public EditJson deleteClock(String endPointId,String ipStr);
	
}
