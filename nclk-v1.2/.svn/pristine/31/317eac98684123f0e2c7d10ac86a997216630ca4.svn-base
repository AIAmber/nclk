package cn.com.sparknet.nclk.service;

import java.util.List;

import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.nclk.endpointserver.bean.TimeInfo;
import cn.com.sparknet.nclk.endpointserver.core.ClockErrorType;

/**
 * @author tangzj
 */
public interface EndPointService {

    /**
     * 获得母钟时间
     *
     * @return
     */
    TimeInfo getParentClock();

    /**
     * 向中心提交子钟异常信息
     *
     * @param ip
     * @param errorType
     * @param errorMsg
     */
    void sendErrorInfo(String ip, ClockErrorType errorType, String errorMsg);

    /**
     * 从中心获取所有需要监控的子钟信息
     *
     * @return
     */
    List<String> findAllMonitoredClock();

    /**
     * 扫描局域网内子钟信息
     *
     * @return
     */
    ListJson scanDevice();

    /**
     * 获取有异常的子钟信息
     *
     * @return
     */
    List<String> findErrorClock();

    /**
     * 将子钟状态更新为可用
     *
     * @param ip
     */
    void enableErrorClock(String ip);
}
