package cn.com.sparknet.nclk.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

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
	 * @throws Exception
	 */
	public int addExamRoomInfo(String examRoomId,String examRoomIp, String examRoomName, String examRoomAddr, String examRoomPerson,
			String examRoomTel, String endpointId) throws Exception {
		String sql = " INSERT INTO T_NCLK_EXAMROOM(EXAMROOM_ID,EXAMROOM_IP,EXAMROOM_NAME,EXAMROOM_ADDR,EXAMROOM_PERSON,EXAMROOM_TEL,ENDPOINT_ID,CREATE_DATE,STATE) VALUES (?,?,?,?,?,?,?,?,?) ";
		return baseDao.updateBySql(sql, new Object[]{examRoomId,examRoomIp,examRoomName,examRoomAddr,examRoomPerson,examRoomTel,endpointId,new Date(),"normal"});
	}
	
	/**
	 * 根据状态、考点id查询考场所有监控的子钟的数量
	 * @param endPointId
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> findExamRoomClockCount(String endPointId,String state) throws Exception {
		String sql = " SELECT count(*) AS NORMALCOUNT FROM T_NCLK_EXAMROOM A WHERE A.STATE = ? AND A.ENDPOINT_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{state,endPointId});
	}
	
	/**
	 * 查询所有启用的时钟个数
	 * @param endPointId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> findExamRoomClockNotDisableCount(String endPointId) throws Exception {
		String sql = " SELECT count(*) AS NORMALCOUNT FROM T_NCLK_EXAMROOM A WHERE A.STATE != 'disable' AND A.ENDPOINT_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{endPointId});
	}
	
	/**
	 * 根据状态、考点id查询考场子钟信息
	 * @param endPointId
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findExamRoomClock(String endPointId,String state) throws Exception {
		String sql = " SELECT A.EXAMROOM_IP FROM T_NCLK_EXAMROOM A WHERE A.STATE = ? AND A.ENDPOINT_ID = ? ORDER BY inet_aton(A.EXAMROOM_IP) ASC ";
		return baseDao.findListBySql(sql, new Object[]{state,endPointId});
	}
	
	/**
	 * 根据考点id查询所有启用的考场子钟信息
	 * @param endPointId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findExamRoomClockNotDisable(String endPointId) throws Exception {
		String sql = " SELECT A.EXAMROOM_IP FROM T_NCLK_EXAMROOM A WHERE A.STATE != 'disable' AND A.ENDPOINT_ID = ? ORDER BY inet_aton(A.EXAMROOM_IP) ASC ";
		return baseDao.findListBySql(sql, new Object[]{endPointId});
	}
	
	/**
	 * 根据考点id和考场ip查询子钟信息
	 * @param endPointId
	 * @param examRoomIP
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> findExamRoomClockInfo(String endPointId,String examRoomIP) throws Exception {
		String sql = " SELECT A.EXAMROOM_IP,A.EXAMROOM_ID,A.EXAMROOM_NAME,A.EXAMROOM_ADDR,A.EXAMROOM_PERSON,A.EXAMROOM_TEL,A.STATE FROM T_NCLK_EXAMROOM A WHERE A.EXAMROOM_IP = ? AND A.ENDPOINT_ID = ? ";
		return baseDao.findMapBySql(sql, new Object[]{examRoomIP,endPointId});
	}
	
	/**
	 * 根据考点ip和子钟ip修改子钟信息
	 * @param endPointId
	 * @param examRoomIP
	 * @param examRoomName
	 * @param examRoomAddr
	 * @param examRoomPerson
	 * @param examRoomTel
	 * @return
	 * @throws Exception
	 */
	public int updateExamRoomInfo(String endPointId,String examRoomIP,String examRoomName,String examRoomAddr,String examRoomPerson,String examRoomTel) throws Exception {
		String sql = " UPDATE T_NCLK_EXAMROOM SET EXAMROOM_NAME = ? , EXAMROOM_ADDR = ? , EXAMROOM_PERSON = ? , EXAMROOM_TEL = ? WHERE ENDPOINT_ID = ? AND EXAMROOM_IP = ? ";
		return baseDao.updateBySql(sql, new Object[]{examRoomName,examRoomAddr,examRoomPerson,examRoomTel,endPointId,examRoomIP});
	}

	/**
	 * 根据考点id 考场ip和状态 启用或者停用子钟
	 * @param endPointId
	 * @param examRoomIP
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int updateExamRoomNormalOrDisable(String endPointId, String examRoomIP, String state) throws Exception  {
		String sql = " UPDATE T_NCLK_EXAMROOM SET STATE = ? WHERE ENDPOINT_ID = ? AND EXAMROOM_IP = ? ";
		return baseDao.updateBySql(sql, new Object[]{state,endPointId,examRoomIP});
	}
	
	/**
	 * 根据考点id和子钟id查询考点下是否有此子钟
	 * @param endPointId 考点id
	 * @param examRoomIP 子钟ip
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> findExamRoomIPEXISTS(String endPointId,String examRoomIP) throws Exception {
		String sql = " SELECT A.EXAMROOM_IP FROM T_NCLK_EXAMROOM A WHERE A.ENDPOINT_ID = ? AND A.EXAMROOM_IP = ? ";
		return baseDao.findMapBySql(sql, new Object[]{endPointId,examRoomIP});
	}
	
	/**
	 * 子钟扫描的子钟添加信息
	 * @param examRoomId 考场id
	 * @param examRoomIp 子钟ip
	 * @param endpointId 考点id
	 * @return
	 * @throws Exception
	 */
	public int addAutoSearchExamRoomInfo(String examRoomId,String examRoomIp,String endpointId) throws Exception {
		String sql = " INSERT INTO T_NCLK_EXAMROOM(EXAMROOM_ID,EXAMROOM_IP,ENDPOINT_ID,CREATE_DATE,STATE) VALUES (?,?,?,?,?) ";
		return baseDao.updateBySql(sql, new Object[]{examRoomId,examRoomIp,endpointId,new Date(),"normal"});
	}
	
	/**
	 * 根据考点id查询考场子钟IP
	 * @param endPointId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findExamRoomIPByEndPointId(String endPointId) throws Exception {
		String sql = " SELECT EXAMROOM_IP FROM T_NCLK_EXAMROOM B WHERE B.ENDPOINT_ID = ? ";
		return baseDao.findListBySql(sql, new Object[]{endPointId});
	}
	
	/**
	 * 根据考场id删除考点日志表信息
	 * @param examroomId
	 * @return
	 * @throws Exception
	 */
	public int deleteExamRoomLog(String examroomId) throws Exception {
		String sql = " DELETE FROM T_NCLK_EXAMROOM_LOG WHERE EXAMROOM_ID = ? ";
		return baseDao.updateBySql(sql, new Object[]{examroomId});
	}
	
	/**
	 * 根据考场id删除考点日志历史表信息
	 * @param examroomId
	 * @return
	 * @throws Exception
	 */
	public int deleteExamRoomLogHis(String examroomId) throws Exception {
		String sql = " DELETE FROM T_NCLK_EXAMROOM_LOG_HIS WHERE EXAMROOM_ID = ? ";
		return baseDao.updateBySql(sql, new Object[]{examroomId});
	}
	
	/**
	 * 根据考场id删除考点日志历史表信息
	 * @param examroomId
	 * @return
	 * @throws Exception
	 */
	public int deleteExamRoom(String examroomId) throws Exception {
		String sql = " DELETE FROM T_NCLK_EXAMROOM WHERE EXAMROOM_ID = ? ";
		return baseDao.updateBySql(sql, new Object[]{examroomId});
	}
	
}
