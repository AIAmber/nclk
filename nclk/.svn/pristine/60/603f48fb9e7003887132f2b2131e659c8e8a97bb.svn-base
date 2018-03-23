package cn.com.sparknet.nclk.endpointapi.service;

import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;

/**
 * @author tangzj
 */
public interface EndPointApiService {
    /**
     * 根据考点编号查询所有需要监控的子钟
     *
     * @param endPointNo 考点编号
     * @return
     */
    ListJson findAllMonitoredClock(String endPointNo);

    /**
     * 保存子钟错误信息
     *
     * @param endPointNo
     * @param ip
     * @param errorType
     * @param errorMsg
     * @return
     */
    EditJson saveErrorInfo(String endPointNo, String ip, String errorType, String errorMsg);

    /**
     * 获取有异常的子钟信息
     *
     * @param endPointNo
     * @return
     */
    ListJson findErrorClock(String endPointNo);

    /**
     * 将子钟状态更新为可用
     *
     * @param endPointNo
     * @param ip
     * @return
     */
    EditJson updateClockStateNormal(String endPointNo, String ip);
}
