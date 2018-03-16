package cn.com.sparknet.nclk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sparknet.nclk.dao.ModuleDao;
import cn.com.sparknet.nclk.service.ModuleService;

/**
 * 模块管理
 * @author wuyl
 *
 */
@Service
public class ModuleServiceImpl implements ModuleService{
	
	@Resource
	private ModuleDao moduleDao;

	/**
	 * 根据用户名查询用户权限集合
	 */
	@Override
	public List<Map<String, Object>> findModuleByUserName(String userName) {
		List<Map<String,Object>> list = null;
		try {
			list = moduleDao.findUserIdByName(userName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return list;
	}
	

}
