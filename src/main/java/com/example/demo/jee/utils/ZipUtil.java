package com.example.demo.jee.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 将多个文件打包成zip文件，目前只有SINA申请企业用户时使用
 * @author hkw
 *
 */
public class ZipUtil {
	private ZipInputStream  zipIn;      //解压Zip
	private ZipOutputStream zipOut;     //压缩Zip
	private ZipEntry        zipEntry;
	private static int      bufSize;    //size of bytes
	private byte[]          buf;
	private int             readedBytes;

	public ZipUtil(){
		this(512);
	}

	public ZipUtil(int bufSize){
		this.bufSize = bufSize;
		this.buf = new byte[this.bufSize];
	}
	
	/**
	 * 将文件列表压缩成zip
	 */
	public static void toZip(String ... filePath) throws Exception{
		byte[] buffer = new byte[1024];   
		  
	       //生成的ZIP文件名为upload.zip   
	  
	       String strZipName = "c:/upload.zip";   
	  
	       ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));   
	  
	       //需要同时下载的两个文件result.txt ，source.txt   
	  
	       File[] file1 = new File[filePath.length];//{new File("D:/Pictures/壁纸/1349423306618.jpg"),new File("D:/Pictures/壁纸/1416986959385.jpg"),new File("D:/Pictures/壁纸/1425004861624.jpg")};   
	       
	       for(int i=0;i<filePath.length;i++) {   
	    	   file1[i] = new File(filePath[i]);
	           FileInputStream fis = new FileInputStream(file1[i]);   
	  
	           out.putNextEntry(new ZipEntry(file1[i].getName()));   
	  
	           int len;   
	  
	           //读入需要下载的文件的内容，打包到zip文件   
	  
	          while((len = fis.read(buffer))>0) {   
	  
	           out.write(buffer,0,len);    
	  
	          }   
	  
	           out.closeEntry();   
	  
	           fis.close();   
	  
	       }   
	  
	        out.close();   
	  
	        System.out.println("生成Demo.zip成功");   
	  
	    }  
	/**
	 * 解压指定zip文件至当前文件
	 * @param unZipfileName  需要解压的zip文件名
	 * @return 解压缩完成的文件全路径
	 */
	public String unZip(String unZipfileName){
		FileOutputStream fileOut = null;
		File file;
		String filePath = null;
		try{
			this.zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)));

			while((this.zipEntry = this.zipIn.getNextEntry()) != null){

				filePath=new File(unZipfileName).getParent()+File.separator+this.zipEntry.getName();
				file = new File(filePath);

				if(this.zipEntry.isDirectory()){
					file.mkdirs();
				} else {
					//如果指定文件的目录不存在,则创建之.
					File parent = file.getParentFile();
					if(!parent.exists()){
						parent.mkdirs();
					}

					fileOut = new FileOutputStream(file);
					while(( this.readedBytes = this.zipIn.read(this.buf) ) > 0){
						fileOut.write(this.buf , 0 , this.readedBytes );
					}
				}
				this.zipIn.closeEntry();
			}
			return filePath;
		}catch(Exception ioe){
			throw new RuntimeException(ioe);
		} finally {
			if(this.zipIn != null) {
				try {
					this.zipIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
