package cn.com.sparknet.nclk.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sparknet.nclk.bean.FileFormInfo;
import cn.com.sparknet.nclk.bean.FileInfo;

/**
 * 文件操作
 * @author chenxy
 *
 */
public interface FileService {
	
	/**
	 * 上传文件（不包括表单参数）
	 */
	public List<FileInfo> uploadFile(HttpServletRequest request);
	
	/**
	 * 上传文件（包括表单参数）
	 */
	public FileFormInfo uploadFileWithParams(HttpServletRequest request);
	
	/**
	 * 下载文件
	 */
	public void downloadFile(InputStream is,String fileName,HttpServletResponse response);
	
	/**
	 * 显示图片
	 */
	public void showPic(InputStream is, HttpServletResponse response);
	
	/**
	 * 是否允许读取
	 * 只允许下载、显示临时目录下的文件
	 * 防止“任意文件读取”漏洞
	 */
	public boolean isAllowRead(String filePath);
	
}
