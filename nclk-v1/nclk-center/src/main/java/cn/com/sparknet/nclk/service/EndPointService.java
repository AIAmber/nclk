package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.json.ListJson;

/**
 * 考点管理
 * @author wuyl
 *
 */
public interface EndPointService {
	
	/**
	 * 获取所有考点信息
	 * @param callBack
	 * @param districtId 区县id 
	 * @return
	 */
	public String getEndPointByDistrictId(String callBack,String districtId);
	
	
	/**
	 * 新增考点管理
	 * @param endPointNum
	 * @param endPointName
	 * @param endPointAreaId
	 * @param endPointAddr
	 * @param endPointPerson
	 * @param endPointTel
	 * @param endPointIPAddr
	 * @param endPointPort
	 * @param ord
	 * @return
	 */
	public EditJson addEndPointInfo(String endPointNum,String endPointName, String endPointAreaId,String endPointAddr,String  endPointPerson, String endPointTel,String endPointIPAddr,String endPointPort,String ord);

	/**
	 * 
	 * 查询考点列表信息
	 * @param start
	 * @param limit
	 * @param searchText
	 * @return
	 */
	public ListJson findEndPoints(String start, String limit,String searchText);
}
