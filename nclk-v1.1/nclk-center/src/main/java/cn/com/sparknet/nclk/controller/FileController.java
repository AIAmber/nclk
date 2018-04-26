package cn.com.sparknet.nclk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sparknet.nclk.bean.FileInfo;
import cn.com.sparknet.nclk.json.EditJson;
import cn.com.sparknet.nclk.service.FileService;

/**
 * 文件操作
 * @author chenxy
 *
 */
@Controller
@RequestMapping("/FileController")
public class FileController {
	
	@Resource
	private FileService fileService;
	
	/**
	 * 上传文件
	 */
	@RequestMapping("/uploadFile")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out=null;
		EditJson editJson=new EditJson();
		List<FileInfo> fileInfos=new ArrayList<FileInfo>();
		try{
			response.setCharacterEncoding("UTF-8");
        	response.setContentType("text/html;charset=UTF-8");
        	out=response.getWriter();
        	fileInfos=fileService.uploadFile(request);
        	editJson.setBean(fileInfos);
			editJson.setSuccess(true);
			out.print(editJson.toJsonString());
		}catch(Exception e){
			editJson.setBean(fileInfos);
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
	 * 文件下载
	 */
	@ResponseBody
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath=request.getParameter("filePath");
		String fileName=FilenameUtils.getName(filePath);
		//是否允许读取
		if(fileService.isAllowRead(filePath)){
			InputStream is=new FileInputStream(new File(filePath));
			fileService.downloadFile(is, fileName, response);
		}else{
			throw new RuntimeException("非法的文件读取！");
		}
	}
	
	/**
	 * 显示图片
	 */
	@ResponseBody
	@RequestMapping("/showPic")
	public void showPic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath=request.getParameter("filePath");
		filePath=URLDecoder.decode(filePath,"UTF-8");
		//是否允许读取
		if(fileService.isAllowRead(filePath)){
			InputStream is=new FileInputStream(new File(filePath));
			fileService.showPic(is, response);
		}else{
			throw new RuntimeException("非法的文件读取！");
		}
	}
	
}
