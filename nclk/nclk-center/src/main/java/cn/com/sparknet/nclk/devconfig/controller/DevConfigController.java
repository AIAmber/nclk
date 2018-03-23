package cn.com.sparknet.nclk.devconfig.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.common.json.ListJson;

/**
 * 设备配置
 * @author wuyl
 *
 */
@Controller
@RequestMapping("/DevConfigController")
public class DevConfigController {

	static List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	static{
		for(int i=1;i<=100;i++){
			if(i % 2 == 0){
				Map<String,Object> map = new HashMap<String,Object>();
				 map.put("id", i+"A");
		   		 map.put("machineId", "1111");
		   		 map.put("timeSource", "GPS");
		   		 map.put("time", "22:33:44");
		   		 map.put("clockip", "127.0.0.1");
		   		 map.put("machineStatus", "正常");
		   		 map.put("testCenter", "金陵中学");
		   		 map.put("isEnable", true);
		   		list.add(map);
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				 map.put("id", i+"B");
		   		 map.put("machineId", "2222");
		   		 map.put("timeSource", "北斗");
		   		 map.put("time", "22:33:44");
		   		 map.put("clockip", "127.0.0.1");
		   		 map.put("machineStatus", "异常");
		   		 map.put("testCenter", "南京二十九中学");
		   		 map.put("isEnable", false);
		   		list.add(map);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/findTimeServer")
	public ListJson findTimeServer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ListJson listJson=new ListJson();
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
		Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber").toString());
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		try{
			if(pageSize!=0){
				int crrentMax = pageNumber*pageSize>list.size()?list.size():pageNumber*pageSize;
				for(int i=((pageNumber-1)*pageSize);i<crrentMax ;i++){
					result.add(list.get(i));
	        	}
			}else{
        		result.addAll(list);
			}
        	listJson.setRows(result);
        	listJson.setTotal(list.size());
		}catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
		return listJson;
	}
	
}
