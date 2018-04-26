package cn.com.sparknet.nclk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.nclk.service.MonitorService;

/**
 * @author tangzj
 */
@Controller
@RequestMapping("MonitorController")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @ResponseBody
    @RequestMapping("findErrorInfo")
    public ListJson findErrorInfo(HttpServletRequest request) {
        String start = StringUtil.nullToEmpty(request.getParameter("start"));
        String limit = StringUtil.nullToEmpty(request.getParameter("limit"));
        String searchText = StringUtil.nullToEmpty(request.getParameter("searchText"));
        String endPointId = StringUtil.nullToEmpty(request.getParameter("endPointId"));
        String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
        String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
        return monitorService.findErrorInfo(start, limit, searchText, endPointId, startTime, endTime);
    }
}

