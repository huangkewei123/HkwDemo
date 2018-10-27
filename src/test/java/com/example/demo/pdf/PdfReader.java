package com.example.demo.pdf;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PdfReader {

    public static String getPdfContent(String filePath){
        try {
            //pdfbox提取pdf
            PDDocument document = null;
            document = PDDocument.load(new File(filePath));
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();
                String st = Tstripper.getText(document);
                //System.out.println("Text:" + st);
                return st;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "无提取数据";
    }

    public static void main(String[] args){
        String str = getPdfContent("D:\\PDF\\Jquery.pdf");

        boolean yes = str.contains("等双目操作符的前后加空格");

        System.out.println(yes);
        //System.out.println("Text:" + str);
    }

}
