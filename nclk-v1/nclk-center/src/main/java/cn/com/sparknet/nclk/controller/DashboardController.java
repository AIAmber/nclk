package cn.com.sparknet.nclk.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.sparknet.common.util.JsonUtil;
import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.nclk.service.DashboardService;

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
	 * test
	 * */
	@ResponseBody
	@RequestMapping("/getJsonErrorType.do")
	public Map<String, String> getJsonErrorType(String num, String type){
		Map<String, String> mapLineDataX=new HashMap<String, String>();
		mapLineDataX.put("lineDataX", "09:30");
		mapLineDataX.put("lineDataX", "09:31");
		mapLineDataX.put("lineDataX", "09:32");

		String lineDataX = JsonUtil.mapToJson(mapLineDataX);

		Map<String, Integer> lineDataY=new HashMap<String, Integer>();
		lineDataY.put("lineDataY", 10);
		lineDataY.put("lineDataY", 0);
		lineDataY.put("lineDataY", 0);


		return mapLineDataX;
	}

	/**
	 * test
	 * */
	@ResponseBody
	@RequestMapping("/jsonDataX.do")
	public String jsonDataX(String errorType){
		Map<String, String> mapLineDataX=new HashMap<String, String>();
		mapLineDataX.put("lineDataX", "09:30");
		mapLineDataX.put("lineDataX", "09:31");
		mapLineDataX.put("lineDataX", "09:32");

		String lineDataX = JsonUtil.mapToJson(mapLineDataX);
		return lineDataX;
	}

	/**
	 * test
	 * */
	@ResponseBody
	@RequestMapping("/jsonData.do")
	public String jsonData(String errorType){
		Map<String, Integer> mapLineDataX=new HashMap<String, Integer>();
		mapLineDataX.put("09:30", 10);
		mapLineDataX.put("09:31", 0);
		mapLineDataX.put("09:32", 0);

		String lineDataX = JsonUtil.mapToJson(mapLineDataX);

		return lineDataX;
	}

	/**
	* test
	* */
	@ResponseBody
	@RequestMapping("/jsonTest.do")
	public String jsonTest(HttpServletRequest request,HttpServletResponse response){
		// json test， 数据库直接传值
		//获取getJSON方法传递的参数callBack
		String errorType = StringUtil.nullToEmpty(request.getParameter("errorType"));
		//查询所有错误类型
		return dashboardService.jsonErrorType(errorType);
	}

	/**
	 * data of pie
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTestForEcharts.do")
	public String jsonTestForEcharts(HttpServletRequest request,HttpServletResponse response){
		//echarts - pie
		//获取getJSON方法传递的参数callBack
		String errorType = StringUtil.nullToEmpty(request.getParameter("errorType"));
		//查询所有错误类型
		return dashboardService.jsonErrorTypeForEcharts(errorType);
	}

	/**
	 * data of line
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonLine.do")
	public String jsonLine(HttpServletRequest request,HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String errorType = StringUtil.nullToEmpty(request.getParameter("errorType"));
		//查询所有错误类型
		return dashboardService.jsonLine(errorType);
	}

	/**
	 * data of total, citys
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeA")
	public String jsonTotalTypeA(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String errorNum = StringUtil.nullToEmpty(request.getParameter("errorNum"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeA(errorNum);
	}

	/**
	 * data of total, border
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeBname")
	public String jsonTotalTypeB(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String errorNum = StringUtil.nullToEmpty(request.getParameter("errorNum"));
		String areaPid = StringUtil.nullToEmpty(request.getParameter("areaPid"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeBName(errorNum, areaPid);
	}

	/**
	 * data of total, border
	 * @param
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
	 * data of total, border - father num, e.g. {"320502":1,"320302":1,"320105":2,"320402":1,"320202":1}
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeBFatherNum")
	public String jsonTotalTypeBFatherNum(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeBFatherNum(areaId);
	}

	/**
	 * data of total, border
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
		//查询所有错误类型
		return dashboardService.jsonTotalTypeCName(areaId);
	}

	/**
	 * data of total, border
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/jsonTotalTypeCNum")
	public String jsonTotalTypeCNum(HttpServletRequest request, HttpServletResponse response){
		//echarts - line
		//获取getJSON方法传递的参数callBack
		String areaId = StringUtil.nullToEmpty(request.getParameter("areaId"));
		//查询所有错误类型
		return dashboardService.jsonTotalTypeCNum(areaId);
	}

	/**
	 * timestamp, 时间戳， 将服务器时间返回给前端
	 * @param
	 * @return url with json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/stringTime")
	public String stringTime(HttpServletRequest request, HttpServletResponse response){
		// time
		Date nowDate = new Date(); // 获取当前时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); // 初始化时间格式化方法
		String dateText = dateFormat.format(nowDate); // 时间格式化至指定格式并转换成字符串
		// String dateJson = "{\"time\": \"" + dateText + "\"}"; // 将字符串转换为JSON格式
		// 将服务器时间返回给前端
		return dateText;
	}
}
