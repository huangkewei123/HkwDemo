package com.example.demo;


import com.example.demo.jee.utils.SpiderUtile.SpiderJsoupUtil;
import com.example.demo.jee.utils.SpiderUtile.bean.Chapter;
import com.example.demo.jee.utils.SpiderUtile.pic.PicDownLoad;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TestSpiderJsoup {
    @Test
    public void testGetChapters() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        List<Chapter> list = util.getsChapter("http://zapet.cn/book/500");
        for (Chapter chapter : list){
            System.out.println(chapter);
        }
    }

    @Test
    public void testGetHtml() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        List<String> list = util.getHtmlDetailsStr("http://zapet.cn/chapter/25003");

        PicDownLoad.downloadByThreed(list , "F:\\spiderDownload\\全职看护\\"  , 4);
    }

    @Test
    public void testCreateTxt(){
        PicDownLoad.judeFileExists(new File("F:\\spiderDownload\\全职看护\\te.txt"));
    }

    @Test
    public void testDownLoad() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        List<Chapter> list = util.getsChapter("http://zapet.cn/book/500");
        for (Chapter chapter : list){
            List<String> detailsStrlist = util.getHtmlDetailsStr(chapter.getUrl());
            PicDownLoad.downloadByThreed(detailsStrlist , "F:\\spiderDownload\\全职看护\\" ,100);
        }
    }

    @Test
    public void testDownLoadDetail() throws Exception{
        SpiderJsoupUtil util = new SpiderJsoupUtil();
        List<Chapter> list = util.getsChapter("http://zapet.cn/book/500");
        for (Chapter chapter : list){
            List<String> detailsStrlist = util.getHtmlDetailsStr(chapter.getUrl());
            PicDownLoad.downloadByThreed(detailsStrlist , "F:\\spiderDownload\\全职看护\\" ,100);
        }
    }
}
