package com.example.demo.jee.utils.SpiderUtile;


public class PicThread extends Thread {
    private String url;
    private String path;

    /**
     *
     * @param name
     * @param url
     * @param path
     */
    public PicThread(String name , String url ,String path){ // 用构造函数控制线程的名字，如果不写，默认是thread-整数
        super (name);
        this.path = path;
        this.url = url;
    }

}
