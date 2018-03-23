package cn.com.sparknet.nclk.bean;

/**
 * 文件信息（不包括表单参数）
 * @author chenxy
 *
 */
public class FileInfo {
	
	//文件全名
	private String fileName;
	//文件前缀名
	private String filePrefixName;
	//文件后缀名
	private String fileSuffixName;
	//文件大小
    private long fileSize;
    //文件路径
    private String filePath;
    
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePrefixName() {
		return filePrefixName;
	}
	
	public void setFilePrefixName(String filePrefixName) {
		this.filePrefixName = filePrefixName;
	}
	
	public String getFileSuffixName() {
		return fileSuffixName;
	}
	
	public void setFileSuffixName(String fileSuffixName) {
		this.fileSuffixName = fileSuffixName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
    
}
