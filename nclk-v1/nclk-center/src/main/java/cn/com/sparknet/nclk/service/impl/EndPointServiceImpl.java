package cn.com.sparknet.nclk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sparknet.common.dao.Page;
import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.UUIDUtil;
import cn.com.sparknet.nclk.dao.EndPointDao;
import cn.com.sparknet.nclk.service.EndPointService;

/**
 * 考点管理
 * @author wuyl
 *
 */
@Service
public class EndPointServiceImpl implements EndPointService{
	
	@Resource
	private EndPointDao endPointDao;

	/**
	 * 根据区域信息查询考点信息
	 */
	@Override
	public String getEndPointByDistrictId(String callBack, String districtId) {
		List<Map<String, Object>> list = endPointDao.findEndPointByDistrictId(districtId);
		//拼接json数据
		StringBuffer sb = new StringBuffer(callBack+"([");
		for (int i = 0; i < list.size(); i++) {
			sb.append("{\"ENDPOINT_NAME\":\""+list.get(i).get("ENDPOINT_NAME")+"\",\"ENDPOINT_ID\":\""+list.get(i).get("ENDPOINT_ID")+"\"},");
		}
		sb.append("])");
		return sb.toString();
	}

	/**
	 *查询考点列表信息
	 */
	@Override
	public ListJson findEndPoints(String start, String limit,String searchText){
		ListJson listJson =new ListJson();
			Page page=endPointDao.findEndPoints(start, limit,searchText);
			listJson.setRows(page.getList());
        	listJson.setTotal(page.getCount());
		return listJson;
	}
	
	
	/**
	 *新增考点管理
	 */
	@Override
	public EditJson addEndPointInfo(String endPointNum, String endPointName, String endPointAreaId, String endPointAddr,
			String endPointPerson, String endPointTel, String endPointIPAddr, String endPointPort, String ord) {
		EditJson editJson = new EditJson();
		try {
			int ordId = Integer.parseInt(ord);
			String endPointId = UUIDUtil.getNextValue();
			endPointDao.addEndPointInfo(endPointId, endPointNum, endPointName, endPointAreaId, endPointAddr, endPointPerson, endPointTel, endPointIPAddr, endPointPort, ordId);
			editJson.setSuccess(true);
			editJson.setBean("添加考点管理成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("添加考点管理失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}

	

}
