package cn.com.sparknet.nclk.endpointapi.dao;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.sparknet.nclk.dao.BaseDao;
import cn.com.sparknet.nclk.util.UUIDUtil;

/**
 * @author tangzj
 */
@Repository
public class EndPointApiDao {

    @Resource
    private BaseDao baseDao;

    /**
     * 查询指定考点下，非停用状态的子钟
     *
     * @param endPointNo
     * @return
     */
    public List<Map<String, Object>> findAllMonitoredClock(String endPointNo) {
        StringBuilder builder = new StringBuilder();
        builder.append("select t.EXAMROOM_IP ");
        builder.append("from T_NCLK_EXAMROOM t ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e on e.ENDPOINT_ID = t.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA a on a.AREA_ID = e.AREA_ID ");
        builder.append("where (t.STATE = 'normal' or t.STATE = 'error') ");
        builder.append("and e.STATE = 'A' AND a.STATE = 'A' and e.ENDPOINT_NO = ? ");
        return baseDao.findListBySql(builder.toString(), new Object[]{endPointNo});
    }

    /**
     * 根据考点编号和IP地址唯一获得一个考场
     *
     * @param endPointNo
     * @param ip
     * @return
     */
    public Map<String, Object> getExamRoom(String endPointNo, String ip) {
        StringBuilder builder = new StringBuilder();
        builder.append("select t.EXAMROOM_ID ");
        builder.append("from T_NCLK_EXAMROOM t ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e on e.ENDPOINT_ID = t.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA a on a.AREA_ID = e.AREA_ID ");
        builder.append("where e.ENDPOINT_NO = ? and e.STATE = 'A' and a.STATE = 'A' and t.EXAMROOM_IP = ? ");
        return baseDao.findMapBySql(builder.toString(), new Object[]{endPointNo, ip});
    }

    /**
     * 新增或修改异常实时日志
     *
     * @param examRoomId
     * @param errorType
     * @param errorMsg
     */
    public void saveOrUpdateErrorInfo(String examRoomId, String errorType, String errorMsg) {
        String sql = "update T_NCLK_EXAMROOM_LOG set ERROR_TYPE = ?,ERROR_DESC = ?,ERROR_DATE = ? where EXAMROOM_ID = ?";
        int count = baseDao.updateBySql(sql, new Object[]{errorType, errorMsg, new Date(), examRoomId});
        if (count == 0) {
            sql = "insert into T_NCLK_EXAMROOM_LOG(EXAMROOM_LOG_ID,EXAMROOM_ID,ERROR_TYPE,ERROR_DESC,ERROR_DATE) values(?,?,?,?,?)";
            baseDao.updateBySql(sql, new Object[]{UUIDUtil.getNextValue(), examRoomId, errorType, errorMsg, new Date()});
        }
    }

    /**
     * 新增异常历史日志
     *
     * @param examRoomId
     * @param errorType
     * @param errorMsg
     */
    public void saveErrorHistory(String examRoomId, String errorType, String errorMsg) {
        String sql = "insert into T_NCLK_EXAMROOM_LOG_HIS(EXAMROOM_LOG_HIS_ID,EXAMROOM_ID,ERROR_TYPE,ERROR_DESC,ERROR_DATE) values(?,?,?,?,?)";
        baseDao.updateBySql(sql, new Object[]{UUIDUtil.getNextValue(), examRoomId, errorType, errorMsg, new Date()});
    }

    /**
     * 查询有异常的子钟
     *
     * @param endPointNo
     * @return
     */
    public List<Map<String, Object>> findErrorClock(String endPointNo) {
        StringBuilder builder = new StringBuilder();
        builder.append("select t.EXAMROOM_IP ");
        builder.append("from T_NCLK_EXAMROOM t ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e on e.ENDPOINT_ID = t.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA a on a.AREA_ID = e.AREA_ID ");
        builder.append("where t.STATE = 'error' and e.STATE = 'A' and a.STATE = 'A' and e.ENDPOINT_NO = ? ");
        return baseDao.findListBySql(builder.toString(), new Object[]{endPointNo});
    }

    /**
     * 更新子钟状态信息
     *
     * @param examRoomId
     * @param state      normal,error,disable
     */
    public void updateClockState(String examRoomId, String state) {
        String sql = "update T_NCLK_EXAMROOM set STATE = ? where EXAMROOM_ID = ?";
        baseDao.updateBySql(sql, new Object[]{state, examRoomId});
    }

}
