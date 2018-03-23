package cn.com.sparknet.nclk.endpointserver.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.nclk.service.EndPointService;
import io.netty.util.internal.ConcurrentSet;

import static cn.com.sparknet.nclk.endpointserver.task.ClockErrorType.NO_CONNECTION;

/**
 * @author tangzj
 */
@Component
public class CycleCheckTask {

    /**
     * 所有已经连接的子钟
     */
    private static final Map<String, Long> ALL_CONNECTED_DEVICES = new ConcurrentHashMap<>();
    /**
     * 健康的子钟
     */
    private static final Set<String> HEALTHY_CONNECTED_DEVICES = new ConcurrentSet<>();
    private static final int REPORT_TIMIEOUT = Integer.parseInt(Config.getInstance().getProperty("reportserver.report.timieout"));
    private static final String REPORT_TIMIEOUT_MSG = "网络时钟超过" + REPORT_TIMIEOUT + "秒没有上报数据";
    private static final String NO_CONNECTION_MSG = "网络时钟未连接服务器，处于离线状态";

    @Resource
    private EndPointService endPointService;

    /**
     * 周期性的从母钟获取时间
     */
    @Scheduled(fixedDelayString = "${scheduleCycleTime}")
    public void updateParentClockInfo() {
        endPointService.updateParentClockTime();
    }

    /**
     * 定时从中心获得需要检测的子钟，知道哪些子钟需要检测<br/>
     * 没有连接的子钟，没有及时上报数据的子钟，都进行上报
     */
    @Scheduled(fixedDelayString = "${scheduleCycleTime}")
    public void updateClockReportState() {
        List<String> ips = endPointService.findAllMonitoredClock();
        if (ips == null || ips.size() == 0) {
            return;
        }
        long curTime = System.currentTimeMillis();
        for (String ip : ips) {
            if (ALL_CONNECTED_DEVICES.containsKey(ip)) {
                long time = ALL_CONNECTED_DEVICES.get(ip);
                long intervalTime = (curTime - time) / 1000;
                if (intervalTime > REPORT_TIMIEOUT) {
                    removeHealth(ip);
                    endPointService.sendErrorInfo(ip, ClockErrorType.REPORT_TIMEOUT, REPORT_TIMIEOUT_MSG);
                }
            } else {
                removeHealth(ip);
                endPointService.sendErrorInfo(ip, NO_CONNECTION, NO_CONNECTION_MSG);
            }
        }
    }

    /**
     * 定时从服务器获取异常状态的子钟，如果子钟正常了，告知中心
     */
    @Scheduled(fixedDelayString = "${scheduleCycleTime}")
    public void updateClockState() {
        List<String> ips = endPointService.findErrorClock();
        if (ips == null || ips.size() == 0) {
            return;
        }
        for (String ip : ips) {
            if (HEALTHY_CONNECTED_DEVICES.contains(ip)) {
                endPointService.enableErrorClock(ip);
            }
        }
    }

    /**
     * 更新子钟上报时间
     *
     * @param ip
     */
    public static void updateClock(String ip) {
        ALL_CONNECTED_DEVICES.put(ip, System.currentTimeMillis());
        HEALTHY_CONNECTED_DEVICES.add(ip);
    }

    /**
     * 子钟断开连接后，删除子钟信息
     *
     * @param ip
     */
    public static void removeClock(String ip) {
        ALL_CONNECTED_DEVICES.remove(ip);
        HEALTHY_CONNECTED_DEVICES.remove(ip);
    }

    /**
     * 从健康的列表中删除
     *
     * @param ip
     */
    public static void removeHealth(String ip) {
        HEALTHY_CONNECTED_DEVICES.remove(ip);
    }

}
