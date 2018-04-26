package cn.com.sparknet.nclk.service;

import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;

/**
 * 运行监控
 *
 * @author tangzj
 */
public interface MonitorService {
    /**
     * 查询异常信息
     * @param userId
     * @param start
     * @param limit
     * @param searchText
     * @param startTime
     * @param endTime
     * @return
     */
    ListJson findErrorInfo(String userId, String start, String limit, String searchText,
                           String startTime, String endTime);

    /**
     * 根据用户权限查询权限树
     *
     * @param userId
     * @return
     */
    ListJson findAllArea(String userId);

    /**
     * 根据权限和状态查询子钟的数量
     *
     * @param userId
     * @return
     */
    EditJson getMonitedCount(String userId, String[] areaIds);

    /**
     * 根据权限查询考点
     *
     * @param userId
     * @param state
     * @param areaIds
     * @return
     */
    ListJson findEndpoints(String userId, String state, String[] areaIds);

    /**
     * 根据考点ID获得所有异常信息
     *
     * @param endpointId
     * @return
     */
    ListJson findDetailError(String endpointId);

    /**
     * 删除异常信息
     *
     * @param ids
     * @return
     */
    EditJson deleteErrorInfo(String[] ids);
}
