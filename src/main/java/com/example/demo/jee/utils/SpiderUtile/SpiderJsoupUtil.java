package com.example.demo.jee.utils.SpiderUtile;

import com.example.demo.jee.utils.SpiderUtile.bean.Chapter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiderJsoupUtil {
    /**
     * 连接url获取返回的页面
     * @param url
     * @return
     * @throws Exception
     */
    public String crawl(String url) throws Exception {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取表单的dom元素，解析出url和标题放入list
     * @param url
     * @return
     * @throws Exception
     */
    public List<Chapter> getsChapter(String url , Map<String , String > schamel) throws Exception{
        try {
            String result = crawl(url);
            Document doc = Jsoup.parse(result);
            Elements elements = doc.select(schamel.get("detail-attr-url-site"));
            List<Chapter> list = new ArrayList<>();
            for (Element a : elements){
                Chapter chapter = new Chapter();
                chapter.setTitle(a.text());
                chapter.setUrl(schamel.get("url") + a.attr(schamel.get("img-href-site")));
                list.add(chapter);
            }
            return list;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 计算所需线程数量
     * @param list
     * @return
     */
    public Map threadRec(List<Chapter> list){
        //获取总共章节数量，然后根据计算方法放入多个集合中返回
        int threadCount = list.size();
        Map<String,List<Chapter>> map = new HashMap<String , List<Chapter>>();


        return map;
    }

    /**
     * 获取表单的dom元素，解析出图片
     * @param url
     * @return
     * @throws Exception
     */
    public List<Chapter> getHtmlDetailsChapter(String url , Map<String , String > schamel) throws Exception{
        try {
            String result = crawl(url);
            Document doc = Jsoup.parse(result);
            Elements elements = doc.select(schamel.get("img-url-selector-site"));
            List<Chapter> list = new ArrayList<Chapter>();
            for (Element a : elements){
                Chapter chapter = new Chapter();
                chapter.setTitle(a.text());
                chapter.setUrl(a.attr(schamel.get("img-data-site")));
                list.add(chapter);
            }
            return list;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取表单的dom元素，解析出图片
     * @param url
     * @return
     * @throws Exception
     */
    public List<String> getHtmlDetailsStr(String url , Map<String , String > schamel) throws Exception{
        try {
            String result = crawl(url);
            Document doc = Jsoup.parse(result);
            Elements elements = doc.select(schamel.get("img-url-selector-site"));
            List<String> list = new ArrayList<String>();
            for (Element a : elements){
                list.add(a.attr(schamel.get("img-data-site")));
            }
            return list;
        } catch (Exception e) {
            throw e;
        }
    }
}
