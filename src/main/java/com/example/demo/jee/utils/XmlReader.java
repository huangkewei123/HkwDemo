package com.example.demo.jee.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlReader {
    public static void loadConfig(){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File("src/main/resources/tagConfig.xml"));
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements){
                List<Element> site = element.elements();
                Map<String, String> subMap = new HashMap<String , String>();
                for (Element sub : site){
                    String name = sub.getName();
                    String text = sub.getTextTrim();
                    System.out.println(name +"       " + text);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }



}
