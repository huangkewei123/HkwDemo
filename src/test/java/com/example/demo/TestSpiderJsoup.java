package com.example.demo;


import com.example.demo.jee.utils.NovelSiteEnum;
import com.example.demo.jee.utils.SpiderUtile.SpiderJsoupUtil;
import com.example.demo.jee.utils.SpiderUtile.bean.Chapter;
import com.example.demo.jee.utils.SpiderUtile.pic.PicDownLoad;
import com.example.demo.jee.utils.XmlReader;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TestSpiderJsoup {
    @Test
    public void testGetChapters() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        String url = "http://zapet.cn/chapter/25003";
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        List<Chapter> list = util.getsChapter(url , schamel);
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
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        List<Chapter> list = util.getsChapter(url , schamel);

        for (Chapter chapter : list){
            List<String> detailsStrlist = util.getHtmlDetailsStr(chapter.getUrl() , schamel);
            PicDownLoad.downloadByThreed(detailsStrlist , "F:\\spiderDownload\\全职看护\\" );
        }
    }

    @Test
    public void testDownLoadDetail() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        String url = "http://zapet.cn/book/500";
        Map<String, String> schamel = XmlReader.getContext(NovelSiteEnum.getEnumByUrl(url));
        List<Chapter> list = util.getsChapter(url, schamel);
        for (Chapter chapter : list){
            List<String> detailsStrlist = util.getHtmlDetailsStr(chapter.getUrl() , schamel);
            PicDownLoad.downloadByThreed(detailsStrlist , "F:\\spiderDownload\\全职看护\\" );
        }
    }

    @Test
    public void testSAXreader(){
        XmlReader.getContext(NovelSiteEnum.getEnumByUrl("http://zapet.cn/chapter/25003"));
    }
}
