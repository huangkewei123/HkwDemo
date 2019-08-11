package com.example.demo.jee.utils.ThreadCoinfguration;

import java.io.Serializable;

public class ThreadConfiguration implements Serializable {
    /*
    每个线程下载的最大章节数
     */
    public static final int DEFAUT_SIZE = 100;

    public  ThreadConfiguration(){
        this.size = DEFAUT_SIZE;
    }
    /*

     */
    private String path;

    private int size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
