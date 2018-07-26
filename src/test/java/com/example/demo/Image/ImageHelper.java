package com.example.demo.Image;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
 
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
 
public class ImageHelper {
	
	
	/*
	 * 根据尺寸图片居中裁剪
	 */
	 public static void cutCenterImage(String src,String dest,int w,int h) throws IOException{ 
		 Iterator<?> iterator = ImageIO.getImageReadersByFormatName("jpg"); 
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);  
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, "jpg", new File(dest));           
  
	 }
	 
	/*
	 * 图片裁剪二分之一
	 */
	 public static void cutHalfImage(String src,String dest) throws IOException{ 
		 Iterator<?> iterator = ImageIO.getImageReadersByFormatName("jpg"); //getImageReadersByFormatName("jpg")
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         int width = reader.getWidth(imageIndex)/2; 
         int height = reader.getHeight(imageIndex)/2; 
         Rectangle rect = new Rectangle(width/2, height/2, width, height); 
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, "jpg", new File(dest));   
	 }
	 
	/*
	 * 图片裁剪
	 */
    public static BufferedImage cutImage(InputStream target,int x,int y,int w,int h) throws IOException{ 
    	   Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");//getImageReadersByFormatName("jpg")
           ImageReader reader = iterator.next();
           ImageInputStream iis = ImageIO.createImageInputStream(target); 
           reader.setInput(iis, true); 
           ImageReadParam param = reader.getDefaultReadParam(); 
           Rectangle rect = new Rectangle(x,y,w,h);  
           param.setSourceRegion(rect);
           BufferedImage bi = reader.read(0,param);
           return bi;
    }
 
    /*
     * 图片缩放320*320 240*192 560*400 120*96
     */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {
		double wr=0,hr=0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bufImg = ImageIO.read(srcFile);
		Image itemp = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		wr=w*1.0/bufImg.getWidth();
		hr=h*1.0/bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    /*
     * 图片缩放
     */
    public static BufferedImage zoomImage(BufferedImage bufImg,int w,int h){
		double wr=0,hr=0;
		bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		wr=w*1.0/bufImg.getWidth();
		hr=h*1.0/bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		bufImg = ato.filter(bufImg, null);
		return bufImg;
	}
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
	 * @param targetImg
	 *            -- 水印文件
	 * @param logoImg
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
    
    public final static BufferedImage pressOnRightBottom(BufferedImage pressTo,BufferedImage logoImage){
    	
    	//目标文件
        int wideth = pressTo.getWidth();
        int height = pressTo.getHeight();
 
        //水印文件
        int wideth_biao = logoImage.getWidth();
        int height_biao = logoImage.getHeight();
        
    	return pressImage(pressTo,logoImage,(int)((wideth-wideth_biao)*0.95),(int)((height-height_biao)*0.95));
    }
    
    public static void main(String[] args) throws Exception {
 
		 //覆盖到目标上
		 pressImage("E:/testImage/145046aqr858s8o8ob8uvr.png","E:/testImage/123.png","E:/0003.jpg",
				 	835,1933);
 
	}
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
         }
     
        // This code ensures that all the pixels in the image are loaded
         image = new ImageIcon(image).getImage();
     
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);
     
        // Create a buffered image with a format that's compatible with the screen
         BufferedImage bimage = null;
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
 
            int transparency = Transparency.OPAQUE;
 
     
             GraphicsDevice gs = ge.getDefaultScreenDevice();
             GraphicsConfiguration gc = gs.getDefaultConfiguration();
             bimage = gc.createCompatibleImage(
                 image.getWidth(null), image.getHeight(null), transparency);
         } catch (HeadlessException e) {
         }
     
        if (bimage == null) {
 
            int type = BufferedImage.TYPE_INT_RGB;
 
             bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
         }
     
 
         Graphics g = bimage.createGraphics();
 
         g.drawImage(image, 0, 0, null);
         g.dispose();
     
        return bimage;
     }     
    
}