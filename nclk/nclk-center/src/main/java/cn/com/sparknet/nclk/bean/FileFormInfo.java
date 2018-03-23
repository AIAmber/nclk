package cn.com.sparknet.nclk.bean;

import java.util.List;
import java.util.Map;

/**
 * 文件信息（包括表单参数）
 * @author chenxy
 */
public class FileFormInfo {

	//表单参数信息
	private Map<String, Object> formParams;
	//上传文件信息
	private List<FileInfo> fileInfos;
	
	public Map<String, Object> getFormParams() {
		return formParams;
	}
	
	public void setFormParams(Map<String, Object> formParams) {
		this.formParams = formParams;
	}
	
	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}
	
	public void setFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}

}
