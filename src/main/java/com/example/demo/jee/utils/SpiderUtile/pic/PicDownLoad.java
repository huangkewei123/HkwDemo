package com.example.demo.jee.utils.SpiderUtile.pic;

import com.example.demo.jee.constants.SysConstants;
import com.example.demo.jee.utils.ThreadCoinfguration.CallableDownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PicDownLoad {
    /***
     * 下载图片
     *
     * @param listImgSrc
     * @param path
     */
    public static void download(List<String> listImgSrc , String path) {
        try {
            for (String url : listImgSrc) {
                downloadAction(url , path);
            }
        } catch (Exception e) {
            System.out.println("下载失败");
            e.getStackTrace();
        }
    }

    /***
     * 下载图片多线程实现
     *
     * @param listImgSrc 地址集合
     * @param path 存储地址
     */
    public static void downloadByThreed(List<String> listImgSrc , String path ) {
        try {
            /*
            遍历地址集合，将地址分配到多个map中，启动线程爬取map
             */
            List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

            //查看是否有文件夹
            judeDirExists(new File(path));

            ExecutorService service = SysConstants.THREAD_POOL;
//            ExecutorService service = Executors.newFixedThreadPool(10);
            List<Future<String>> tasks = new ArrayList<>();
            for (final String url : listImgSrc) {
                tasks.add(
                    service.submit(new CallableDownload( url , path))
                );
            }

            for (Future<String> future : tasks) {
                try {
                    System.out.println(future.get() + ",线程完成！");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
//            service.shutdown();
        } catch (Exception e) {
            System.out.println("下载失败");
            e.getStackTrace();
        }
    }

    /**
     * 正式启动下载，下载某一个地址下的数据
     * @param url   被下载的地址
     * @param path  下载的文件存储的本地地址
     * @throws IOException
     */
    public static void downloadAction(String url, String path) throws IOException {
        String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
        URL uri = new URL(url);
        InputStream in = uri.openStream();

        File file = new File(path + imageName);
        System.out.println(path + imageName);
        judeFileExists(file);
        //文件大小估值
        int fileAvailable = in.available();
        //如果文件大小估值与本地文件大小一致，则跳过此文件的下载


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
    }

    // 判断文件是否存在
    public static void judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
        } else {
            System.out.println("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }


}
