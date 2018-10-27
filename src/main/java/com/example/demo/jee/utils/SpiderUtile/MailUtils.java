package com.example.demo.jee.utils.SpiderUtile;

import com.example.demo.jee.constants.SysConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtils {
	/**
	 * 向用户发送邮件
	 */
	private static final long serialVersionUID = 1L;
 
	private MimeMessage mimeMsg; // MIME邮件对象
 
	private Session session; // 邮件会话对象
 
	private Properties props; // 系统属性
 
	private boolean needAuth = false; // smtp是否需要认证
 
	private String username; // smtp认证用户名和密码
 
	private String password;
 
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成
 
	private String log;
 
	public MailUtils() {
 
	}
 
	public MailUtils(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}
 
	public void setSmtpHost(String hostName) {
		System.out.println("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
		props.put("mail.smtp.port", "25"); // 设置SMTP端口
//		props.put("mail.debug","true");
		//props.put("mail.smtp.localhost", "localHostAdress");
	}
 
	public boolean createMimeMessage() {
		try {
			System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			log = "获取邮件会话对象时发生错误！" + e.toString();
			System.err.println(log);
			return false;
		}
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart(); // mp 一个multipart对象
			// Multipart is a container that holds multiple body parts.
			return true;
		} catch (Exception e) {
			log = "创建MIME邮件对象失败！" + e;
			System.err.println(log);
			return false;
		}
	}
 
	public void setNeedAuth(boolean need) {
		System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}
 
	public void setNamePass(String name, String pass) {
		System.out.println("程序得到用户名与密码");
		username = name;
		password = pass;
	}
 
	public boolean setSubject(String mailSubject) {
		System.out.println("设置邮件主题！");
		try {
			mimeMsg.setSubject(MimeUtility.encodeText(mailSubject,"utf-8","B"));
			return true;
		} catch (Exception e) {
			log = "设置邮件主题发生错误！"+e;
			System.err.println(log);
			return false;
		}
	}
 
	public boolean setBody(String mailBody) {
		try {
			System.out.println("设置邮件体格式");
			BodyPart bp = new MimeBodyPart();
			// 转换成中文格式
			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=utf-8>"
							+ mailBody, "text/html;charset=utf-8");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			log = "设置邮件正文时发生错误！" + e;
			System.err.println(log);
			return false;
		}
	}
 
	public boolean setFiles(List<String> files){
		try{
			for(String s:files){
				MimeBodyPart mbp=new MimeBodyPart();
				FileDataSource fds=new FileDataSource(s); //得到数据源
				mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
				mbp.setFileName(fds.getName());  //得到文件名同样至入BodyPart
				mp.addBodyPart(mbp);
			}
			return true;
		}catch(Exception e){
			log = "增加附件时出错："+e;
			e.printStackTrace();
			return false;
		}
	}
 
	public boolean addFile(String path, String name){
		try{
			MimeBodyPart mbp=new MimeBodyPart();
			FileDataSource fds=new FileDataSource(path); //得到数据源
			mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
			mbp.setFileName(MimeUtility.encodeText(name,"utf-8","B"));
			mp.addBodyPart(mbp);
			return true;
		}catch(Exception e){
			log = "增加附件时出错："+e;
			e.printStackTrace();
			return false;
		}
	}
 
	public boolean setFrom(String from) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			log = "设置发信人出错:"+e;
			return false;
		}
	}
 
	public boolean setTo(String to) {
		System.out.println("设置收信人");
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
 
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
 
	public boolean sendout() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			System.out.println("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			System.out.println("发送邮件成功！");
			//保存邮件到发件箱

			transport.close();
			//saveEmailToSentMailFolder(mimeMsg);
			return true;
		} catch (Exception e) {
			log = "邮件发送失败！" + e;
			System.err.println(log);
			return false;
		}
	}

	/**
	 * 获取用户的发件箱文件夹
	 *
	 * @param message
	 *            信息
	 * @param store
	 *            存储
	 * @return
	 * @throws MessagingException
	 */
	private Folder getSentMailFolder(Message message, Store store)
			throws  MessagingException {
		// 准备连接服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", "imap.vip.sina.com");
		props.setProperty("mail.imap.port", "143");

		/** QQ邮箱需要建立ssl连接 */
		props.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		//props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.starttls.enable", "true");
		props.setProperty("mail.imap.socketFactory.port", "143");

		// 创建Session实例对象
		Session session = Session.getInstance(props);
		URLName urln = new URLName("imap", "imap.vip.sina.com", 143, null,
				"hnyxcourt@vip.sina.com", "0731yxfy");
		// 创建IMAP协议的Store对象
		store = session.getStore(urln);
		store.connect();

		// 获得发件箱
		Folder folder = store.getFolder("已发送");
		if (!folder.exists()) {
			folder.create(Folder.HOLDS_MESSAGES);
		}
		// 以读写模式打开发件箱
		folder.open(Folder.READ_WRITE);

		return folder;
	}


	/**
	 * 保存邮件到发件箱
	 *
	 * @param message
	 *            邮件信息
	 */
	private void saveEmailToSentMailFolder(Message message) {

		Store store = null;
		Folder sentFolder = null;
		try {
			sentFolder = getSentMailFolder(message, store);
			message.setFlag(Flags.Flag.SEEN, true); // 设置已读标志
			sentFolder.appendMessages(new Message[] { message });

			System.out.println("已保存到发件箱...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 判断发件文件夹是否打开如果打开则将其关闭
			if (sentFolder != null && sentFolder.isOpen()) {
				try {
					sentFolder.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			// 判断邮箱存储是否打开如果打开则将其关闭
			if (store != null && store.isConnected()) {
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getLog(){
		return log;
	}
 
	public static void main(String[] args) {

		List<String>fileList=new ArrayList<>();
		fileList.add("d:\\123\\1.jpg");
		fileList.add("d:\\123\\2.jpg");
		fileList.add("d:\\123\\3.jpg");
		fileList.add("d:\\123\\4.jpg");
		fileList.add("d:\\123\\5.jpg");


		MailUtils utils = new MailUtils("smtp.bestedm.org");
		utils.setFrom("ken@ceshi1.unioncyber.net");
		utils.setSubject("123123123");
		utils.setNamePass("ken@ceshi1.unioncyber.net","DFdf235drFW");
		utils.setBody("main");
		utils.setTo("20224397@qq.com");
		utils.setFiles(fileList);
		/*MailUtils utils = new MailUtils(SysConstants.MY_EMAIL_SMTPHOST);
		utils.setFrom("hnyxcourt@vip.sina.com");
		utils.setSubject("ceshi");
		utils.setNamePass("hnyxcourt@vip.sina.com","0731yxfy");
		utils.setBody("main");
		utils.setTo("20224397@qq.cio");
		utils.setFiles(fileList);*/
		utils.sendout();

	}
}