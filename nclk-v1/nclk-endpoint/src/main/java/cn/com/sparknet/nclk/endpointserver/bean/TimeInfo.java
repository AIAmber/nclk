package cn.com.sparknet.nclk.endpointserver.bean;

/**
 * 上报时间对象
 *
 * @author tangzj
 */
public class TimeInfo {
    private int hour;
    private int minute;
    private int second;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * 以一天的0点位起始点，得到当前时间与0点相差多少
     *
     * @return
     */
    public long getTime() {
        return hour * 3600L + minute * 60L + second;
    }
}
