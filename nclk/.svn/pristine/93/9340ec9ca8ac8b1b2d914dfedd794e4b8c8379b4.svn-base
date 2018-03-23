package cn.com.sparknet.nclk.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.sparknet.common.dao.BaseDao;
import cn.com.sparknet.common.dao.Page;

/**
 * @author tangzj
 */
@Repository
public class MonitorDao {

    @Autowired
    private BaseDao baseDao;

    public Page findErrorInfo(String start, String limit, String searchText, String endPointId,
                              String startTime, String endTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT r.EXAMROOM_ID ID,r.EXAMROOM_NAME,r.EXAMROOM_IP,t.ERROR_DATE,t.ERROR_TYPE,t.ERROR_DESC ");
        builder.append("FROM T_NCLK_EXAMROOM_LOG_HIS t ");
        builder.append("INNER JOIN T_NCLK_EXAMROOM r ON t.EXAMROOM_ID = r.EXAMROOM_ID where 1=1 ");
        if (StringUtils.isNotBlank(searchText)) {
            builder.append("and (r.EXAMROOM_NAME LIKE ? OR r.EXAMROOM_IP LIKE ?) ");
            params.add("%" + searchText + "%");
            params.add("%" + searchText + "%");
        }
        if (StringUtils.isNotBlank(endPointId)) {
            builder.append("and r.ENDPOINT_ID ");
            params.add("%" + endPointId + "%");
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            builder.append("and t.ERROR_DATE between ? and ? ");
            params.add(dateFormat.parse(startTime));
            params.add(dateFormat.parse(endTime));
        }
        Page page = baseDao.findPageAllBySql(builder.toString(), start, limit, params.toArray());
        for (Map<String, Object> map : page.getList()) {
            map.put("ERROR_DATE", dateFormat.format(dateFormat.parse(map.get("ERROR_DATE").toString())));
        }
        return page;
    }
}
