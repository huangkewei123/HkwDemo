package com.example.demo.java2word;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.testEntity.word.Address;
import com.example.demo.testEntity.word.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
 
public class FrermarkerXml {
	Template testfile;
	Map root;
	private void initData() {
		root = new HashMap();
		User user = new User();
		user.setName("root");
		user.setPwd("root");
		List<Address> addresses = new ArrayList<Address>();
		for (int i = 0; i < 15; i++) {
			Address address = new Address();
			address.setName("address_"+i);
			address.setSex("女"+i);
			addresses.add(address);
		}
		Map<String, Address> keyadds = new HashMap<String, Address>();
		for (int i = 0; i < 10; i++) {
			Address address = new Address();
			address.setName("mapaddress_"+i);
			address.setSex("男"+i);
			keyadds.put("keyadd_"+i, address);
		}
		user.setAdds(addresses);
		user.setKeyadds(keyadds);
		root.put("user", user);
	}


	private  void init(){
	       try {
	             Configuration tempConfiguration = new Configuration();
	             tempConfiguration.setClassicCompatible(true);
	             tempConfiguration.setClassForTemplateLoading(this.getClass(), "/");	            
	             tempConfiguration.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	             tempConfiguration.setNumberFormat("");
	             tempConfiguration.setDefaultEncoding("utf-8");
	             testfile = tempConfiguration.getTemplate("java2word/123.ftl");
	       } catch (IOException ex) {
	             ex.printStackTrace();
	       }
	}

	public String toString(){
		   StringWriter sw = new StringWriter();
		   File file = new File("E:\\testWord.doc");
		   if(!file.exists()){
		   		try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		   }
		   try {
		   		testfile.process(root, new OutputStreamWriter(new FileOutputStream(file)));
		   } catch (Exception ex) {
		   		ex.printStackTrace();
		   }
		   return sw.toString();
	}

	FrermarkerXml(){
		init();
		initData();
	}

	public static void main(String[] args) {
		  System.out.println(new FrermarkerXml().toString());
		 }
}