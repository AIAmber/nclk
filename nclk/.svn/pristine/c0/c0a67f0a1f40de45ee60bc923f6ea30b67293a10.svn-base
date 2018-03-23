package cn.com.sparknet.nclk.devconfig.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.com.sparknet.common.dao.BaseDao;

/**
 * 网络时钟终端配置
 * @author wuyl
 *
 */
@Repository
public class ClockTerminalConfigDao {
	
	@Resource
	private BaseDao baseDao;

	/**
	 * 手动添加考场子钟信息
	 * @param examRoomIp 考场子钟ip
	 * @param examRoomName 考场名称
	 * @param examRoomAddr 考场地址
	 * @param examRoomPerson 考场负责人名称
	 * @param examRoomTel 考场电话
	 * @param endpointId 考点编号
	 * @return
	 */
	public int addExamRoomInfo(String examRoomId,String examRoomIp, String examRoomName, String examRoomAddr, String examRoomPerson,
			String examRoomTel, String endpointId,int ord) {
		String sql = " INSERT INTO T_NCLK_EXAMROOM(EXAMROOM_ID,EXAMROOM_IP,EXAMROOM_NAME,EXAMROOM_ADDR,EXAMROOM_PERSON,EXAMROOM_TEL,ENDPOINT_ID,CREATE_DATE,STATE,ORD) VALUES (?,?,?,?,?,?,?,?,?,?) ";
		return baseDao.updateBySql(sql, new Object[]{examRoomId,examRoomIp,examRoomName,examRoomAddr,examRoomPerson,examRoomTel,endpointId,new Date(),"normal",ord});
	}
	
	/**
	 * 根据状态、考点id查询考场所有监控的子钟的数量
	 * @param endPointId
	 * @return
	 */
	public Map<String,Object> findExamRoomClockCount(String endPointId,String state){
		String sql = " SELECT count(*) AS NORMALCOUNT FROM T_NCLK_EXAMROOM A WHERE A.STATE = ? AND A.ENDPOINT_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{state,endPointId});
	}
	
	/**
	 * 根据状态、考点id查询考场子钟信息
	 * @param endPointId
	 * @return
	 */
	public List<Map<String,Object>> findExamRoomClock(String endPointId,String state){
		String sql = " SELECT A.EXAMROOM_IP FROM T_NCLK_EXAMROOM A WHERE A.STATE = ? AND A.ENDPOINT_ID = ? ";
		return baseDao.findListBySql(sql, new Object[]{state,endPointId});
	}
	
	/**
	 * 根据考点id查询考场所有禁用的子钟的数量
	 * @param endPointId
	 * @return
	 */
	public Map<String,Object> findExamRoomClockInfo(String endPointId,String examRoomIP){
		String sql = " SELECT A.EXAMROOM_IP,A.EXAMROOM_ID,A.EXAMROOM_NAME,A.EXAMROOM_ADDR,A.EXAMROOM_PERSON,A.EXAMROOM_TEL,A.STATE,A.ORD FROM T_NCLK_EXAMROOM A WHERE A.EXAMROOM_IP = ? AND A.ENDPOINT_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{examRoomIP,endPointId});
	}
	
}
