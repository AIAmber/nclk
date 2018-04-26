package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import cn.com.sparknet.nclk.json.ListJson;

/**
 * 左侧菜单权限
 * @author wuyl
 */
public interface ModuleService {
	
	/**
	 * 查询用户所具有的权限信息
	 * @param userName
	 * @return
	 */
	public List<Map<String, Object>> findModuleByUserName(String userName);
	/**
	 * 权限模块获取
	 * @return
	 */
	public ListJson getModuleInfo();
	/**
	 * 查询用户所具有的区域权限
	 * @return
	 */
	public List<Map<String, Object>> getAreaByUserId(String userId);
}
