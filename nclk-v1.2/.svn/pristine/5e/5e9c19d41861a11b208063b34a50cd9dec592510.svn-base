package cn.com.sparknet.nclk.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.sparknet.nclk.config.Config;
import cn.com.sparknet.nclk.util.NtpTime;

/**
 * ntp时间获取任务<br/>
 * ntp协议通过UDP发送，由于网络原因，不是每次都能取到数据，因此产生该定时任务，做优化
 *
 * @author tangzj
 */
@Component
public class NtpTimeTask {
    private Logger logger = Logger.getLogger(NtpTimeTask.class.getName());

    /**
     * NTP服务器IP
     */
    private static final String NTP_SERVER_IP = Config.getInstance().getProperty("center.ntp.ip");

    @Scheduled(fixedDelayString = "${center.ntp.scheduleCycleTime}")
    public void getNtpTime() {
        try {
            NtpTime.getInstance().refreshDeviation(NTP_SERVER_IP);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
