package cn.com.sparknet.nclk.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;

import cn.com.sparknet.nclk.bean.FileInfo;
import cn.com.sparknet.nclk.dao.AreaDao;
import cn.com.sparknet.nclk.dao.ClockTerminalConfigDao;
import cn.com.sparknet.nclk.dao.EndPointDao;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.service.ClockTerminalConfigService;
import cn.com.sparknet.nclk.service.FileService;
import cn.com.sparknet.nclk.util.HttpClientUtils;
import cn.com.sparknet.nclk.util.JsonUtil;
import cn.com.sparknet.nclk.util.StringUtil;
import cn.com.sparknet.nclk.util.UUIDUtil;
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
	public EditJson addExamRoomInfo(String examRoomIP, String examRoomName, String examRoomAddr, String examRoomPerson,
			String examRoomTel, String endpointId) {
		EditJson editJson = new EditJson();
		try {
			Map<String,Object> map = clockTerminalConfigDao.findExamRoomIPEXISTS(endpointId, examRoomIP);
			if(map.size() > 0){
				editJson.setBean("考点已存在IP:"+map.get("EXAMROOM_IP")+"请不要重复添加！");
				editJson.setSuccess(false);
			}else{
				String examRoomId = UUIDUtil.getNextValue();
				clockTerminalConfigDao.addExamRoomInfo(examRoomId, examRoomIP, examRoomName, examRoomAddr, examRoomPerson, examRoomTel, endpointId);
				editJson.setBean("添加考场子钟信息成功！");
				editJson.setSuccess(true);
			}
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
	public void submitUpload(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		EditJson editJson=new EditJson();
        try{
        	response.setCharacterEncoding("UTF-8");
        	response.setContentType("text/html;charset=UTF-8");
        	out=response.getWriter();
        	//上传Excel文件
        	List<FileInfo> fileInfos=fileService.uploadFile(request);
        	for(FileInfo fileInfo:fileInfos){
        		//解析Excel文件
        		editJson = this.resolveExcel(fileInfo);
        	}
 			out.print(editJson.toJsonString());
        }catch(Exception e){
        	editJson.setBean(StringUtil.getDateTime()+" 初始化考场子钟信息失败!");
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
					//将所有列都设置为字符类型
					cell.setCellType(1);
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
		int rowNum = 0;
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
		String districtId = "";
		String endPointId = "";
		try{
			for(int i = 0 ;i < rowList.size() ; i++){
				//excel表格数据从第5行开始解析
				rowNum = i + 5; 
				Map<String,String> rowMap = rowList.get(i);
				List<String> keys=StringUtil.getMapKey(rowMap);
				for(String key:keys){
					if("A".equals(key)){
						//列名-顺序号
						ord = rowMap.get(key).toString().trim();
					}else if("B".equals(key)){
						//列名-省
						provinceName = rowMap.get(key).toString().trim();
					}else if("C".equals(key)){
						//列名-市
						cityName = rowMap.get(key).toString().trim();
					}else if("D".equals(key)){
						//列名-区
						districtName = rowMap.get(key).toString().trim();
					}else if("E".equals(key)){
						//列名-考点名称
						endPointName = rowMap.get(key).toString().trim();
					}else if("F".equals(key)){
						//列名-考点子钟IP
						examRoomIP = rowMap.get(key).toString().trim();
					}else if("G".equals(key)){
						//列名-考场名称
						examRoomName = rowMap.get(key).toString().trim();
					}else if("H".equals(key)){
						//列名-考场地址
						examRoomAddr = rowMap.get(key).toString().trim();
					}else if("I".equals(key)){
						//列名-考场负责人
						examRoomPerson = rowMap.get(key).toString().trim();
					}else if("I".equals(key)){
						//列名-考场负责人电话
						examRoomTel = rowMap.get(key).toString().trim();
					}
				}
				if(StringUtil.isNotEmpty(provinceName)&&StringUtil.isNotEmpty(cityName)
						&&StringUtil.isNotEmpty(districtName)&&StringUtil.isNotEmpty(endPointName)
						&&StringUtil.isNotEmpty(examRoomIP)){
					//判断省，市，区县相关信息是否正确
					List<Map<String,Object>> areaIdsList = areaDao.findAreaIdAndCheck(provinceName, cityName, districtName);
					if(null != areaIdsList && areaIdsList.size() == 3 ){
						districtId = areaIdsList.get(2).get("AREA_ID").toString();
					}else{
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的省、市、县区不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//校验考点是否属于相关区县
					if(null != endPointDao.findEndPointIdByNameANDAreaId(endPointName,districtId).get("ENDPOINT_ID")){
						endPointId = endPointDao.findEndPointIdByNameANDAreaId(endPointName,districtId).get("ENDPOINT_ID").toString();
						if(null != provinceId && null != endPointDao.findAreaIdByEndPointId(endPointId)){
							String areaId = (String)endPointDao.findAreaIdByEndPointId(endPointId).get("AREA_ID");
							if(!areaId.equals(districtId)){
								editJson.setSuccess(false);
								editJson.setBean("第"+rowNum+"行填写的考点不属于相关区县，请检查后重新上传！");
								//所有数据都正确时 数据才入库 否则回滚事务
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
								return editJson;
							}
						}
					}else{
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的考点名称不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//校验IP地址格式是否正确
					if(!isIP(examRoomIP)){
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的考场子钟IP不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//校验考点下面考场IP地址唯一
					Map<String,Object> map = clockTerminalConfigDao.findExamRoomIPEXISTS(endPointId, examRoomIP);
					if(map.size() > 0){
						editJson.setBean("第"+rowNum+"行考点已存在IP:"+map.get("EXAMROOM_IP")+"请不要重复添加！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						editJson.setSuccess(false);
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					String examRoomId = UUIDUtil.getNextValue();
					clockTerminalConfigDao.addExamRoomInfo(examRoomId, examRoomIP, examRoomName, examRoomAddr, examRoomPerson, examRoomTel, endPointId);
					editJson.setSuccess(true);
					editJson.setBean("考场信息初始化成功！请选择所属的省、市、县区、考点后查看导入的考场子钟信息！");
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
	public String findAutoSearchClock(String endPointId) {
		String result = null;
		String endPointIP = null;
		String endPointPort = null;
		StringBuffer sb = new StringBuffer();
		try {
			//根据考点Id查询考点IP和端口信息
			Map<String,Object> map = endPointDao.findEndPointIPAndPortByEndPointId(endPointId);
			//根据考点Id查询库中已经有的数据
			List<Map<String,Object>> list = clockTerminalConfigDao.findExamRoomIPByEndPointId(endPointId);
			JSONArray jsonArray = new JSONArray();
			if(null != map && map.size() > 0){
				//考点ip
				endPointIP = map.get("ENDPOINT_IP").toString().trim();
				//考点端口
				endPointPort = map.get("ENDPOINT_PORT").toString().trim();
				result = HttpClientUtils.get("http://"+endPointIP+":"+endPointPort+"/nclk-endpoint/EndPointController/scanDevice");
				Map<String,Object> json = (Map<String, Object>) JsonUtil.jsonToMap(result);
				//{"dateFormat":"yyyy-MM-dd","rows":[{"ip":"10.66.1.54","mac":"D8:B0:4C:DD:47:86"},{"ip":"10.66.1.54","mac":"D8:B0:4C:DD:47:86"}],"total":2}
				Object obj = json.get("rows");
				if(null != map){
					 JSONArray array = (JSONArray) obj;
			         for (int i = 0; i < array.size(); i++) {
			        	 String ip = array.getJSONObject(i).getString("ip");
			        	 boolean flag = true;
			        	 for (int j = 0; j < list.size(); j++) {
			        		 if(null != ip && null != list.get(j) && ip.equals(list.get(j).get("EXAMROOM_IP"))){
			        			 //搜索出来的IP地址在数据库中存在，标志位设置为false
			        			 flag = false;
			        			 break;
				        	 }
			        	 }
			        	 //搜索出来的子钟IP地址在数据库中不存在，则添加
			        	 if(flag){
			        		 jsonArray.add(array.getJSONObject(i));
			        	 }
			         }
				}
			}
			sb.append("{\"dateFormat\":\"yyyy-MM-dd\",\"rows\":");
			sb.append(jsonArray.toJSONString());
			sb.append(",\"total\":"+jsonArray.size()+"}");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return sb.toString();
	}

	/**
	 * 获取考场子钟状态
	 */
	@Override
	public String findEndPointClockState(String endPointId) {
		try {
			//已停用的子钟信息查询
			List<Map<String,Object>> disableClock = clockTerminalConfigDao.findExamRoomClock(endPointId,"disable");
			//已启用的子钟信息查询
			List<Map<String,Object>> normalClock = clockTerminalConfigDao.findExamRoomClockNotDisable(endPointId);
			//查询所有停用的子钟的数量
			Map<String,Object> disableCount = clockTerminalConfigDao.findExamRoomClockCount(endPointId,"disable");
			//查询所有启用的子钟的数量
			Map<String,Object> normalCount = clockTerminalConfigDao.findExamRoomClockNotDisableCount(endPointId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("disableClock", disableClock);
			map.put("normalClock", normalClock);
			map.put("disableCount", disableCount);
			map.put("normalCount", normalCount);
			return JsonUtil.mapToJson(map);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 根据考点id和考场子钟ip获取子钟其他信息
	 */
	@Override
	public Map<String,Object> findExamRoomInfo(String endPointId, String examRoomIP) {
		try {
			Map<String,Object> map = clockTerminalConfigDao.findExamRoomClockInfo(endPointId,examRoomIP);
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 根据考点ip和子钟ip修改子钟信息
	 */
	@Override
	public EditJson updateExamRoomInfo(String endPointId, String examRoomIP, String examRoomName, String examRoomAddr,
			String examRoomPerson, String examRoomTel) {
		EditJson editJson = new EditJson();
		try {
			clockTerminalConfigDao.updateExamRoomInfo(endPointId, examRoomIP, examRoomName, examRoomAddr, examRoomPerson, examRoomTel);
			editJson.setSuccess(true);
			editJson.setBean("编辑子钟信息成功！");
		} catch (Exception e) {
			editJson.setSuccess(true);
			editJson.setBean("编辑子钟信息失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}

	/**
	 * 根据考点信息，考场子钟ip和考点信息  状态 启用或者禁用子钟
	 */
	@Override
	public EditJson updateExamRoomNormalOrDisable(String endPointId, String examRoomIP, String state) {
		EditJson editJson = new EditJson();
		try {
			clockTerminalConfigDao.updateExamRoomNormalOrDisable(endPointId, examRoomIP, state);
			editJson.setSuccess(true);
			editJson.setBean("修改子钟状态成功！");
		} catch (Exception e) {
			editJson.setSuccess(true);
			editJson.setBean("修改子钟状态失败！");
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}

	/**
	 * 查询考场中的ip是否已经存在
	 */
	@Override
	public EditJson findExamRoomIPEXISTS(String endPointId, String examRoomIP) {
		EditJson editJson = new EditJson();
		try {
			Map<String,Object> map = clockTerminalConfigDao.findExamRoomIPEXISTS(endPointId, examRoomIP);
			if(map.size() > 0){
				editJson.setBean("考点已存在IP:"+map.get("EXAMROOM_IP")+"请不要重复添加！");
				editJson.setSuccess(false);
			}else{
				editJson.setBean("考点不存在IP！");
				editJson.setSuccess(true);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return editJson;
	}

	/**
	 * 将自动扫描到的考场子钟ip入库
	 */
	@Override
	public EditJson addAutoSearchExamRoomInfo(String examRoomIp, String endpointId) {
		EditJson editJson = new EditJson();
		try {
			String examRoomId = UUIDUtil.getNextValue();
			clockTerminalConfigDao.addAutoSearchExamRoomInfo(examRoomId,examRoomIp,endpointId);
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
	 * 根据考点id查询考场子钟IP
	 */
	@Override
	public List<Map<String,Object>> findExamRoomIPByEndPointId(String endPointId){
		try {
			return clockTerminalConfigDao.findExamRoomIPByEndPointId(endPointId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 删除考场子钟IP配置
	 */
	@Override
	public EditJson deleteClock(String endPointId, String ipStr) {
		EditJson editJson = new EditJson();
		try {
			String [] ip = ipStr.split(",");
			for (int i = 0; i < ip.length; i++) {
				Map<String,Object> map = clockTerminalConfigDao.findExamRoomClockInfo(endPointId, ip[i]);
				if(null != map.get("EXAMROOM_ID")){
					String examroomId = map.get("EXAMROOM_ID").toString();
					clockTerminalConfigDao.deleteExamRoomLog(examroomId);
					clockTerminalConfigDao.deleteExamRoomLogHis(examroomId);
					clockTerminalConfigDao.deleteExamRoom(examroomId);
					editJson.setBean("删除子钟信息成功！");
					editJson.setSuccess(true);
				}else{
					editJson.setBean("删除子钟信息失败！");
					editJson.setSuccess(false);
				}
			}
		} catch (Exception e) {
			editJson.setBean("删除子钟信息失败！");
			editJson.setSuccess(false);
			throw new RuntimeException(e.getMessage(), e);
		}
		return editJson;
	} 

	/**
	 * 校验IP格式是否正确
	 */
    private boolean isIP(String addr){   
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr)) { 
            return false;  
        } 
        /** 
         * 判断IP格式和范围 
         */  
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";  
        Pattern pat = Pattern.compile(rexp);    
        Matcher mat = pat.matcher(addr);    
        boolean ipAddress = mat.find();  
        return ipAddress;  
    } 
    
}
