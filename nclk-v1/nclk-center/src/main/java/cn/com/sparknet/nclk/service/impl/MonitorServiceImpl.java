package cn.com.sparknet.nclk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import cn.com.sparknet.common.dao.Page;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.nclk.dao.MonitorDao;
import cn.com.sparknet.nclk.service.MonitorService;

/**
 * @author tangzj
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    @Override
    public ListJson findErrorInfo(String start, String limit, String searchText, String endPointId,
                                  String startTime, String endTime) {
        Page page = null;
        try {
            page = monitorDao.findErrorInfo(start, limit, searchText, endPointId, startTime, endTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ListJson listJson = new ListJson();
        listJson.setTotal(page.getCount());
        listJson.setRows(page.getList());
        return listJson;
    }

}
