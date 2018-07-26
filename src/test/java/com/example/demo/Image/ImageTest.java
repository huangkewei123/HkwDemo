package com.example.demo.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTest {

    /**
     * 把图片印刷到图片上
     *
     * @param pressImg1
     *            -- 原图
     * @param logo
     *            -- 水印
     * @param targetImg
     *            -- 目标文件
     * @param x
     *            --水印x坐标
     * @param y
     *            --水印y坐标
     * @return
     * @return
     */
    public static void pressImage(String pressImg1,String logo, String targetImg,int x, int y) {
        try {
            File file1 = new File(pressImg1);
            BufferedImage image = pressImage(ImageIO.read(file1),ImageIO.read(new File(logo)),x,y);
//        	BufferedImage image = pressOnRightBottom(ImageIO.read(file1),ImageIO.read(new File(logo)));
            ImageIO.write(image,file1.getName().replaceAll("^[^\\.]+\\.", "") ,new File(targetImg)) ;//ImageIO.write(image, "jpg",new File(targetImg)) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 把图片印刷到图片上
     *
     * @param pressImg1
     *            -- 水印文件
     * @param pressImg2
     *            -- 目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     * @return
     * @return
     */
    public final static BufferedImage pressImage(BufferedImage pressImg1,BufferedImage pressImg2,
                                                 int x, int y) {
        try {
            //目标文件
            int wideth = pressImg1.getWidth();
            int height = pressImg1.getHeight();

            Graphics g =pressImg1.getGraphics();
            if(g==null){
                pressImg1.createGraphics();
            }else{
                g.drawImage(pressImg1, 0, 0, wideth, height, null);
            }

            //水印文件
            int wideth_biao = pressImg2.getWidth(null);//获得水印图真实宽度,无图返回-1
            int height_biao = pressImg2.getHeight(null);//获得水印图真实高度,无图返回-1

            x=x>0?x:(x+wideth-wideth_biao);
            y=y>0?y:(y+height-height_biao);

            g.drawImage(pressImg2, x,  y, wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pressImg1;
    }

    public static void main(String[] args) throws Exception {

        //覆盖到目标上
        pressImage("E:/testImage/145046aqr858s8o8ob8uvr.png","E:/testImage/123.png","E:/0003.jpg",
                835,1933);

    }
}
