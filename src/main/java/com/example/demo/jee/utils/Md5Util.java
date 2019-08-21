package com.example.demo.jee.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 密码生成器
 * 
 * @author minz
 *
 */
public class Md5Util {

	public Md5Util() {
	}

	public static final String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte strTemp[] = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 15];
				str[k++] = hexDigits[byte0 & 15];
			}
			return new String(str);
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将文件做MD5加密
	 * 
	 * @param file
	 *            鏂囦欢璺姴
	 * @return 32浣嶇殑MD5鎽樿
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		String bytes2hex03 = bytes2hex03(digest.digest());
		return bytes2hex03;

	}
	
	 public static String bytes2hex03(byte[] bytes)  
	    {  
	        final String HEX = "0123456789abcdef";  
	        StringBuilder sb = new StringBuilder(bytes.length * 2);  
	        for (byte b : bytes)  
	        {  
	            // 
	            sb.append(HEX.charAt((b >> 4) & 0x0f));  
	            // 
	            sb.append(HEX.charAt(b & 0x0f));  
	        }  
	  
	        return sb.toString();  
	    }
	 
	 public static void main(String[] args) {
		 String pwd = Md5Util.md5(Md5Util.md5("123456").toUpperCase());
		 System.out.println("pwd:"+pwd);
	}
}
