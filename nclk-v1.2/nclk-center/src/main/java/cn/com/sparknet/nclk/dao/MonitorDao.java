package cn.com.sparknet.nclk.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 运行监控
 *
 * @author tangzj
 */
@Repository
public class MonitorDao {

    @Autowired
    private BaseDao baseDao;

    /**
     * 查询异常日志
     *
     * @param userId
     * @param start
     * @param limit
     * @param searchText
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public Page findErrorInfo(String userId, String start, String limit, String searchText,
                              String startTime, String endTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT t.EXAMROOM_LOG_ID,e.ENDPOINT_NAME, a.AREA_NAME, pa.AREA_NAME 'CITY_NAME', r.EXAMROOM_ID EXAMROOM_ID, ");
        builder.append("r.EXAMROOM_NAME, r.EXAMROOM_IP, t.ERROR_DATE, t.ERROR_TYPE, t.ERROR_DESC,r.EXAMROOM_ADDR, ");
        builder.append("r.EXAMROOM_PERSON,r.EXAMROOM_TEL,e.ENDPOINT_PERSON,e.ENDPOINT_TEL ");
        builder.append("FROM T_NCLK_EXAMROOM_LOG t ");
        builder.append("INNER JOIN T_NCLK_EXAMROOM r ON t.EXAMROOM_ID = r.EXAMROOM_ID ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e ON e.ENDPOINT_ID = r.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_USER_ENDPOINT ue ON ue.ENDPOINT_ID = e.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA a ON a.AREA_ID = e.AREA_ID ");
        builder.append("INNER JOIN T_NCLK_AREA pa ON a.PARENT_AREA_ID = pa.AREA_ID ");
        builder.append("where ue.USER_ID = ? and e.STATE = 'A' and a.STATE = 'A' ");
        params.add(userId);
        if (StringUtils.isNotBlank(searchText)) {
            builder.append("and (r.EXAMROOM_NAME LIKE ? OR r.EXAMROOM_IP LIKE ? OR e.ENDPOINT_NAME LIKE ?) ");
            params.add("%" + searchText + "%");
            params.add("%" + searchText + "%");
            params.add("%" + searchText + "%");
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            builder.append("and t.ERROR_DATE between ? and ? ");
            params.add(dateFormat.parse(startTime));
            params.add(dateFormat.parse(endTime));
        }
        builder.append("order by t.ERROR_DATE desc ");
        Page page = baseDao.findPageAllBySql(builder.toString(), start, limit, params.toArray());
        for (Map<String, Object> map : page.getList()) {
            map.put("ERROR_DATE", dateFormat.format(dateFormat.parse(map.get("ERROR_DATE").toString())));
        }
        return page;
    }

    /**
     * 查询历史异常日志
     *
     * @param examroomId
     * @param userId
     * @param start
     * @param limit
     * @param searchText
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public Page findHistoryErrorInfo(String examroomId, String userId, String start, String limit, String searchText,
                                     String startTime, String endTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT t.EXAMROOM_LOG_HIS_ID,e.ENDPOINT_NAME, a.AREA_NAME, pa.AREA_NAME 'CITY_NAME', r.EXAMROOM_ID EXAMROOM_ID, ");
        builder.append("r.EXAMROOM_NAME, r.EXAMROOM_IP, t.ERROR_DATE, t.ERROR_TYPE, t.ERROR_DESC,r.EXAMROOM_ADDR, ");
        builder.append("r.EXAMROOM_PERSON,r.EXAMROOM_TEL,e.ENDPOINT_PERSON,e.ENDPOINT_TEL ");
        builder.append("FROM T_NCLK_EXAMROOM_LOG_HIS t ");
        builder.append("INNER JOIN T_NCLK_EXAMROOM r ON t.EXAMROOM_ID = r.EXAMROOM_ID ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e ON e.ENDPOINT_ID = r.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_USER_ENDPOINT ue ON ue.ENDPOINT_ID = e.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA a ON a.AREA_ID = e.AREA_ID ");
        builder.append("INNER JOIN T_NCLK_AREA pa ON a.PARENT_AREA_ID = pa.AREA_ID ");
        builder.append("where ue.USER_ID = ? and e.STATE = 'A' and a.STATE = 'A' and t.EXAMROOM_ID = ? ");
        params.add(userId);
        params.add(examroomId);
        if (StringUtils.isBlank(searchText) && StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
            builder.append("and t.ERROR_DATE > ? ");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 2);
            params.add(calendar.getTime());
        }
        if (StringUtils.isNotBlank(searchText)) {
            builder.append("and (r.EXAMROOM_NAME LIKE ? OR r.EXAMROOM_IP LIKE ? OR e.ENDPOINT_NAME LIKE ?) ");
            params.add("%" + searchText + "%");
            params.add("%" + searchText + "%");
            params.add("%" + searchText + "%");
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            builder.append("and t.ERROR_DATE between ? and ? ");
            params.add(dateFormat.parse(startTime));
            params.add(dateFormat.parse(endTime));
        }
        builder.append("order by t.ERROR_DATE desc ");
        Page page = baseDao.findPageAllBySql(builder.toString(), start, limit, params.toArray());
        for (Map<String, Object> map : page.getList()) {
            map.put("ERROR_DATE", dateFormat.format(dateFormat.parse(map.get("ERROR_DATE").toString())));
        }
        return page;
    }

    /**
     * 查询用户拥有的所有区域
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findAreaNodes(String userId) {
        StringBuilder builder = new StringBuilder();
        builder.append("select t.AREA_NAME \"name\",t.AREA_ID \"id\",t.PARENT_AREA_ID \"pId\" from T_NCLK_AREA t ");
        builder.append("INNER JOIN T_NCLK_USER_AREA ua on ua.AREA_ID = t.AREA_ID ");
        builder.append("where ua.USER_ID = ? and t.STATE = 'A' ORDER BY t.ORD asc");
        return baseDao.findListBySql(builder.toString(), new Object[]{userId});
    }

    /**
     * 权限下所有考点的异常及正常的时钟数量
     *
     * @param userId
     * @return
     */
    public Map<String, Object> getMonitedClockCount(String userId, String[] areaIds) {
        StringBuilder builder = new StringBuilder();
        List<String> params = new ArrayList<>();
        builder.append("SELECT ");
        builder.append("COUNT(CASE WHEN r.STATE = 'normal' THEN 'normal' END) 'normalCount',");
        builder.append("COUNT(CASE WHEN r.STATE = 'error' THEN 'error' END) 'errorCount' ");
        builder.append("FROM T_NCLK_EXAMROOM r ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e ON e.ENDPOINT_ID = r.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_USER_ENDPOINT ue ON ue.ENDPOINT_ID = e.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA qu ON qu.AREA_ID = e.AREA_ID ");
        builder.append("WHERE e.STATE = 'A' AND qu.STATE = 'A' AND ue.USER_ID = ? ");
        params.add(userId);
        makeAreaCondition(areaIds, builder, params);
        return baseDao.findMapBySql(builder.toString(), params.toArray());
    }

    /**
     * 权限下所有正在监控的考点（考点下不全是disabled状态的子钟）
     *
     * @param userId
     * @return
     */
    public Map<String, Object> getMoniteredEndpointCount(String userId, String[] areaIds) {
        StringBuilder builder = new StringBuilder();
        List<String> params = new ArrayList<>();
        builder.append("SELECT count(*) 'endpointTotal' ");
        builder.append("FROM T_NCLK_ENDPOINT e ");
        builder.append("INNER JOIN (SELECT ENDPOINT_ID FROM T_NCLK_EXAMROOM ");
        builder.append("WHERE STATE = 'normal' OR STATE = 'error' GROUP BY ENDPOINT_ID ");
        builder.append(") t ON t.ENDPOINT_ID = e.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_USER_ENDPOINT ue ON ue.ENDPOINT_ID = e.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA qu ON qu.AREA_ID = e.AREA_ID ");
        builder.append("WHERE e.STATE = 'A' AND qu.STATE = 'A' AND ue.USER_ID = ? ");
        params.add(userId);
        makeAreaCondition(areaIds, builder, params);
        return baseDao.findMapBySql(builder.toString(), params.toArray());
    }

    /**
     * 创建区域查询条件
     *
     * @param areaIds
     * @param builder
     * @param params
     */
    private void makeAreaCondition(String[] areaIds, StringBuilder builder, List<String> params) {
        if (areaIds != null && areaIds.length > 0) {
            builder.append("and qu.AREA_ID in ( ");
            for (String areaId : areaIds) {
                builder.append("?,");
                params.add(areaId);
            }
            builder.setLength(builder.length() - 1);
            builder.append(") ");
        }
    }

    /**
     * 根据权限和所属区域查询考点信息
     *
     * @param userId
     * @param state   考点状态，normal（正常考点）、error（异常考点）、all（所有考点）
     * @param areaIds
     * @return
     */
    public List<Map<String, Object>> findEndpoints(String userId, String state, String[] areaIds) {
        StringBuilder builder = new StringBuilder();
        List<String> params = new ArrayList<>();
        builder.append("select * from (");
        builder.append("SELECT e.ENDPOINT_ID,e.ENDPOINT_NAME,c.AREA_NAME CITY_NAME,qu.AREA_NAME AREA_NAME,");
        builder.append("c.AREA_ID CITY_ID,qu.AREA_ID AREA_ID,");
        builder.append("COUNT(CASE WHEN r.STATE = 'error' THEN 'error' END) 'ERROR_COUNT',");
        builder.append("COUNT(CASE WHEN r.STATE = 'disable' THEN 'disable' END) 'DISABLE_COUNT',");
        builder.append("COUNT(CASE WHEN r.STATE = 'normal' THEN 'normal' END) 'NORMAL_COUNT' ");
        builder.append("FROM T_NCLK_ENDPOINT e ");
        builder.append("INNER JOIN T_NCLK_EXAMROOM r ON e.ENDPOINT_ID = r.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_USER_ENDPOINT ue ON ue.ENDPOINT_ID = e.ENDPOINT_ID ");
        builder.append("INNER JOIN T_NCLK_AREA qu on qu.AREA_ID = e.AREA_ID ");
        builder.append("INNER JOIN T_NCLK_AREA c on c.AREA_ID = qu.PARENT_AREA_ID ");
        builder.append("WHERE e.STATE = 'A' and qu.STATE = 'A' and ue.USER_ID = ? ");
        params.add(userId);
        makeAreaCondition(areaIds, builder, params);
        builder.append("GROUP BY e.ENDPOINT_ID,e.ENDPOINT_NAME,c.AREA_NAME,qu.AREA_NAME ");
        builder.append(") t where (t.NORMAL_COUNT > 0 OR t.ERROR_COUNT > 0) ");
        if (!"all".equals(state)) {
            builder.append("normal".equals(state) ? "and t.ERROR_COUNT = 0 " : "and t.ERROR_COUNT > 0 ");
        }
        builder.append("order by t.CITY_NAME asc,t.AREA_NAME asc,t.ENDPOINT_NAME asc ");
        return baseDao.findListBySql(builder.toString(), params.toArray());
    }

    /**
     * 查询考点下面所有异常考场
     *
     * @param endpointId
     * @return
     */
    public List<Map<String, Object>> findDetailError(String endpointId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT r.EXAMROOM_ID, r.EXAMROOM_IP, r.EXAMROOM_NAME, r.EXAMROOM_ADDR,r.EXAMROOM_PERSON,");
        builder.append("r.EXAMROOM_TEL,e.ENDPOINT_PERSON,e.ENDPOINT_TEL, l.ERROR_TYPE, l.ERROR_DESC ");
        builder.append("FROM T_NCLK_EXAMROOM r ");
        builder.append("INNER JOIN T_NCLK_ENDPOINT e ON e.ENDPOINT_ID = r.ENDPOINT_ID ");
        builder.append("LEFT JOIN T_NCLK_EXAMROOM_LOG l ON r.EXAMROOM_ID = l.EXAMROOM_ID ");
        builder.append("WHERE r.STATE = 'error' AND e.ENDPOINT_ID = ? ");
        builder.append("ORDER BY INET_ATON(EXAMROOM_IP) ASC,r.EXAMROOM_NAME ASC ");
        return baseDao.findListBySql(builder.toString(), new Object[]{endpointId});
    }

    /**
     * 删除异常信息
     *
     * @param ids
     */
    public void deleteErrorInfo(String[] ids) {
        StringBuilder builder = new StringBuilder();
        List<String> params = new ArrayList<>();
        builder.append("delete from T_NCLK_EXAMROOM_LOG_HIS where EXAMROOM_LOG_HIS_ID in ( ");
        for (String id : ids) {
            builder.append("?,");
            params.add(id);
        }
        builder.setLength(builder.length() - 1);
        builder.append(") ");
        baseDao.updateBySql(builder.toString(), params.toArray());
    }

    /**
     * 删除7天前的实时日志
     */
    public void deleteOldLog() {
        baseDao.updateBySql("delete FROM T_NCLK_EXAMROOM_LOG where date(ERROR_DATE) <= DATE_SUB(CURDATE(), INTERVAL 7 DAY)");
    }

    /**
     * 删除7天前的历史日志
     */
    public void deleteOldHisLog() {
        baseDao.updateBySql("delete FROM T_NCLK_EXAMROOM_LOG_HIS where date(ERROR_DATE) <= DATE_SUB(CURDATE(), INTERVAL 7 DAY)");
    }
}
