package cn.com.sparknet.nclk.controller;


import cn.com.sparknet.nclk.config.Config;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.service.DashboardService;
import cn.com.sparknet.nclk.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页echarts数据
 * @author lirj
 *
 */
@Controller
@RequestMapping("/DashboardController")
public class DashboardController {

	@Resource
	private DashboardService dashboardService;

    /**
     * 查询得到时钟的个数统计信息（采用monitor中的方法），
	 * 此外，首页echarts - line 省级数据加载也调用此方法
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/monitorCount")
    public EditJson monitorCount(HttpServletRequest request) {
        EditJson editJson = new EditJson();
        String userId = request.getSession().getAttribute("USER_ID").toString();
        editJson.setBean(dashboardService.getClockCount(userId));
        editJson.setSuccess(true);
        return editJson;
    }

	/**
	 * data of pie， 首页echarts - pie 省级数据加载
	 * @param request
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTestForEcharts")
	public EditJson jsonTestForEcharts(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - pie
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaPid = StringUtil.nullToEmpty(request.getParameter("areaPid"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		editJson.setBean(dashboardService.jsonErrorTypeForEcharts(userId, areaPid, startTime, endTime));
		editJson.setSuccess(true);
		//查询所有错误类型
		return editJson;
	}

	/**
	 * data of pie， 首页echarts - pie 市级数据加载
	 * @param request
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTestForEchartsB")
	public EditJson jsonTestForEchartsB(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - pie
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		editJson.setBean(dashboardService.jsonErrorTypeForEchartsB(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		//查询所有错误类型
		return editJson;
	}

	/**
	 * data of pie， 首页echarts - pie 县区级数据加载
	 * @param request
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTestForEchartsC")
	public EditJson jsonTestForEchartsC(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - pie
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		//查询所有错误类型
		editJson.setBean(dashboardService.jsonErrorTypeForEchartsC(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		return editJson;
	}

	/**
	 * data of total, citys， 首页echarts - total 省级数据加载（num - y轴）
	 * @param request
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeA")
	public String jsonTotalTypeA(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaPid = StringUtil.nullToEmpty(request.getParameter("areaPid"));
		String userId = request.getSession().getAttribute("USER_ID").toString();
		//查询所有错误类型
		return dashboardService.jsonTotalTypeA(areaPid, userId);
	}

	/**
	 * data of total, border， 首页echarts - total 市级数据加载（name - x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeBName")
	public String jsonTotalTypeB(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaPid = StringUtil.nullToEmpty(request.getParameter("areaPid"));
		String userId = request.getSession().getAttribute("USER_ID").toString();
		//查询所有错误类型
		return dashboardService.jsonTotalTypeBName(areaPid, userId);
	}

	/**
	 * data of total, border， 首页echarts - total 数据加载（获取当前区域的id 或 parentId）
	 * @param request
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeId")
	public String jsonTotalTypeId(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaPid = StringUtil.nullToEmpty(request.getParameter("areaPid"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeId(areaPid);
	}

	/**
	 * data of total, border - num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
     * 首页echarts - total 市级数据加载（num - y轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeBNum")
	public String jsonTotalTypeBNum(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String errorNum = StringUtil.nullToEmpty(request.getParameter("errorNum"));
		String areaPid = StringUtil.nullToEmpty(request.getParameter("areaPid"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeBNum(errorNum, areaPid);
	}

	/**
	 * data of total, border，首页echarts - total 市级数据加载（name - x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeCName")
	public String jsonTotalTypeCName(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String userId = request.getSession().getAttribute("USER_ID").toString();
		//查询所有错误类型
		return dashboardService.jsonTotalTypeCName(areaId, userId);
	}

	/**
	 * data of total, border，首页echarts - total 县区级数据加载（id） -> num - y轴
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeCId")
	public String jsonTotalTypeCId(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeCId(areaId);
	}

    /**
     * data of total, border，首页echarts - total 省级数据加载（name - x轴）
     * @param
     * @return url with json
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/jsonTotalTypeBErrname")
    public EditJson jsonTotalTypeBErrname(HttpServletRequest request, HttpServletResponse response) throws ParseException{
        //echarts - line
        //获取getJSON方法传递的参数callBack
        EditJson editJson = new EditJson();
        String userId = request.getSession().getAttribute("USER_ID").toString();
        String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
        String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
        String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
        //查询所有错误类型
        editJson.setBean(dashboardService.jsonTotalTypeBErrname(userId, areaId, startTime, endTime));
        editJson.setSuccess(true);
        return editJson;
    }

	/**
	 * data of total, border，首页echarts - total 省级数据加载（name - x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeBErrInfo")
	public EditJson jsonTotalTypeBErrInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - line
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		//查询所有错误类型
		editJson.setBean(dashboardService.jsonTotalTypeBErrInfo(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		return editJson;
	}

	/**
	 * data of total, border，首页echarts - total 市级数据加载（异常区域name，x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeCErrname")
	public EditJson jsonTotalTypeCErrname(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - line
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		//查询所有错误类型
		editJson.setBean(dashboardService.jsonTotalTypeCErrname(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		return editJson;
	}

	/**
	 * data of total, border，首页echarts - total 市级数据加载（异常区域name，x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeCErrInfo")
	public EditJson jsonTotalTypeCErrInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - line
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		//查询所有错误类型
		editJson.setBean(dashboardService.jsonTotalTypeCErrInfo(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		return editJson;
	}

	/**
	 * data of total, border，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeDErrname")
	public EditJson jsonTotalTypeDErrname(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - line
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		//查询所有错误类型
		editJson.setBean(dashboardService.jsonTotalTypeDErrname(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		return editJson;
	}

	/**
	 * data of total, border，首页echarts - total 区县级数据加载（异常区域name - x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeDErrInfo")
	public EditJson jsonTotalTypeDErrInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//echarts - line
		//获取getJSON方法传递的参数callBack
		EditJson editJson = new EditJson();
		String userId = request.getSession().getAttribute("USER_ID").toString();
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		String startTime = StringUtil.nullToEmpty(request.getParameter("startTime"));
		String endTime = StringUtil.nullToEmpty(request.getParameter("endTime"));
		//查询所有错误类型
		editJson.setBean(dashboardService.jsonTotalTypeDErrInfo(userId, areaId, startTime, endTime));
		editJson.setSuccess(true);
		return editJson;
	}

	/**
	 * timestamp, 时间戳， 将服务器时间返回给前端
     * 首页echarts - line 时间数据加载（timestamp - x轴）
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/stringTime")
	public String stringTime(HttpServletRequest request, HttpServletResponse response){
		// time
		Date nowDate = new Date(); // 获取当前时间
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // 初始化时间格式化方法
		String timeText = timeFormat.format(nowDate); // 时间格式化至指定格式并转换成字符串
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetimeText = datetimeFormat.format(nowDate);
        String dateJson = "{\"time\": \"" + timeText + "\", \"datetime\": \"" + datetimeText + "\"}"; // 将字符串转换为JSON格式
		// 将服务器时间返回给前端
		return dateJson;
	}

	/**
	 * 获取首页total - echarts 刷新时间。
	 * */
	@ResponseBody
	@RequestMapping("/getDashboardRTime")
	public EditJson getIntervalTime() {
		Map<String, Object> dashboardRuntimeConf = new HashMap<String, Object>();
		dashboardRuntimeConf.put("dashboardRTime", Integer.parseInt(Config.getInstance().getProperty("center.dashboard.dashboardRTime")));
		dashboardRuntimeConf.put("timestampRTime", Integer.parseInt(Config.getInstance().getProperty("center.dashboard.timestampRTime")));
		EditJson editJson = new EditJson();
		editJson.setSuccess(true);
		editJson.setBean(dashboardRuntimeConf);
		return editJson;
	}
}
