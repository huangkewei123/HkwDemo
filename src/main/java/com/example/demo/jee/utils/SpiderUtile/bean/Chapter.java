package com.example.demo.jee.utils.SpiderUtile.bean;

public class Chapter {
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){

        return "标题=" + title + ",url=" + url;
    }
}
