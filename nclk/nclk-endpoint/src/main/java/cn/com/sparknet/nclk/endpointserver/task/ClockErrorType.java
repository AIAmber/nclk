package cn.com.sparknet.nclk.endpointserver.task;

/**
 * 子钟异常状态
 *
 * @author tangzj
 */
public enum ClockErrorType {
    /**
     * 时间不一致
     */
    TIME_NOT_CONSISTENT,
    /**
     * 连接异常，子钟没有连接服务器
     */
    NO_CONNECTION,
    /**
     * 上报超时
     */
    REPORT_TIMEOUT;

    public String toMsgString() {
        String msg;
        switch (this) {
            case NO_CONNECTION:
                msg = "连接异常";
                break;
            case TIME_NOT_CONSISTENT:
                msg = "时间不一致";
                break;
            case REPORT_TIMEOUT:
                msg = "上报超时";
                break;
            default:
                msg = "";
                break;
        }
        return msg;
    }
}
