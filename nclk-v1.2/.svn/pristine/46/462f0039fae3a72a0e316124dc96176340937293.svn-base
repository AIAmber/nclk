package cn.com.sparknet.nclk.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.sparknet.nclk.service.MonitorService;

/**
 * 该定时任务用于在每周日凌晨1点钟删除一周前的数据
 *
 * @author tangzj
 */
@Component
public class ExamroomLogTask {

    @Autowired
    private MonitorService monitorService;

    @Scheduled(cron = "${center.log.deleteLogCron}")
    public void getNtpTime() {
        monitorService.deleteOldLog();
        monitorService.deleteOldHisLog();
    }
}
