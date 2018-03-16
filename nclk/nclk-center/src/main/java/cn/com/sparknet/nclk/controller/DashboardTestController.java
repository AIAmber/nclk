//package cn.com.sparknet.nclk.controller;
//
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//
//import cn.com.sparknet.common.util.JsonUtil;
//import cn.com.sparknet.nclk.service.DashboardService;
//
///**
// * 首页echarts数据
// * @author lirj
// *
// */
//public class DashboardTestController {
//	@Controller
//	@RequestMapping("/DashboardController")
//	public class DashboardController {
//		
//		@Resource
//		private DashboardService DashboardService;
//		
//		@ResponseBody
//		@RequestMapping(value="/getJsonErrorType.do")
//		public void getJsonErrorType(HttpServletRequest request, HttpServletResponse response) throws Exception {
////			response.setContentType("text/plain");
////			response.setCharacterEncoding("utf-8");        
//			//模拟数据库数据
//			Map<String, String> mapErrorTypeTest=new HashMap<String, String>();
//			mapErrorTypeTest.put("1", "网络延迟");
////			return mapErrorTypeTest;
//		}
//		
//		public Map<String, String> getJsonErrorType(String num, String type){
//			Map<String, String> mapErrorTypeTest=new HashMap<String, String>();
//			mapErrorTypeTest.put("1", "网络延迟");
//			
//			return mapErrorTypeTest;
//		}
//	}
//}
