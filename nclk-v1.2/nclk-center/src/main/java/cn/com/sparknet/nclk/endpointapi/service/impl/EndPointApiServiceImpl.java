package cn.com.sparknet.nclk.endpointapi.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.sparknet.nclk.endpointapi.dao.EndPointApiDao;
import cn.com.sparknet.nclk.endpointapi.service.EndPointApiService;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;

/**
 * @author tangzj
 */
@Service
public class EndPointApiServiceImpl implements EndPointApiService {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Resource
    private EndPointApiDao endPointApiDao;

    @Override
    public ListJson findAllMonitoredClock(String endPointNo) {
        List<Map<String, Object>> list = endPointApiDao.findAllMonitoredClock(endPointNo);
        List<String> ipList = new ArrayList<>(list.size());
        for (Map<String, Object> map : list) {
            ipList.add(map.get("EXAMROOM_IP").toString());
        }
        ListJson listJson = new ListJson();
        listJson.setTotal(ipList.size());
        listJson.setRows(ipList);
        return listJson;
    }

    @Override
    public EditJson saveErrorInfo(String endPointNo, String ip, String errorType, String errorMsg) {
        EditJson editJson = new EditJson();
        Map<String, Object> map = endPointApiDao.getExamRoom(endPointNo, ip);
        Object examRoomIdObj = map.get("EXAMROOM_ID");
        if (!checkEndPointNo(endPointNo, ip, editJson, examRoomIdObj)) {
            return editJson;
        }
        String examRoomId = examRoomIdObj.toString();
        endPointApiDao.updateClockState(examRoomId, "error");
        endPointApiDao.saveOrUpdateErrorInfo(examRoomId, errorType, errorMsg);
        endPointApiDao.saveErrorHistory(examRoomId, errorType, errorMsg);
        editJson.setSuccess(true);
        return editJson;
    }

    @Override
    public ListJson findErrorClock(String endPointNo) {
        List<Map<String, Object>> list = endPointApiDao.findErrorClock(endPointNo);
        List<String> ipList = new ArrayList<>(list.size());
        for (Map<String, Object> map : list) {
            ipList.add(map.get("EXAMROOM_IP").toString());
        }
        ListJson listJson = new ListJson();
        listJson.setTotal(ipList.size());
        listJson.setRows(ipList);
        return listJson;
    }

    @Override
    public EditJson updateClockStateNormal(String endPointNo, String ip) {
        EditJson editJson = new EditJson();
        Map<String, Object> map = endPointApiDao.getExamRoom(endPointNo, ip);
        Object examRoomIdObj = map.get("EXAMROOM_ID");
        if (!checkEndPointNo(endPointNo, ip, editJson, examRoomIdObj)) {
            return editJson;
        }
        endPointApiDao.updateClockState(examRoomIdObj.toString(), "normal");
        editJson.setSuccess(true);
        return editJson;
    }

    /**
     * 检查考点编号是否有效
     *
     * @param endPointNo
     * @param ip
     * @param editJson
     * @param examRoomIdObj
     * @return true：有效；false：无效
     */
    private boolean checkEndPointNo(String endPointNo, String ip, EditJson editJson, Object examRoomIdObj) {
        if (examRoomIdObj == null || "".equals(examRoomIdObj)) {
            editJson.setSuccess(false);
            String errorStr = "无法根据考点编号[" + endPointNo + "]和IP[" + ip + "]地址找到对应的子钟";
            editJson.setBean(errorStr);
            logger.error(errorStr);
            return false;
        }
        return true;
    }

}
