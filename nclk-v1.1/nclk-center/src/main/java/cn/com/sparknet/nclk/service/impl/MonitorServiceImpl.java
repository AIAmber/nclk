package cn.com.sparknet.nclk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import cn.com.sparknet.nclk.dao.MonitorDao;
import cn.com.sparknet.nclk.dao.Page;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.MonitorService;

/**
 * 运行监控
 *
 * @author tangzj
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    @Override
    public ListJson findErrorInfo(String userId, String start, String limit, String searchText,
                                  String startTime, String endTime) {
        Page page;
        try {
            page = monitorDao.findErrorInfo(userId, start, limit, searchText, startTime, endTime);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        ListJson listJson = new ListJson();
        listJson.setTotal(page.getCount());
        listJson.setRows(page.getList());
        return listJson;
    }

    @Override
    public ListJson findAllArea(String userId) {
        List<Map<String, Object>> list = monitorDao.findAreaNodes(userId);
        ListJson listJson = new ListJson();
        listJson.setRows(list);
        return listJson;
    }

    @Override
    public EditJson getMonitedCount(String userId, String[] areaIds) {
        Map<String, Object> map = monitorDao.getMonitedClockCount(userId, areaIds);
        Map<String, Object> mapEndpointCount = monitorDao.getMoniteredEndpointCount(userId, areaIds);
        map.put("enableTotal", Integer.parseInt(map.get("normalCount").toString()) + Integer.parseInt(map.get("errorCount").toString()));
        map.put("endpointTotal", mapEndpointCount.get("endpointTotal"));
        EditJson editJson = new EditJson();
        editJson.setSuccess(true);
        editJson.setBean(map);
        return editJson;
    }

    @Override
    public ListJson findEndpoints(String userId, String state, String[] areaIds) {
        List<Map<String, Object>> list = monitorDao.findEndpoints(userId, state, areaIds);
        ListJson listJson = new ListJson();
        listJson.setRows(list);
        return listJson;
    }

    @Override
    public ListJson findDetailError(String endpointId) {
        List<Map<String, Object>> list = monitorDao.findDetailError(endpointId);
        ListJson listJson = new ListJson();
        listJson.setRows(list);
        return listJson;
    }

    @Override
    public EditJson deleteErrorInfo(String[] ids) {
        monitorDao.deleteErrorInfo(ids);
        EditJson editJson = new EditJson();
        editJson.setSuccess(true);
        return editJson;
    }


}
