package com.example.demo.jee.utils.SpiderUtile;

import com.example.demo.jee.utils.SpiderUtile.pic.PicDownLoad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

    @Override
    public void run() {
        try {
            String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
            URL uri = new URL(url);
            InputStream in = uri.openStream();
            File file = new File(path + imageName);
            System.out.println(path + imageName);
            PicDownLoad.judeFileExists(file);
            FileOutputStream fo = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int length = 0;
            System.out.println("开始下载:" + url);
            while ((length = in.read(buf, 0, buf.length)) != -1) {
                fo.write(buf, 0, length);
            }
            in.close();
            fo.close();
            System.out.println(imageName + "下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super .run();
    }
}
