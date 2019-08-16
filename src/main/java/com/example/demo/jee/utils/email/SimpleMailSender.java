package com.example.demo.jee.utils.email;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
import java.io.File;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.jee.constants.SysConstants;
import com.example.demo.jee.utils.SpiderUtile.SpiderXpathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单邮件（不带附件的邮件）发送器
 */


public class SimpleMailSender {
	
  private static Logger logger=LoggerFactory.getLogger(SimpleMailSender.class);
 /**   
  * 以文本格式发送邮件   
  * @param mailInfo 待发送的邮件的信息   
  */    
    public JSONObject sendTextMail(MailSenderInfo mailInfo) {
	      JSONObject ret=new JSONObject();
	      // 判断是否需要身份认证    
	      MyAuthenticator authenticator = null;
	      Properties pro = mailInfo.getProperties();   
	      if (mailInfo.isValidate()) {    
	      // 如果需要身份认证，则创建一个密码验证器    
	        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
	      }   
	      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
	      Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	      logger.info("构造一个发送邮件的session");
	      
	      // 根据session创建一个邮件消息    
	      Message mailMessage = new MimeMessage(sendMailSession);  
	     try{
		      // 创建邮件发送者地址    
		      Address from = new InternetAddress(mailInfo.getFromAddress());    
		      // 设置邮件消息的发送者    
		      mailMessage.setFrom(from);    
		      // 创建邮件的接收者地址，并设置到邮件消息中    
		      Address to = new InternetAddress(mailInfo.getToAddress());    
		      mailMessage.setRecipient(Message.RecipientType.TO,to);    
		      // 设置邮件消息的主题    
		      mailMessage.setSubject(mailInfo.getSubject());    
		      // 设置邮件消息发送的时间    
		      mailMessage.setSentDate(new Date());    
		      // 设置邮件消息的主要内容    
		      String mailContent = mailInfo.getContent();    
		      mailMessage.setText(mailContent);    
		      // 发送邮件    
		      Transport.send(mailMessage); 
		      logger.info("发送成功！");
		      ret.put("code", SysConstants.SUCCESS_CODE);
		      ret.put("msg", "发送邮件成功");
		      return ret;    
	     }catch(Exception e){
	    	 ret.put("code", SysConstants.FAILED_CODE);
			 ret.put("msg", "发送邮件失败");
	     	 e.printStackTrace();
	     	 logger.error("send email error"+e.getMessage());
	     	 return ret;
	    	 
	     }
	     
    }    
       
    /**   
      * 以HTML格式发送邮件   
      * @param mailInfo 待发送的邮件信息   
      */    
    public  JSONObject sendHtmlMail(MailSenderInfo mailInfo) {    
    	 JSONObject ret=new JSONObject();
      // 判断是否需要身份认证    
      MyAuthenticator authenticator = null;   
      Properties pro = mailInfo.getProperties();   
      //如果需要身份认证，则创建一个密码验证器     
      if (mailInfo.isValidate()) {    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
      }    
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
        
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);  
      try{
	      // 创建邮件发送者地址    
	      Address from = new InternetAddress(mailInfo.getFromAddress());    
	      // 设置邮件消息的发送者    
	      mailMessage.setFrom(from);    
	      // 创建邮件的接收者地址，并设置到邮件消息中    
	      Address to = new InternetAddress(mailInfo.getToAddress());    
	      // Message.RecipientType.TO属性表示接收者的类型为TO    
	      mailMessage.setRecipient(Message.RecipientType.TO,to);    
	      // 设置邮件消息的主题    
	      mailMessage.setSubject(mailInfo.getSubject());    
	      // 设置邮件消息发送的时间    
	      mailMessage.setSentDate(new Date());    
	      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
	      Multipart mainPart = new MimeMultipart();    
	      // 创建一个包含HTML内容的MimeBodyPart    
	      BodyPart html = new MimeBodyPart();    
	      // 设置HTML内容    
	      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
	      mainPart.addBodyPart(html);    
	      // 将MiniMultipart对象设置为邮件内容    
	      mailMessage.setContent(mainPart);    
	      // 发送邮件    
	      Transport.send(mailMessage);
	      ret.put("code", SysConstants.SUCCESS_CODE);
	      ret.put("msg", "发送邮件成功");
	      return ret; 
      }catch(Exception e){
    	  ret.put("code", SysConstants.FAILED_CODE);
 		  ret.put("msg", "发送邮件失败");
      	  e.printStackTrace();
      	 logger.error("send email error"+e.getMessage());
      	 return ret;
    	  
      }  
        
    }    
 
    /*
     * @title:标题
     * @content:内容
     * @type:类型,1:文本格式;2:html格式
     * @tomail:接收的邮箱
     */
    public JSONObject sendMail(MailSenderInfo mailInfo,String type) throws Exception{   
        JSONObject ret=new JSONObject();
    	/*//这个类主要是设置邮件   
	     MailSenderInfo mailInfo = new MailSenderInfo();    
	     mailInfo.setMailServerHost("smtp.qq.com");    
	     mailInfo.setMailServerPort("25");    
	     mailInfo.setValidate(true);    
	     mailInfo.setUserName("itfather@1b23.com");    
	     mailInfo.setPassword("tttt");//您的邮箱密码    
	     mailInfo.setFromAddress("itfather@1b23.com");    
	     mailInfo.setToAddress(tomail);    
	     mailInfo.setSubject(title);    
	     mailInfo.setContent(content);*/    
	     //这个类主要来发送邮件   
	  
	     SimpleMailSender sms = new SimpleMailSender();   
	     
	     if("1".equals(type)){
	    	 return sms.sendTextMail(mailInfo);//发送文体格式    
	     }else if("2".equals(type)){
	    	 return sms.sendHtmlMail(mailInfo);//发送html格式   
	     }
	     ret.put("code", SysConstants.FAILED_CODE);
		 ret.put("msg", "发送邮件失败,参数格式不对");
	     
	     return ret;
	   }
    /**
     * @param SMTP  	邮件服务器
     * @param PORT		端口
     * @param EMAIL		本邮箱账号
     * @param PAW		本邮箱密码
     * @param toEMAIL	对方箱账号
     * @param TITLE		标题
     * @param CONTENT	内容
     * @param TYPE		1：文本格式;2：HTML格式
     */
    public static void sendEmail(String SMTP, String PORT, String EMAIL, String PAW, String toEMAIL, String TITLE, String CONTENT, String TYPE) throws Exception{ 
    	
        //这个类主要是设置邮件   
	     MailSenderInfo mailInfo = new MailSenderInfo();
	     
	     mailInfo.setMailServerHost(SMTP);    
	     mailInfo.setMailServerPort(PORT);    
	     mailInfo.setValidate(true);    
	     mailInfo.setUserName(EMAIL);    
	     mailInfo.setPassword(PAW);   
	     mailInfo.setFromAddress(EMAIL);    
	     mailInfo.setToAddress(toEMAIL);    
	     mailInfo.setSubject(TITLE);    
	     mailInfo.setContent(CONTENT);  
	     //这个类主要来发送邮件   
	  
	     SimpleMailSender sms = new SimpleMailSender();
	     
	    if("1".equals(TYPE)){
	    	sms.sendTextMail(mailInfo);
	    }else{
	    	sms.sendHtmlMail(mailInfo);
	    }
	     
	   }
    
    
   
    /**
     * 
     * @Name: sendMimeMessage
     * @Description:发送带附件和文本的邮件
     * @Author:shiliu
     * @Version: V1.00 （版本号）
     * @Create Date: 2018年4月19日（创建日期）
     * @param mailInfo 邮件对象
     * //@param filePath 附件地址
     * @throws Exception
     */
    public  JSONObject sendMimeMessage(MailSenderInfo mailInfo,List<File>fileList){
    	
    	JSONObject ret=new JSONObject();
    	
    	// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();

        //如果需要身份认证，则创建一个密码验证器     
        if (mailInfo.isValidate()) {    
          authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
        }    
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
        Session sendMailSession = Session.getInstance(pro, authenticator);  
        sendMailSession.setDebug(true);
        logger.info("构造一个发送邮件的session");
    	
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(sendMailSession);
        
        try{
		        // 2. From: 发件人
		        message.setFrom(new InternetAddress(mailInfo.getFromAddress()));
		        
		        
		        String recieveAddress = mailInfo.getRecieveAddress();
		        Address[] addr = new Address[1];
		        addr[0] = new InternetAddress(recieveAddress);
		        message.addRecipients(RecipientType.TO, addr);
		
		        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
		       // message.addRecipient(RecipientType.TO, addr);
		
		        // 4. Subject: 邮件主题
		        message.setSubject(mailInfo.getSubject(), "UTF-8");
		        
		
		        /*
		         * 下面是邮件内容的创建:
		         */
		
		       /* // 5. 创建图片“节点”
		        MimeBodyPart image = new MimeBodyPart();
		        DataHandler dh = new DataHandler(new FileDataSource("C:\\attachment\\icon128.png")); // 读取本地文件
		        image.setDataHandler(dh);                   // 将图片数据添加到“节点”
		        image.setContentID("image_fairy_tail");     // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
		      
		*/
		        
		        //6. 创建文本“节点”
		        MimeBodyPart text = new MimeBodyPart();
		        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
		        text.setContent(mailInfo.getContent(), "text/html;charset=UTF-8");
		
		        // 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
		        MimeMultipart mm_text_image = new MimeMultipart();
		        mm_text_image.addBodyPart(text);
		        //mm_text_image.addBodyPart(image);
		        //mm_text_image.setSubType("related");    // 关联关系
		
		        // 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
		        //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
		        //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
		        MimeBodyPart text_image = new MimeBodyPart();
		        text_image.setContent(mm_text_image);
		        
		        
		        
		        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
		        MimeMultipart mm = new MimeMultipart();
		        mm.addBodyPart(text_image);
		       
		
		        
		        
		        
		        
		        for (File file : fileList) {
		        	
		        	  // 9. 创建附件“节点”
			        MimeBodyPart attachment = new MimeBodyPart();
			        DataHandler dh2 = new DataHandler(new FileDataSource(file.getPath()));  // 读取本地文件
			        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
			        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));              // 设置附件的文件名（需要编码）
			        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
			
					
				}
		        
		        
		        mm.setSubType("mixed");         // 混合关系
		        
		      
		      
		        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
		        message.setContent(mm);
		        
		
		        // 12. 设置发件时间
		        message.setSentDate(new Date());
		
		        // 13. 保存上面的所有设置
		        message.saveChanges();
		        // 发送邮件    
		        Transport.send(message);
//		        Transport  transport = sendMailSession.getTransport("smtp");

//		        transport.connect(mailInfo.getMailServerHost(), mailInfo.getUserName(),
//						mailInfo.getPassword());

		        // 然后发送邮件
		        //transport.sendMessage(message, message.getAllRecipients());

		        // 等判断所有邮件都发送完毕了，再
		        //transport.close();
		        
		        ret.put("code", SysConstants.SUCCESS_CODE);
		        ret.put("msg", "发送邮件成功");
		        
        }catch(Exception e){
        	ret.put("code", SysConstants.FAILED_CODE);
		    ret.put("msg", "发送邮件失败");
        	e.printStackTrace();
        	logger.error("send email error"+e.getMessage());
        	return ret;
        }
       // logger.info("发送成功！");
        return ret;
    }

    public static void main(String[] args){
		//发送邮件通知被申请人
		MailSenderInfo be_mailInfo = new MailSenderInfo();
//		be_mailInfo.setMailServerHost(SysConstants.MY_EMAIL_SMTPHOST);
//		be_mailInfo.setMailServerPort("465");
		be_mailInfo.setMailServerHost("smtp.bestedm.org");
		be_mailInfo.setMailServerPort("25");
		be_mailInfo.setValidate(true);

//		be_mailInfo.setUserName(SysConstants.MY_EMAIL_ACCOUNT);
//		be_mailInfo.setPassword(SysConstants.MY_EMAIL_PASSWORD);//您的邮箱密码
		be_mailInfo.setUserName("ken@ceshi1.unioncyber.net");
		be_mailInfo.setPassword("DFdf235drFW");//您的邮箱密码
		be_mailInfo.setFromAddress("ken@ceshi1.unioncyber.net");
		be_mailInfo.setSubject("攸县人民法院执行通知");
		be_mailInfo.setContent("11111");
		be_mailInfo.setRecieveAddress("kewei.huang@xr58.com");

		//这个类主要来发送邮件
		SimpleMailSender be_sms = new SimpleMailSender();
		List<File>fileList=new ArrayList<>();
		fileList.add(new File("d:\\123\\1.jpg"));
		fileList.add(new File("d:\\123\\2.jpg"));
		fileList.add(new File("d:\\123\\3.jpg"));
		fileList.add(new File("d:\\123\\4.jpg"));
		fileList.add(new File("d:\\123\\5.jpg"));
//		JSONObject resu= be_sms.sendMimeMessage(be_mailInfo, fileList);//发送文本+附件格式
		//爬取公网代理IP和PORT列表
		//Map<String,String> ipportMap = SpiderXpathUtil.getProxyIpByInternet();

		//循环发送邮件，每个IP和PORT发送10封邮件
		long startTime=System.currentTimeMillis();   //获取开始时间
		logger.info("开始--------------------------------------------------"+startTime);

		int sendCount = 0;
		int fieldCount = 0;
		int succeseCount = 0;
		/*try {
			sendCount = 1;
			System.setProperty("http.maxRedirects", "50");
			System.getProperties().setProperty("proxySet", "true");
			fieldCount = 1;
			succeseCount = 1;
			//发送500封邮件
			for (String key : ipportMap.keySet()) {
				if(sendCount>500){
					break;
				}
				//发送10封邮件后切换下个IP
				int i = 1;
				while (i <= 10) {
					System.out.print(i + "--------");
					System.out.println(key + ":" + ipportMap.get(key));
					//设置本机的公网IP
					System.getProperties().setProperty("http.proxyHost", key);
					System.getProperties().setProperty("http.proxyPort", ipportMap.get(key));
					be_mailInfo.setSubject("第" + sendCount + "封邮件");
					//JSONObject resu = be_sms.sendMimeMessage(be_mailInfo, fileList);//发送文本+附件格式
					*//*if(resu.getString("code").equals(SysConstants.SUCCESS_CODE)){
						logger.info("发送成功");
						succeseCount++;
					}else{
						logger.info("发送失败");
						fieldCount++;
					}*//*
					succeseCount++;
					i++;
					sendCount++;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			fieldCount++;
			logger.info("发送失败---------------------------------------------------------------------------------");
		}*/

		//for (int i = 0 ; i < 500 ; i++){
			//设置本机的公网IP
			be_mailInfo.setSubject("第" + sendCount + "封邮件");
			JSONObject resu = be_sms.sendMimeMessage(be_mailInfo, fileList);//发送文本+附件格式
			if(resu.getString("code").equals(SysConstants.SUCCESS_CODE)){
				logger.info("发送成功");
				succeseCount++;
			}else{
				logger.info("发送失败");
				fieldCount++;
			}
			succeseCount++;
			//i++;
			sendCount++;
		//}

		//System.out.println(i++ + "-=-------key" + key +"value" + ipportMap.get(key));
		System.out.println("发送总数：" + sendCount);
		System.out.println("成功发送总数：" + fieldCount);
		System.out.println("失败发送总数：" + succeseCount);
		long endTime=System.currentTimeMillis(); //获取结束时间
		float excTime=(float)(endTime-startTime)/1000;
		logger.info("结束--------------------------------------------------"+excTime+"s");




	   }
    
}   
