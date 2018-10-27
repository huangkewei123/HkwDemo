package com.example.demo.jee.utils.SpiderUtile;

import com.example.demo.jee.utils.LoggerUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpiderXpathUtil {

    private static Logger logger = LoggerFactory.getLogger(SpiderXpathUtil.class);


    /**
     * 创建webclient
     * @return
     * @throws IOException
     */
    private static HtmlPage createWebClient(String url) throws IOException {
        // 创建webclient
        WebClient webClient = new WebClient();
        // 取消 JS 支持
        webClient.getOptions().setJavaScriptEnabled(false);
        // 取消 CSS 支持
        webClient.getOptions().setCssEnabled(false);
        // 获取指定网页实体
        return (HtmlPage) webClient.getPage(url);
    }

    public static void baidu(){
        try {
            // 创建webclient
            HtmlPage page = createWebClient("https://www.baidu.com/");
            // 获取搜索输入框
            HtmlInput input = (HtmlInput) page.getHtmlElementById("kw");
            // 往输入框 “填值”
            input.setValueAttribute("larger5");
            // 获取搜索按钮
            HtmlInput btn = (HtmlInput) page.getHtmlElementById("su");
            // “点击” 搜索
            HtmlPage page2 = btn.click();
            // 选择元素
            List<HtmlElement> spanList= (List<HtmlElement>) page2.getByXPath("//h3[@class='t']/a");
            for(int i=0;i<spanList.size();i++) {
                // 输出新页面的文本
                System.out.println(i+1+"、"+spanList.get(i).asText());
            }
        } catch (IOException e) {
            logger.error("SpiderXpathUtil出现错误" + e.getMessage());
            e.printStackTrace();
        }
    }

    //在以下网站中获取动态代理IP
    public static Map<String,String> getProxyIpByInternet(){
        try {
            HtmlPage page = createWebClient("http://www.66ip.cn/nmtq.php?getnum=200&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=0&proxytype=2&api=66ip");

            // 选择元素
            List<HtmlElement> spanList= (List<HtmlElement>) page.getByXPath("//body");
            //ip和port的map
            Map<String,String > ipportMap = new HashMap<String,String>();
            //第二次循环时，从爬虫中分解出来的ip和port字符串
            String [] ipportSpilit = new String[2];
            for(int i=0;i<spanList.size();i++) {
                // 输出新页面的文本
                //System.out.println(i+1+"、"+spanList.get(i).asText());
                //分解换行符，一个换行符一条数据
                String [] ipPortList = spanList.get(i).asText().split("\r\n");
                System.out.println(ipPortList.length);
                //分解出来的数据中带有":"，所以将其拆出并放入map，key为ip，value为port
                for (int j = 0 ; j < ipPortList.length ; j++){
                    ipportSpilit = ipPortList[j].trim().split(":");
                    ipportMap.put(ipportSpilit[0],ipportSpilit[1]);
                    //System.out.println(ipPortList[j].trim());
                }
            }

            return ipportMap;

        } catch (IOException e) {
            logger.error("SpiderXpathUtil出现错误" + e.getMessage());
            return null;
        }

    }

    public static void main(String[] args) throws Exception {
        Map<String,String> ipportMap = SpiderXpathUtil.getProxyIpByInternet();

        System.out.println("开始--------------------------------------------------");
            int i = 1;
            for (String key : ipportMap.keySet()) {

                System.out.println(i++ + "-=-------key" + key +"value" + ipportMap.get(key));
            }
            System.out.println("结束--------------------------------------------------");
    }

}
