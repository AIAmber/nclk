package cn.com.sparknet.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩与解压缩
 * @author chenxy
 *
 */
public final class ZipUtil {
	
	private ZipUtil(){
	}
	
	/**
	 * 文件压缩
	 * @param fileNames			需要压缩的文件名数组
	 * @param fileStreams		需要压缩的文件流数组
	 * @param zipFile			指定需要生成的ZIP文件
	 * @throws Exception
	 */
	public static void zip(String[] fileNames,InputStream[] fileStreams,File zipFile) throws Exception {
		OutputStream os=null;
		ZipOutputStream zos=null;
		try {
			os=new FileOutputStream(zipFile);
    		zos=new ZipOutputStream(os);
			for(int i=0;i<fileNames.length;i++){
				zos.putNextEntry(new ZipEntry(fileNames[i]));
				InputStream is=fileStreams[i];
				int bufferSize=InputStreamUtil.getBufferSize(is);
				byte[] content=new byte[bufferSize];
                if(is!=null){
                	while(is.read(content)!=-1){  
                        zos.write(content);  
                    }
                	is.close();
                	is=null;
                }else{
                	zos.write(null);
                }
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(null!=zos){
				zos.flush();
				zos.close();
				zos=null;
			}
			if(null!=os){
				os.flush();
				os.close();
				os=null;
			}
		}
	}
	
	/**
	 * 文件解压缩
	 * @param zipInputStream	ZIP文件流
	 * @param outputPath		解压后的输出路径
	 * @throws Exception
	 */
	public static void unZip(InputStream zipInputStream,String outputPath) throws Exception {
		File outputFile=null;
		ZipEntry entry=null;
		ZipInputStream zis=null;
		BufferedInputStream bis=null;
		try {
			zis=new ZipInputStream(zipInputStream);
			bis=new BufferedInputStream(zis);
			while((entry = zis.getNextEntry())!=null && !entry.isDirectory()){
				outputFile=new File(outputPath,entry.getName());
				if(!outputFile.exists()){
					outputFile.getParentFile().mkdirs();
				}
				FileOutputStream fos=new FileOutputStream(outputFile);
				BufferedOutputStream bos=new BufferedOutputStream(fos);
				int b;
				while((b=bis.read())!=-1){
					bos.write(b);
				}
				if(null!=bos){
					bos.flush();
					bos.close();
					bos=null;
				}
				if(null!=fos){
					fos.flush();
					fos.close();
					fos=null;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(null!=bis){
				bis.close();
				bis=null;
			}
			if(null!=zis){
				zis.close();
				zis=null;
			}
			if(null!=zipInputStream){
				zipInputStream.close();
				zipInputStream=null;
			}
		}
	}
	
}
