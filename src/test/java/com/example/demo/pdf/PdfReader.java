package com.example.demo.pdf;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PdfReader {

    public static String getPdfContent(String filePath){
        PDDocument document = null;
        try {
            //pdfbox提取pdf
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
        String str = getPdfContent("E:\\case\\2018-10-28\\(2018)湘0223民初17950号\\传票-存根-被告.pdf");
        //System.out.println(str);
        boolean yes = str.replaceAll(" ","").contains("(2018)湘0223民初17950号");

        System.out.println(yes);
        //System.out.println("Text:" + str);
    }

}
