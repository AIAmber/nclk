package cn.com.sparknet.nclk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.nclk.dao.AreaDao;
import cn.com.sparknet.nclk.service.AreaService;

/**
 * 区域管理
 * @author wuyl
 *
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaDao areaDao;
	
	/**
	 * 查询所有省信息信息
	 */
	@Override
	public String findAllProvince(String callBack) {
		List<Map<String, Object>> list = areaDao.findAllProvince();
		//拼接json数据
		StringBuffer sb = new StringBuffer(callBack+"([");
		for (int i = 0; i < list.size(); i++) {
			sb.append("{\"AREA_ID\":\""+list.get(i).get("AREA_ID")+"\",\"AREA_NAME\":\""+list.get(i).get("AREA_NAME")+"\"},");
		}
		sb.append("])");
		return sb.toString();
	}

	@Override
	@ResponseBody
	/**
	 * 查询所有地级市或区县信息信息
	 * @param callBack jquery getjson 回调函数
	 * @param parentAreaId 父级区域id
	 * @return
	 */
	public String findChildrenByParentAreaId(String callBack,String parentAreaId) {
		List<Map<String, Object>> list = areaDao.findChildrenByParentAreaId(parentAreaId);
		//拼接json数据
		StringBuffer sb = new StringBuffer(callBack+"([");
		for (int i = 0; i < list.size(); i++) {
			sb.append("{\"AREA_ID\":\""+list.get(i).get("AREA_ID")+"\",\"AREA_NAME\":\""+list.get(i).get("AREA_NAME")+"\"},");
		}
		sb.append("])");
		return sb.toString();
	}

	/**
	 *获取区域管理树的信息
	 */
	@Override
	public String findAllArea() {
		List<Map<String,Object>> list = areaDao.findAllAreaInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"rows\":[");
		for (int i = 0; i < list.size(); i++) {
			if("江苏省".equals(list.get(i).get("AREA_NAME"))) {
				sb.append("{\"id\":\""+list.get(i).get("AREA_ID")+"\" ,\"pId\":\""+list.get(i).get("PARENT_AREA_ID")+"\" ,\"name\":\""+list.get(i).get("AREA_NAME")+"\" ,\"open\":\"true\"},");
			}else if(i< list.size()-1){
				sb.append("{\"id\":\""+list.get(i).get("AREA_ID")+"\" ,\"pId\":\""+list.get(i).get("PARENT_AREA_ID")+"\" ,\"name\":\""+list.get(i).get("AREA_NAME")+"\"},");
			}else if(i== list.size()-1){
				sb.append("{\"id\":\""+list.get(i).get("AREA_ID")+"\" ,\"pId\":\""+list.get(i).get("PARENT_AREA_ID")+"\" ,\"name\":\""+list.get(i).get("AREA_NAME")+"\"}");
			}
		}
		sb.append("]}");
		return sb.toString();
	}

}
