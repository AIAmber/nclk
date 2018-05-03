package cn.com.sparknet.nclk.endpointserver.core;

/**
 * 子钟异常状态
 *
 * @author tangzj
 */
public enum ClockErrorType {
    /**
     * 时间误差
     */
    TIME_NOT_CONSISTENT,
    /**
     * 时钟掉线，子钟没有连接服务器
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
                msg = "网络异常";
                break;
            case TIME_NOT_CONSISTENT:
                msg = "时间误差";
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
