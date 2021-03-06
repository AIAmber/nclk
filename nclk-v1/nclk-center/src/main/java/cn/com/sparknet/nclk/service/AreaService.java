package cn.com.sparknet.nclk.service;

/**
 * 区域管理
 * @author wuyl
 *
 */
public interface AreaService {

	/**
	 * 查询所有省信息
	 * @return
	 */
	public String findAllProvince(String callBack);
	/**
	 * 查询所有地级市或者区县信息
	 * @return
	 */
	public String findChildrenByParentAreaId(String callBack,String parentAreaId);
	
	/**
	 * 获取区域管理树的信息
	 * @return
	 */
	public String findAllArea();
}
