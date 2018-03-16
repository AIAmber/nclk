package cn.com.sparknet.nclk.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sparknet.nclk.dao.DashboardDao;
import cn.com.sparknet.nclk.service.DashboardService;
import cn.com.sparknet.common.util.JsonUtil;

/**
 * 首页echarts数据
 * @author lirj
 *
 */
public class DashboardServiceImpl implements DashboardService{
	
	@Resource
	private DashboardDao DashboardDao;
	
	@Override
	public String jsonErrorType(String errorType){
		String json = null;
		try {
			json = JsonUtil.mapToJson(DashboardDao.mapErrorType(errorType));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return json;
	}
	
}
