package cn.com.sparknet.nclk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.nclk.service.EndPointService;

/**
 * @author tangzj
 */
@Controller
@RequestMapping("EndPointController")
public class EndPointController {

    @Resource
    private EndPointService endPointService;

    private static final String ENDPOINT_NO = Config.getInstance().getProperty("endPointNo");

    /**
     * 扫描考点下的所有子钟（不一定能扫描到）
     *
     * @return
     */
    @RequestMapping("scanDevice")
    @ResponseBody
    public ListJson scanDevice() {
        return endPointService.scanDevice();
    }

    @RequestMapping("getEndPointNo")
    @ResponseBody
    public EditJson getEndPointNo() {
        EditJson editJson = new EditJson();
        editJson.setSuccess(true);
        editJson.setBean(ENDPOINT_NO);
        return editJson;
    }
}
