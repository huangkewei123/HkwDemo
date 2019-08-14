package com.example.demo.jee.utils;

import com.example.demo.jee.constants.SysConstants;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlReader {

    private static final Map<NovelSiteEnum , Map<String , String>> CONTEXT_MAP = new HashMap<NovelSiteEnum , Map<String ,String >>();

    static {
        loadConfig();
    }

    private XmlReader() {}

    /**
     * 加载配置文件，获取网站对应的解析元素，封装至map中
     */
    private static void loadConfig(){
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
                    subMap.put(name , text);
                    System.out.println(name +"       " + text);
                }
                if(StringUtils.isEmpty(subMap.get("url")))
                    continue;
                CONTEXT_MAP.put(NovelSiteEnum.getEnumByUrl(subMap.get("url")) , subMap);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拿到对应网站的解析规则
     */
    public static Map<String, String> getContext(NovelSiteEnum novelSiteEnum) {
        return CONTEXT_MAP.get(novelSiteEnum);
    }

}
