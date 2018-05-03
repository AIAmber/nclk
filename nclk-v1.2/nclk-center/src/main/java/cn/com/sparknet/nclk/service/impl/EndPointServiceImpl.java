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

import cn.com.sparknet.nclk.bean.FileInfo;
import cn.com.sparknet.nclk.dao.AreaDao;
import cn.com.sparknet.nclk.dao.EndPointDao;
import cn.com.sparknet.nclk.dao.Page;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.json.ListJson;
import cn.com.sparknet.nclk.service.EndPointService;
import cn.com.sparknet.nclk.service.FileService;
import cn.com.sparknet.nclk.util.JsonUtil;
import cn.com.sparknet.nclk.util.StringUtil;
import cn.com.sparknet.nclk.util.UUIDUtil;

/**
 * 考点管理
 * @author luogang
 *
 */
@Service
public class EndPointServiceImpl implements EndPointService{
	
	@Resource
	private FileService fileService;
	@Resource
	private EndPointDao endPointDao;
	@Resource
	private AreaDao areaDao;

	/**
	 * 根据区域信息查询考点信息
	 */
	@Override
	public String getEndPointByDistrictId(String districtId,HttpServletRequest request) {
		String userName = null;
		if(null != request.getSession().getAttribute("USERNAME")){
			userName = (String)request.getSession().getAttribute("USERNAME");
		}
		List<Map<String, Object>> list = endPointDao.findEndPointByDistrictId(districtId,userName);
		return JsonUtil.listToJson(list);
	}

	/**
	 *查询考点列表信息
	 */
	@Override
	public ListJson findEndPoints(String start, String limit,String searchText,String areaId,String state,String areaType){
		ListJson listJson =new ListJson();
		Page page=endPointDao.findEndPoints(start, limit,searchText,areaId,state,areaType);
		listJson.setRows(page.getList());
    	listJson.setTotal(page.getCount());
		return listJson;
	}
	
	/**
	 * 检查考点编号是否存在
	 */
	@Override
	public int findEndPointNo(String endPointNum){
		int count=0;
        try{
        	count=endPointDao.findEndPointNo(endPointNum);
        }catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }
        return count;
    }
	
	
	/**
	 *保存考点管理
	 */
	//需改WUHL
	@Override
	public EditJson saveEndPointInfo(String endPointId,String endPointNum, String endPointName, String endPointAreaId, String endPointAddr,
			String endPointPerson, String endPointTel, String endPointIPAddr, String endPointPort, String ord,String state) {
		EditJson editJson= new EditJson();
		if(""==endPointId && "".equals(endPointId)) {
			try {
				endPointId = UUIDUtil.getNextValue();
				endPointDao.addEndPointInfo(endPointId, endPointNum, endPointName, endPointAreaId, endPointAddr, endPointPerson, endPointTel, endPointIPAddr, endPointPort, ord,state);
				editJson.setSuccess(true);
				editJson.setBean("新增成功！");
			} catch (Exception e) {
				editJson.setSuccess(false);
				editJson.setBean("新增失败！");
				throw new RuntimeException(e.getMessage(),e);
			}
			return editJson;
			
		}else {
			try {
				endPointDao.updateEndPointInfo(endPointId,endPointNum, endPointName, endPointAreaId, endPointAddr, endPointPerson, endPointTel, endPointIPAddr, endPointPort, ord,state);
				editJson.setSuccess(true);
				editJson.setBean("修改成功！");
			} catch (Exception e) {
				editJson.setSuccess(false);
				editJson.setBean("修改失败！");
				throw new RuntimeException(e.getMessage(),e);
			}
			return editJson;
		}
	}

	/**
	 *根据考点ID获取考点信息
	 */
	@Override
	public Map<String, Object> getEndPointByEndPointId(String endPointId) {
		Map<String, Object> map=endPointDao.getEndPointByEndPointId(endPointId);
		return map;
	}

	/**
	 *根据区县ID获取考点列表信息
	 */
	@Override
	public List<Map<String,Object>> getEndPointInfoByDistrictId( String districtId) {
		
    	return endPointDao.getEndPointInfoByDistrictId(districtId);
	}

	/**
	 *根据考点Id删除考点信息
	 */
	@Override
	public EditJson deleteEndPointInfoByEndPointId(String endPointId) {
		EditJson editJson =new EditJson();
		try {
			String[] endPointIdList=endPointId.split(",");
			endPointDao.deleteEndPointInfoByEndPointId(endPointIdList);
			editJson.setSuccess(true);
			editJson.setBean("删除成功！");
		} catch (Exception e) {
			editJson.setSuccess(false);
			editJson.setBean("删除失败！");
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
        	String fileName="考点信息模板.xlsx";
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
        	editJson.setBean(StringUtil.getDateTime()+" 初始化考点信息失败："+e.getMessage());
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
		int rowNum = 5;//excel表格数据从第5行开始解析
		String ord = "";//顺序号
		String provinceName = "";//考点所在省
		String cityName = "";//考点所在市
		String districtName = "";//考点县区
		String endPointName = "";//考点服务器名称
		String endPointIP = "";//考点服务器IP
		String endPointPort = "";//考点服务器端口
		String endPointNo = "";//考点服务器编号
		String endPointAddr = "";//考点地址
		String endPointPerson = "";//考点负责人
		String endPointTel="";//考点负责人电话
		String state="";//状态
		String stateCode="X";//状态码
		String districtId = "";//区县id
		String districtIdTemp = "";//区县id
		String provinceId="";
		String cityId="";
		boolean rowIsNotEmpty=false;//此行是否有值

		try{
			for(int i = 0 ;i < rowList.size() ; i++){
				rowNum += i ;
				Map<String,String> rowMap = rowList.get(i);
				//判断若这一行所有应有数据为空时退出for循环
				for(String key:rowMap.keySet()){
					if(StringUtil.isNotEmpty(rowMap.get(key))){
						rowIsNotEmpty=true;
						break;
					}
				}
				if(!rowIsNotEmpty){
					continue;
				}else{
					rowIsNotEmpty=false;//重置rowIsNotEmpty的值供下次循环使用
				}
				ord = rowMap.get("A")==null?"":rowMap.get("A").trim();
				provinceName = rowMap.get("B")==null?"":rowMap.get("B").trim();
				cityName = rowMap.get("C")==null?"":rowMap.get("C").trim();
				districtName = rowMap.get("D")==null?"":rowMap.get("D").trim();
				endPointName = rowMap.get("E")==null?"":rowMap.get("E").trim();
				endPointIP = rowMap.get("F")==null?"":rowMap.get("F").trim();
				endPointPort = rowMap.get("G")==null?"":rowMap.get("G").trim();
				endPointNo = rowMap.get("H")==null?"":rowMap.get("H").trim();
				endPointAddr= rowMap.get("I")==null?"":rowMap.get("I").trim();
				endPointPerson=rowMap.get("J")==null?"":rowMap.get("J").trim();
				endPointTel=rowMap.get("K")==null?"":rowMap.get("K").trim();
				state=rowMap.get("L")==null?"":rowMap.get("L").trim();
				if(StringUtil.isNotEmpty(provinceName)&&StringUtil.isNotEmpty(cityName)
						&&StringUtil.isNotEmpty(districtName)&&StringUtil.isNotEmpty(endPointName)
						&&StringUtil.isNotEmpty(endPointIP)&&StringUtil.isNotEmpty(endPointPort)
						&&StringUtil.isNotEmpty(endPointNo)&&StringUtil.isNotEmpty(state)){
					//判断省相关信息是否正确
					if(null != areaDao.findAreaIdByAreaName(provinceName).get("AREA_ID")){
						//根据省名称查询省id
						provinceId = areaDao.findAreaIdByAreaName(provinceName).get("AREA_ID").toString();
					}else{
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的省相关信息不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//判断市相关信息是否正确
					if(null != areaDao.findAreaIdByAreaNameANDParentAreaId(cityName,provinceId).get("AREA_ID")){
						//根据市名称查询id
						cityId = areaDao.findAreaIdByAreaNameANDParentAreaId(cityName,provinceId).get("AREA_ID").toString();
						if(null != provinceId && null != areaDao.findParentAreaIdByAreaId(cityId)){
							String parentAreaId = (String)areaDao.findParentAreaIdByAreaId(cityId).get("PARENT_AREA_ID");
							if(!parentAreaId.equals(provinceId)){
								editJson.setSuccess(false);
								editJson.setBean("第"+rowNum+"行填写的市名称不属于所填写的省，请检查后重新上传！");
								//所有数据都正确时 数据才入库 否则回滚事务
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
								return editJson;
							}
						}
					}else{
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的市相关信息不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//判断区相关信息是否正确
					if(null != areaDao.findAreaIdByAreaNameANDParentAreaId(districtName,cityId).get("AREA_ID")){
						districtId = areaDao.findAreaIdByAreaNameANDParentAreaId(districtName,cityId).get("AREA_ID").toString();
						if(null != provinceId && null != areaDao.findParentAreaIdByAreaId(districtId)){
							String parentAreaId = (String)areaDao.findParentAreaIdByAreaId(districtId).get("PARENT_AREA_ID");
							if(!parentAreaId.equals(cityId)){
								editJson.setSuccess(false);
								editJson.setBean("第"+rowNum+"行填写的区县名称不属于所填写的市，请检查后重新上传！");
								//所有数据都正确时 数据才入库 否则回滚事务
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
								return editJson;
							}
						}
					}else{
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的区县名称不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//校验考点编号唯一
					int endPointNoCount=findEndPointNo(endPointNo);
					if(endPointNoCount>0){
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的考点编号已存在，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//校验考点状态
					if("有效".equals(state)){
						stateCode="A";
					}else if("无效".equals(state)){
						stateCode="X";
					}else{
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的考点状态不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//校验ip是否符合规范
					if(!isIP(endPointIP)){
						editJson.setSuccess(false);
						editJson.setBean("第"+rowNum+"行填写的考点服务器IP不正确，请检查后重新上传！");
						//所有数据都正确时 数据才入库 否则回滚事务
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
						return editJson;
					}
					//验证其他数据项字段长度
					Map<String,String[]> checkDatas=new HashMap<String,String[]>();
					checkDatas.put("endPointName", new String[]{"考点名称","25",endPointName});
					checkDatas.put("endPointPort", new String[]{"考点服务器端口","10",endPointPort});
					checkDatas.put("endPointAddr", new String[]{"考点地址","50",endPointAddr});
					checkDatas.put("endPointPerson", new String[]{"考点负责人","50",endPointPerson});
					checkDatas.put("endPointTel", new String[]{"考点负责人电话","50",endPointTel});
					checkDatas.put("endPointNo", new String[]{"考点编号","25",endPointTel});
					checkDatas.put("org", new String[]{"顺序号","11",ord});
					for(String key : checkDatas.keySet()){
						String[] values=checkDatas.get(key);
						if(isOverLength(values[2],values[1])){
							editJson.setSuccess(false);
							editJson.setBean("第"+rowNum+"行填写的考点服务器"+values[0]+"超出最大长度"+values[1]+"，请检查后重新上传！");
							//所有数据都正确时 数据才入库 否则回滚事务
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
							return editJson;
						};
					}
					//生成考点id
					String endPointId = UUIDUtil.getNextValue();
					endPointDao.addEndPointInfo(endPointId, endPointNo, endPointName, districtId, endPointAddr, endPointPerson, endPointTel, endPointIP, endPointPort, ord, stateCode);
					editJson.setSuccess(true);
					editJson.setBean("考点信息数据导入成功！");
				}else{
					editJson.setSuccess(false);
					editJson.setBean("第"+rowNum+"行必输项有值为空,请检查后重新上传！");
					//所有数据都正确时 数据才入库 否则回滚事务
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
					return editJson;	
				}
			}
			return editJson;
		}catch(Exception e){
			editJson.setSuccess(false);
        	throw new RuntimeException(e.getMessage(),e);
        }
	}
	
	
	/**
	 * 校验IP格式是否正确
	 */
    public boolean isIP(String addr)  
    {  
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))  
        {  
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
    
    /**
     * 校验字段是否超长
     * @return
     */
    public boolean isOverLength(String name,String length){
    	if(name.length()>Integer.parseInt(length)){
    		return true;
    	}else{
    		return false;
    	}
    }

	@Override
	public String identityAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String userId=request.getSession().getAttribute("USER_ID").toString();
		String moduleId=request.getParameter("moduleId");
		return endPointDao.identityAuthentication(userId,moduleId);

	}
    
}
