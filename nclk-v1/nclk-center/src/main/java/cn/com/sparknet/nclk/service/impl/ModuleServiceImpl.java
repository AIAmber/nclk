package cn.com.sparknet.nclk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.Buffer;

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

	/**
	 *权限模块获取
	 */
	@Override
	public String getModuleInfo() {
		List<Map<String, Object>> list=moduleDao.getModuleInfo();
		StringBuffer buffer =new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("PARENT_MODULE_ID")=="") {
				buffer.append("{\"id\":\""+list.get(i).get("MODULE_ID")+"\" ,\"pId\":\""+list.get(i).get("MODULE_ID")+"\" ,\"name\":\""+list.get(i).get("MODULE_NAME")+"\",");
			}else {
				buffer.append("{\"id\":\""+list.get(i).get("MODULE_ID")+"\" ,\"pId\":\""+list.get(i).get("MODULE_ID")+"\" ,\"name\":\""+list.get(i).get("MODULE_NAME")+"\",");
			}
		}
		buffer.append("[");
		return buffer.toString();
	}
	

}
