package cn.com.sparknet.nclk.util;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

/**
 * @author tangzj
 */
public class NtpTime {

    private static NtpTime ntpTime;

    private NtpTime() {
    }

    public static NtpTime getInstance() {
        if (ntpTime == null) {
            synchronized (NtpTime.class) {
                if (ntpTime == null) {
                    ntpTime = new NtpTime();
                }
            }
        }
        return ntpTime;
    }

    /**
     * 本地时间和NTP服务器的时间差
     */
    private long deviation = 0L;

    /**
     * 是否刷新过延迟
     */
    private boolean isRefresh = false;

    /**
     * 从NTP服务器获取时间
     *
     * @param ip ntp服务器IP
     * @return
     */
    public static Date getServerTime(String ip) throws IOException {
        NTPUDPClient timeClient = new NTPUDPClient();
        timeClient.setDefaultTimeout(1000);
        try {
            InetAddress timeServerAddress = InetAddress.getByName(ip);
            TimeInfo btpTimeInfo = timeClient.getTime(timeServerAddress);
            btpTimeInfo.computeDetails();
            Long delay = btpTimeInfo.getDelay();
            long currentTIme = btpTimeInfo.getMessage().getTransmitTimeStamp().getTime() + (delay == null ? 0L : delay / 2);
            return new Date(currentTIme);
        } finally {
            timeClient.close();
        }
    }

    /**
     * 成功调用{@code refreshDeviation}之后，再调用该方法，否则返回null，
     *
     * @return
     */
    public Date getServerTimeWithDeviation() {
        return isRefresh ? new Date(System.currentTimeMillis() + deviation) : null;
    }

    /**
     * 刷新本地时间和服务器时期的偏差
     *
     * @param ip
     */
    public void refreshDeviation(String ip) throws IOException {
        deviation = getServerTime(ip).getTime() - System.currentTimeMillis();
        isRefresh = true;
    }

}
