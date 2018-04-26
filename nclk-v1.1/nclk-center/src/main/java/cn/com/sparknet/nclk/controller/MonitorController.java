package cn.com.sparknet.nclk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.sparknet.nclk.config.Config;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.MonitorService;
import cn.com.sparknet.nclk.util.NtpTime;
import cn.com.sparknet.nclk.util.StringUtil;

/**
 * 运行监控
 *
 * @author tangzj
 */
@Controller
@RequestMapping("MonitorController")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    /**
     * 异常信息查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("findErrorInfo")
    public ListJson findErrorInfo(HttpServletRequest request) {
        String start = StringUtil.nullToEmpty(request.getParameter("start"));
        String limit = StringUtil.nullToEmpty(request.getParameter("limit"));
        String searchText = StringUtil.nullToEmpty(request.getParameter("searchText"));
        String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
        String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
        String userId = request.getSession().getAttribute("USER_ID").toString();
        return monitorService.findErrorInfo(userId, start, limit, searchText, startTime, endTime);
    }

    @ResponseBody
    @RequestMapping("deleteErrorInfo")
    public EditJson deleteErrorInfo(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        return monitorService.deleteErrorInfo(ids.split(";"));
    }

    /**
     * 获取定时任务运行周期
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("getIntervalTime")
    public EditJson getIntervalTime() {
        EditJson editJson = new EditJson();
        editJson.setSuccess(true);
        editJson.setBean(Integer.parseInt(Config.getInstance().getProperty("center.intervalTime")));
        return editJson;
    }

    /**
     * 根据用户权限查询权限树
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("findAllArea")
    public ListJson findAllArea(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("USER_ID").toString();
        return monitorService.findAllArea(userId);
    }

    /**
     * 查询得到时钟的个数统计信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("monitorCount")
    public EditJson monitorCount(HttpServletRequest request) {
        String userId = request.getSession().getAttribute("USER_ID").toString();
        String areaIds = request.getParameter("areaIds");
        return monitorService.getMonitedCount(userId, areaIds == null ? null : areaIds.split(";"));
    }

    /**
     * 根据权限查询考点
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("findEndpoints")
    public ListJson findEndpoints(HttpServletRequest request) {
        String areaIds = request.getParameter("areaIds");
        String state = request.getParameter("state");
        String userId = request.getSession().getAttribute("USER_ID").toString();
        if (!"error".equals(state)) {
            state = "all";
        }
        return monitorService.findEndpoints(userId, state, areaIds == null ? null : areaIds.split(";"));
    }

    /**
     * 根据考点ID查询所有异常子钟及异常信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("findDetailError")
    public ListJson findDetailError(HttpServletRequest request) {
        String endpointId = request.getParameter("endpointId");
        return monitorService.findDetailError(endpointId);
    }

    @ResponseBody
    @RequestMapping("getClockTime")
    public EditJson getClockTime(HttpServletRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = NtpTime.getInstance().getServerTimeWithDeviation();
        date = date == null ? new Date() : date;
        Map<String, Object> map = new HashMap<>(2);
        map.put("datetime", dateFormat.format(date));
        map.put("timestamp", date.getTime());
        EditJson editJson = new EditJson();
        editJson.setSuccess(true);
        editJson.setBean(map);
        return editJson;
    }
}

