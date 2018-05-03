package cn.com.sparknet.nclk.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;

/**
 * 考点管理
 * @author luogang
 *
 */
public interface EndPointService {
	
	/**
	 * 获取所有考点信息
	 * @param districtId 区域id
	 * @param request 
	 * @return 返回考点信息
	 */
	public String getEndPointByDistrictId(String districtId,HttpServletRequest request);
	/**
	 * 检查考点编号是否存在
	 * @param endPointId
	 * @param endPointNum
	 * @return
	 */
	public int findEndPointNo(String endPointNum);
	
	/**
	 * 保存考点管理
	 * @param endPointId
	 * @param endPointNum
	 * @param endPointName
	 * @param endPointAreaId
	 * @param endPointAddr
	 * @param endPointPerson
	 * @param endPointTel
	 * @param endPointIPAddr
	 * @param endPointPort
	 * @param ord
	 * @param state
	 * @return
	 */
	public EditJson saveEndPointInfo(String endPointId ,String endPointNum,String endPointName, String endPointAreaId,String endPointAddr,String  endPointPerson, String endPointTel,String endPointIPAddr,String endPointPort,String ord,String state);

	/**
	 * 查询考点列表信息
	 * @param start
	 * @param limit
	 * @param searchText
	 * @param areaId
	 * @param sort
	 * @param order
	 * @param state
	 * @param areaType
	 * @return
	 */
	public ListJson findEndPoints(String start, String limit,String searchText,String areaId,String state,String areaType);

	/**
	 * 根据考点ID获取考点信息
	 * @param endPointId
	 * @return
	 */
	public Map<String, Object> getEndPointByEndPointId(String endPointId);

	/**
	 * 根据区县ID获取考点列表信息
	 * @param districtId
	 * @return
	 */
	public List<Map<String,Object>>  getEndPointInfoByDistrictId( String districtId);

	/**
	 * 根据考点Id删除考点信息
	 * @param endPointId
	 * @return
	 */
	public EditJson deleteEndPointInfoByEndPointId(String endPointId);


	/**
	 * 上传考点信息模板
	 * @param request
	 * @param response
	 * @return
	 */
	public void submitUpload(HttpServletRequest request, HttpServletResponse response);


	/**
	 * 下载考点信息模板
	 * @param response
	 */
	public void getDownloadTemplet(HttpServletResponse response);
	
	public String identityAuthentication(HttpServletRequest request,HttpServletResponse response);

}
