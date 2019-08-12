package com.example.demo.jee.utils.ThreadCoinfguration;

import com.example.demo.jee.utils.SpiderUtile.pic.PicDownLoad;

import java.util.concurrent.Callable;

public class CallableDownload implements Callable<String> {
    private String url;
    private String path;

    public CallableDownload(String url , String path){
        this.url = url;
        this.path = path;
    }

    @Override
    public String call() throws Exception {
        PicDownLoad.downloadAction(url , path);
        return url;
    }
}
