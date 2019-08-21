package com.example.demo.jee.utils.pdf;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;    
import java.io.ByteArrayOutputStream;    
import java.io.File;    
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;    
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.demo.jee.constants.SysConstants;
import com.example.demo.jee.utils.FtpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;


public class PDFUtils {
        
	
	private final static Logger logger = LoggerFactory.getLogger(PDFUtils.class);
	
    public static void main(String[] args) {    
         //将PDF格式文件转成base64编码 
             String base64String = getPDFBinary(new File("E:/1.pdf"));
             System.out.println(base64String);
            //将base64的编码转成PDF格式文件
             //base64StringToPDF(base64String);
//             base64StringToPDF(base64String,"E:/2.pdf");
             base64StringUpload(base64String, "125","1258");
    }  
        
    /**
     *  将PDF转换成base64编码
     *  1.使用BufferedInputStream和FileInputStream从File指定的文件中读取内容；
     *  2.然后建立写入到ByteArrayOutputStream底层输出流对象的缓冲输出流BufferedOutputStream
     *  3.底层输出流转换成字节数组，然后由BASE64Encoder的对象对流进行编码
     * */
   public static String getPDFBinary(File file) {
            FileInputStream fin =null;
            BufferedInputStream bin =null;
            ByteArrayOutputStream baos = null;
            BufferedOutputStream bout =null;
            try {
                    //建立读取文件的文件输出流
                    fin = new FileInputStream(file);
                    //在文件输出流上安装节点流（更大效率读取）
                    bin = new BufferedInputStream(fin);
                    // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
                    baos = new ByteArrayOutputStream();
                    //创建一个新的缓冲输出流，以将数据写入指定的底层输出流
                    bout = new BufferedOutputStream(baos);
                    byte[] buffer = new byte[1024];
                    int len = bin.read(buffer);
                    while(len != -1){
                            bout.write(buffer, 0, len);
                            len = bin.read(buffer);
                    }
                    //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
                    bout.flush();
                     byte[] bytes = baos.toByteArray();
                     //apache公司的API
                     return Base64.encodeBase64String(bytes);
                    
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }finally{
                        try {
                                fin.close();
                                bin.close();
                                //关闭 ByteArrayOutputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何 IOException
                                //baos.close();
                                bout.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
            return null;
    }
        
    /**
     * 将base64编码转换成PDF
     * @param base64sString
     * 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *  2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *  3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *  4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     */
   public static void base64StringToPDF(String base64sString,String filePath){
            BufferedInputStream bin = null;
            FileOutputStream fout = null;
            BufferedOutputStream bout = null;
            try {
                    //apache公司的API
            			byte[] bytes = Base64.decodeBase64(base64sString);
                        //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
                        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                        //创建从底层输入流中读取数据的缓冲输入流对象
                        bin = new BufferedInputStream(bais);
                        //指定输出的文件
                        File file = new File(filePath);
                        //创建到指定文件的输出流
                        fout  = new FileOutputStream(file);
                        //为文件输出流对接缓冲输出流对象
                        bout = new BufferedOutputStream(fout);
                        byte[] buffers = new byte[1024];
                        int len = bin.read(buffers);
                        while(len != -1){
                                bout.write(buffers, 0, len);
                                len = bin.read(buffers);
                        }
                        //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
                        bout.flush();
                        
                } catch (IOException e) {
                        e.printStackTrace();
                }finally{
                        try {
                                bin.close();
                                fout.close();
                                bout.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
    }
   
   //测试用
   public static final String ftp_host ="esb.xr58.com";
   public static final int ftp_port =21;
   public static final String ftp_username ="ftp_admin";
   public static final String ftp_password ="Njkcfg6310@";
   
   /**
    * 将base64编码转换成PDF并上传ftp服务器
    * @param base64sString
    * 1.使用BASE64Decoder对编码的字符串解码成字节数组
    *  2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
    *  3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
    *  4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
    */
  public static void base64StringUpload(String base64sString,String biddingId,String cashPlatfromId){
           BufferedInputStream bin = null;
           try {
        	   
        	   
		    	   if (StringUtils.isBlank(base64sString) || StringUtils.isBlank(biddingId) || StringUtils.isBlank(cashPlatfromId)) {
		    		   throw new RuntimeException("上传合同文件的参数非法");
		    	   }
		    	   String savePath = getSaveUrl(cashPlatfromId);	//保存路径
	       			String fileNamePdf = biddingId + ".pdf";	//文件名(需进行文件类型校验)
	       		
	       			FtpUtil ftp = new FtpUtil();// 建立ftp连接
	       			ftp.setEncoding("GBK");
//       			ftp.connect(SysConstants.FTP_HOST, SysConstants.FTP_PORT,
//       					SysConstants.FTP_USERNAME,
//       					SysConstants.FTP_PASSWORD, false);
       			
	       			//测试
	       			ftp.connect(ftp_host, ftp_port, ftp_username, ftp_password, false);
       			
       			
       			ftp.setAndCreateWorkingDirectory(savePath);
               //apache公司的API
       			byte[] bytes = Base64.decodeBase64(base64sString);
                   //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
               ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
               //创建从底层输入流中读取数据的缓冲输入流对象
               bin = new BufferedInputStream(bais);
               
               //上传借款合同
      			ftp.putFile(fileNamePdf, bin);
                       
               } catch (IOException e) {
                     logger.error("上传合同文件失败，合同所属标的："+biddingId,e);
                     throw new RuntimeException("上传pdf合同文件失败！");
                       
               }finally{
                   try {
                           bin.close();
                   } catch (IOException e) {
                           e.printStackTrace();
                   }
               }
   }
  
  
  
  /**
	 * 合同在ftp保存的路径：
	 * 	：test/{cashPlatformId}/20171207/{biddingId}.png
	 * 	目录： test 从java.properties中读取
	 * 	平台：
	 * 	文件标识： 如企业合同、企业资质
	 *  时间：20180101
	 *  文件名：
	 * */
	public static String getSaveUrl(String cashPlatformId) {
		//获取保存目录
		StringBuilder sb = new StringBuilder(SysConstants.SAVEPATHPDF+"/");
		//平台
		sb.append(cashPlatformId+"/");
//		//文件标识
//		sb.append(biddingId+"/");
		//时间
		sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		return sb.toString();
		
	}
	
	
  
  
}

