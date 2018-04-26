package cn.com.sparknet.nclk.endpointapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.com.sparknet.nclk.endpointapi.service.EndPointApiService;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;

/**
 * @author tangzj
 */
@Controller
@RequestMapping("EndPointApiController")
public class EndPointApiController {

    @Resource
    private EndPointApiService endPointApiService;

    /**
     * 根据考点编号查询所有需要监控的子钟
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("findAllMonitoredClock")
    public ListJson findAllMonitoredClock(HttpServletRequest request) {
        String endPointNo = request.getParameter("endPointNo");
        return endPointApiService.findAllMonitoredClock(endPointNo);
    }

    /**
     * 保存子钟错误信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("saveErrorInfo")
    public EditJson saveErrorInfo(HttpServletRequest request) {
        String endPointNo = request.getParameter("endPointNo");
        String ip = request.getParameter("ip");
        String errorType = request.getParameter("errorType");
        String errorMsg = request.getParameter("errorMsg");
        return endPointApiService.saveErrorInfo(endPointNo, ip, errorType, errorMsg);
    }

    /**
     * 获取有异常的子钟信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("findErrorClock")
    public ListJson findErrorClock(HttpServletRequest request) {
        String endPointNo = request.getParameter("endPointNo");
        return endPointApiService.findErrorClock(endPointNo);
    }

    /**
     * 将子钟状态更新为可用
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("enableErrorClock")
    public EditJson enableErrorClock(HttpServletRequest request) {
        String endPointNo = request.getParameter("endPointNo");
        String ip = request.getParameter("ip");
        return endPointApiService.updateClockStateNormal(endPointNo, ip);
    }

}
