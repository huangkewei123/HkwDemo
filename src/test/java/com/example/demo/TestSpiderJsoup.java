package com.example.demo;


import com.example.demo.jee.constants.SysConstants;
import com.example.demo.jee.utils.NovelSiteEnum;
import com.example.demo.jee.utils.SpiderUtile.SpiderJsoupUtil;
import com.example.demo.jee.utils.SpiderUtile.bean.Chapter;
import com.example.demo.jee.utils.SpiderUtile.pic.PicDownLoad;
import com.example.demo.jee.utils.ThreadCoinfguration.ThreadConfiguration;
import com.example.demo.jee.utils.XmlReader;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.jsoup.nodes.Document;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestSpiderJsoup {
    @Test
    public void testGetChapters() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        String url = "http://zapet.cn/chapter/25003";
        String result = util.crawl(url);
        Document doc = Jsoup.parse(result);
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        String path = util.getTitlePath(doc,schamel);
        List<Chapter> list = util.getsChapter(doc , schamel);
        for (Chapter chapter : list){
            System.out.println(chapter);
        }
    }

    @Test
    public void testGetHtml() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        String url = "http://zapet.cn/chapter/25003";
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        List<String> list = util.getHtmlDetailsStr(url , schamel);

        PicDownLoad.downloadByThreed(list , "F:\\spiderDownload\\全职看护\\"  );
    }

    @Test
    public void testCreateTxt(){
        PicDownLoad.judeFileExists(new File("F:\\spiderDownload\\全职看护\\te.txt"));
    }

    @Test
    public void testDownLoad() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        String url = "http://zapet.cn/book/500";
        String result = util.crawl(url);
        Document doc = Jsoup.parse(result);
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        String path = util.getTitlePath(doc,schamel);
        List<Chapter> list = util.getsChapter(doc , schamel);

        for (Chapter chapter : list){
            List<String> detailsStrlist = util.getHtmlDetailsStr(chapter.getUrl() , schamel);
            PicDownLoad.downloadByThreed(detailsStrlist , path );
        }
    }

    @Test
    public void testDownLoadDetail() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        String url = "http://zapet.cn/book/500";
        String htmlResult = util.crawl(url);
        Document doc = Jsoup.parse(htmlResult);
        //获取配置文件中的所有的解析规则
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        //解析规则中有存储地址，创建存储目录，然后返回document对象
        String path = util.getTitlePath(doc,schamel);
        System.out.println(path);
        //获取列表页
        List<Chapter> list = util.getsChapter(doc, schamel);

        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future<String>> tasks = new ArrayList<Future<String>>();
        for (Chapter chapter : list){
            //获取详情页的地址集合
            List<String> detailsStrlist = util.getHtmlDetailsStr(chapter.getUrl() , schamel);
            //地址集合
            tasks.add(
                    service.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            PicDownLoad.downloadByThreed(detailsStrlist , path );
                            return chapter.toString();
                        }
                    })
            );
        }

        for (Future<String> result : tasks){
            System.out.println(result.get() +"执行成功");
        }
        service.shutdown();
    }

    @Test
    public void testSAXreader(){
        XmlReader.getContext(NovelSiteEnum.getEnumByUrl("http://zapet.cn/chapter/25003"));
    }
}
