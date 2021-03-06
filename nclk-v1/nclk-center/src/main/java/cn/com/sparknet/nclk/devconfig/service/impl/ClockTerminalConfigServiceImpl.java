package cn.com.sparknet.nclk.devconfig.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;

import cn.com.sparknet.common.json.EditJson;
import cn.com.sparknet.common.util.HttpClientUtils;
import cn.com.sparknet.common.util.JsonUtil;
import cn.com.sparknet.common.util.StringUtil;
import cn.com.sparknet.common.util.UUIDUtil;
import cn.com.sparknet.nclk.bean.FileInfo;
import cn.com.sparknet.nclk.dao.AreaDao;
import cn.com.sparknet.nclk.dao.EndPointDao;
import cn.com.sparknet.nclk.devconfig.dao.ClockTerminalConfigDao;
import cn.com.sparknet.nclk.devconfig.service.ClockTerminalConfigService;
import cn.com.sparknet.nclk.service.FileService;
/**
 * 网络时钟终端配置
 * @author wuyl
 *
 */
@Service
public class ClockTerminalConfigServiceImpl implements ClockTerminalConfigService {

	@Resource
	private FileService fileService;
	@Resource
	private ClockTerminalConfigDao clockTerminalConfigDao;
	@Resource
	private AreaDao areaDao;
	@Resource
	private EndPointDao endPointDao;
	
	/**
	 * 手动添加考场子钟信息
	 */
	@Override
	public EditJson addExamRoomInfo(String examRoomIp, String examRoomName, String examRoomAddr, String examRoomPerson,
			String examRoomTel, String endpointId,String ord) {
		EditJson editJson = new EditJson();
		try {
			int ordId = Integer.parseInt(ord);
			String examRoomId = UUIDUtil.getNextValue();
			clockTerminalConfigDao.addExamRoomInfo(examRoomId, examRoomIp, examRoomName, examRoomAddr, examRoomPerson, examRoomTel, endpointId, ordId);
			editJson.setSuccess(true);
			editJson.setBean("添加考场子钟信息成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("添加考场子钟信息失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}
	
	/**
	 * 下载模板
	 */
	@Override
	public void getDownloadTemplet(HttpServletResponse response) {
		try{
        	String directory="template/";
        	String fileName="考场子钟信息模板.xlsx";
        	InputStream is = ClockTerminalConfigServiceImpl.class.getClassLoader().getResourceAsStream(directory+fileName);
        	fileService.downloadFile(is, fileName, response);
        }catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
	}
	
	/**
	 * 上传模板
	 */
	@Override
	public EditJson submitUpload(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		EditJson editJson=new EditJson();
        try{
        	response.setCharacterEncoding("UTF-8");
        	response.setContentType("text/html;charset=UTF-8");
        	/*out=response.getWriter();*/
        	//上传Excel文件
        	List<FileInfo> fileInfos=fileService.uploadFile(request);
        	for(FileInfo fileInfo:fileInfos){
        		//解析Excel文件
        		editJson = this.resolveExcel(fileInfo);
        	}
        	return editJson;
// 			out.print(editJson.toJsonString());
        }catch(Exception e){
        	editJson.setBean(StringUtil.getDateTime()+" 初始化考场子钟信息失败："+e.getMessage());
			editJson.setSuccess(false);
			out.print(editJson.toJsonString());
        	throw new RuntimeException(e.getMessage(),e);
        }finally{
        	if(null!=out){
        		out.flush();
        		out.close();
        		out=null;
        	}
        }
    }

	/**
	 * 解析Excel文件
	 */
	private EditJson resolveExcel(FileInfo fileInfo){
		try{
			File file=new File(fileInfo.getFilePath());
			InputStream is=new FileInputStream(file);
			Workbook workbook=new XSSFWorkbook(is);
			//获取第一个sheet
			Sheet sheet=workbook.getSheetAt(0);
			int lastRowNum=sheet.getLastRowNum();
			//从第五行开始取数据
			List<Map<String,String>> rowList=new ArrayList<Map<String,String>>();
			for(int r=4;r<=lastRowNum;r++){
				Row row=sheet.getRow(r);
				//获取当前行的列
				Map cellMap=new LinkedMap();
				short lastCellNum=row.getLastCellNum();
				for(short c=0;c<lastCellNum;c++){
					Cell cell=row.getCell(c);
					cell.setCellType(1);//将所有列都设置为字符类型
					cellMap.put(CellReference.convertNumToColString(c), cell.getStringCellValue());
				}
				rowList.add(cellMap);
			}
			//保存数据
			EditJson editJson = this.saveData(rowList);
			return editJson;
		}catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
	}
	
	/**
	 * 保存数据
	 */
	private EditJson saveData(List<Map<String,String>> rowList){
		EditJson editJson = new EditJson();
		String provinceName = "";
		String cityName = "";
		String ord = "";
		String districtName = "";
		String endPointName = "";
		String examRoomIP = "";
		String examRoomName = "";
		String examRoomAddr = "";
		String examRoomPerson = "";
		String examRoomTel = "";
		String provinceId = "";
		String cityId = "";
		String districtId = "";
		String endPointId = "";
		int orderId = 0;
		try{
			for(Map<String,String> rowMap:rowList){
				List<String> keys=StringUtil.getMapKey(rowMap);
				for(String key:keys){
					if(key.equals("A")){//列名-顺序号
						ord = rowMap.get(key).toString();
					}else if(key.equals("B")){//列名-省
						provinceName = rowMap.get(key).toString();
					}else if(key.equals("C")){//列名-市
						cityName = rowMap.get(key).toString();
					}else if(key.equals("D")){//列名-区
						districtName = rowMap.get(key).toString();
					}else if(key.equals("E")){//列名-考点名称
						endPointName = rowMap.get(key).toString();
					}else if(key.equals("F")){//列名-考点子钟IP
						examRoomIP = rowMap.get(key).toString();
					}else if(key.equals("G")){//列名-考场名称
						examRoomName = rowMap.get(key).toString();
					}else if(key.equals("H")){//列名-考场地址
						examRoomAddr = rowMap.get(key).toString();
					}else if(key.equals("I")){//列名-考场负责人
						examRoomPerson = rowMap.get(key).toString();
					}else if(key.equals("I")){//列名-考场负责人电话
						examRoomTel = rowMap.get(key).toString();
					}
				}
				if(StringUtil.isNotEmpty(provinceName)&&StringUtil.isNotEmpty(cityName)
						&&StringUtil.isNotEmpty(districtName)&&StringUtil.isNotEmpty(endPointName)
						&&StringUtil.isNotEmpty(examRoomIP)){
					//判断省相关信息是否正确
					if(null != areaDao.findAreaIdByAreaName(provinceName).get("AREA_ID")){
						//根据省名称查询省id
						provinceId = areaDao.findAreaIdByAreaName(provinceName).get("AREA_ID").toString();
					}else{
						editJson.setSuccess(false);
						editJson.setBean("填写的省相关信息不正确，请检查后重新上传！");
						return editJson;
					}
					//判断市相关信息是否正确
					if(null != areaDao.findAreaIdByAreaName(cityName).get("AREA_ID")){
						//根据市名称查询id
						cityId = areaDao.findAreaIdByAreaName(cityName).get("AREA_ID").toString();
						if(null != provinceId && null != areaDao.findParentAreaIdByAreaId(cityId)){
							String parentAreaId = (String)areaDao.findParentAreaIdByAreaId(cityId).get("PARENT_AREA_ID");
							if(!parentAreaId.equals(provinceId)){
								editJson.setSuccess(false);
								editJson.setBean("填写的市名称不属于所填写的省，请检查后重新上传！");
								return editJson;
							}
						}
					}else{
						editJson.setSuccess(false);
						editJson.setBean("填写的市相关信息不正确，请检查后重新上传！");
						return editJson;
					}
					//判断区相关信息是否正确
					if(null != areaDao.findAreaIdByAreaName(districtName).get("AREA_ID")){
						districtId = areaDao.findAreaIdByAreaName(districtName).get("AREA_ID").toString();
						if(null != provinceId && null != areaDao.findParentAreaIdByAreaId(districtId)){
							String parentAreaId = (String)areaDao.findParentAreaIdByAreaId(districtId).get("PARENT_AREA_ID");
							if(!parentAreaId.equals(cityId)){
								editJson.setSuccess(false);
								editJson.setBean("填写的区县名称不属于所填写的市，请检查后重新上传！");
								return editJson;
							}
						}
					}else{
						editJson.setSuccess(false);
						editJson.setBean("填写的区县名称不正确，请检查后重新上传！");
						return editJson;
					}
					
					if(null != endPointDao.findEndPointIdByName(endPointName).get("ENDPOINT_ID")){
						endPointId = endPointDao.findEndPointIdByName(endPointName).get("ENDPOINT_ID").toString();
						if(null != provinceId && null != endPointDao.findAreaIdByEndPointId(endPointId)){
							String areaId = (String)endPointDao.findAreaIdByEndPointId(endPointId).get("AREA_ID");
							if(!areaId.equals(districtId)){
								editJson.setSuccess(false);
								editJson.setBean("填写的考点不属于相关区县，请检查后重新上传！");
								return editJson;
							}
						}
					}else{
						editJson.setSuccess(false);
						editJson.setBean("填写的考点名称不正确，请检查后重新上传！");
						return editJson;
					}
					if(!StringUtil.isEmpty(ord)){
						orderId = Integer.parseInt(ord);
					}
					String examRoomId = UUIDUtil.getNextValue();
					clockTerminalConfigDao.addExamRoomInfo(examRoomId, examRoomIP, examRoomName, examRoomAddr, examRoomPerson, examRoomTel, endPointId, orderId);
					editJson.setSuccess(true);
					editJson.setBean("考场信息初始化成功！");
				}
			}
			return editJson;
		}catch(Exception e){
			editJson.setSuccess(false);
        	throw new RuntimeException(e.getMessage(),e);
        }
	}

	/**
	 * 自动检索考场子钟信息
	 */
	@Override
	public String autoSearchExamRoomInfo(String endPointId) {
		String result = null;
		String endPointIP = null;
		String endPointPort = null;
		try {
			Map<String,Object> map = endPointDao.findEndPointIPAndPortByEndPointId(endPointId);
			if(null != map){
				//考点ip
				endPointIP = map.get("ENDPOINT_IP").toString().trim();
				//考点端口
				endPointPort = map.get("ENDPOINT_PORT").toString().trim();
				result = HttpClientUtils.get("http://"+endPointIP+":"+endPointPort+"/nclk-endpoint/EndPointController/scanDevice");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 获取考场子钟状态
	 */
	@Override
	public Map<String, Object> findEndPointClockState(String endPointId) {
		//停用状态子钟信息
		List<Map<String,Object>> disableClock = clockTerminalConfigDao.findExamRoomClock(endPointId,"disable");
		//监控状态子钟信息
		List<Map<String,Object>> normalClock = clockTerminalConfigDao.findExamRoomClock(endPointId,"normal");
		//监控状态子钟数量
		Map<String,Object> disableCount = clockTerminalConfigDao.findExamRoomClockCount(endPointId,"disable");
		//监控状态子钟数量
		Map<String,Object> normalCount = clockTerminalConfigDao.findExamRoomClockCount(endPointId,"normal");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disableClock", disableClock);
		map.put("normalClock", normalClock);
		map.put("disableCount", disableCount);
		map.put("normalCount", normalCount);
		return map;
	}

	/**
	 * 根据考点id和考场子钟ip获取子钟其他信息
	 */
	@Override
	public Map<String,Object> findExamRoomInfo(String endPointId, String examRoomIP) {
		Map<String,Object> map = clockTerminalConfigDao.findExamRoomClockInfo(endPointId,examRoomIP);
		return map;
	}

}
