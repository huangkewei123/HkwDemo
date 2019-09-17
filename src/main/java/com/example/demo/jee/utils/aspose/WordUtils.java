/**
 *
 */
package com.example.demo.jee.utils.aspose;

import com.aspose.words.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.*;


/**
 * @author
 * @Name WordUtils
 * @date 2017年7月6日 上午11:51:11
 */
public class WordUtils {

    private static Logger logger = LoggerFactory.getLogger(WordUtils.class);

    static {
        InputStream is = WordUtils.class.getClassLoader().getResourceAsStream("Aspose.Words.xml");
        License aposeLic = new License();
        try {
            aposeLic.setLicense(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WordUtils() {
        throw new AssertionError();
    }

    /**
     * @param path
     * @return
     * @throws IOException
     * @throws
     * @Description: 图片转base64code
     * @author: shiliu
     * @date:2018年7月31日 下午3:19:01
     */
    public static String imgConvertBaseCode(String path) throws IOException {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null)
                in.close();
        }
        return data != null ? Base64.getEncoder().encodeToString(data) : "";
    }

    /**
     * 根据模板 输出pdf文件
     *
     * @param tempPath   模板路径
     * @param data       替换数据
     * @param outPDFPath pdf文件路径
     * @throws Exception
     */
    public static List<String> WordToPDFFileAspose(String tempPath, Map<String, Object> data, String outPDFPath) throws Exception {
        return WordToFileAspose(tempPath, data, outPDFPath, SaveFormat.PDF);
    }

    /**
     * 根据模板 输出指定类型的文件
     *
     * @param tempPath   模板路径
     * @param data       替换数据
     * @param outPDFPath pdf文件路径
     * @param fileType   文件输出类型   SaveFormat.PDF SaveFormat.DOC SaveFormat.JPEG 等常量
     * @throws Exception
     */
    public static List<String> WordToFileAspose(String tempPath, Map<String, Object> data, String outPDFPath, int fileType) throws Exception {
        Document doc = new Document(tempPath);//读取模板
        List<String> list = new ArrayList<String>();
        FindReplaceOptions options = new FindReplaceOptions();
        for (String k : data.keySet()) {
            doc.getRange().replace("${" + k + "}", data.get(k) != null ? data.get(k).toString() : "", options);
        }
        if (fileType == SaveFormat.JPEG) {
            String stringBuffer = outPDFPath.substring(0, outPDFPath.indexOf("."));
            ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
            iso.setResolution(200);
            for (int i = 0; i < doc.getPageCount(); i++) {
                iso.setPageIndex(i);
                doc.save(stringBuffer + "_" + (i + 1) + ".jpg", iso);
                list.add(stringBuffer + "_" + (i + 1) + ".jpg");
            }
        } else {
            doc.save(outPDFPath, fileType);
            list.add(outPDFPath);
        }
        return list;
    }

    /**
     * 根据模板 输出map（ i.后缀名，字节数组）
     *
     * @param in       模板文件流
     * @param data     替换数据
     * @param fileType 文件输出类型   SaveFormat.PDF SaveFormat.DOC SaveFormat.JPEG 等常量
     * @throws Exception
     */
    public static synchronized Map<Integer, byte[]> getBytesAspose(InputStream in, Map<String, Object> data, int fileType) throws Exception {
        Document doc = new Document(in);//读取模板
        Map<Integer, byte[]> map = new HashMap<>();
        FindReplaceOptions options = new FindReplaceOptions();
        for (String k : data.keySet()) {
            doc.getRange().replace("${" + k + "}", data.get(k) != null ? data.get(k).toString() : "", options);
        }
        getBytesMapByType(fileType, doc, map);
        return map;
    }

    /**
     * 根据模板生成pdf文件 输出指定类型的文件的输出流
     *
     * @param tempPath 模板路径
     * @param data     替换的文本内容
     * @param fileType 文件输出类型   SaveFormat.PDF SaveFormat.DOC 等常量
     * @throws Exception
     */
    public static InputStream getInputStreamWordAspose(String tempPath, Map<String, Object> data, int fileType) throws Exception {
        Document doc = new Document(tempPath);//读取模板
        return getInputStreamAspose(data, fileType, doc);
    }

    /**
     * 根据模板生成pdf文件 输出指定类型的文件的输出流
     *
     * @param in       模板流
     * @param data     替换的文本内容
     * @param fileType 文件输出类型   SaveFormat.PDF SaveFormat.DOC 等常量
     * @throws Exception
     */
    public static InputStream getInputStreamWordAspose(InputStream in, Map<String, Object> data, int fileType) throws Exception {
        Document doc = new Document(in);//读取模板
        return getInputStreamAspose(data, fileType, doc);
    }

    private static InputStream getInputStreamAspose(Map<String, Object> data, int fileType, Document doc) throws IOException {
        ByteArrayOutputStream putStream = new ByteArrayOutputStream();
        try {
            FindReplaceOptions options = new FindReplaceOptions();
            for (String k : data.keySet()) {
                doc.getRange().replace("${" + k + "}", data.get(k) != null ? data.get(k).toString() : "", options);
            }
            doc.save(putStream, fileType);
            return (new ByteArrayInputStream(putStream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WordAspose；  案号：" + data.get("caseNo").toString() + "文件生成异常");
            throw new RuntimeException(e);
        } finally {
            if (putStream == null) {
                putStream.close();
            }
        }
    }

    /**
     * 根据模板生成文件（PDF ，DOC）
     *
     * @param template 模板输入流
     * @param data     替换的文本内容
     * @param fileType 文件输出类型   SaveFormat.PDF SaveFormat.DOC 等常量
     * @param os       文件输出
     * @throws Exception
     */
    public static void WordAspose(InputStream template, Map<String, Object> data, int fileType, OutputStream os) throws Exception {
        try {
            Document doc = new Document(template);//读取模板
            FindReplaceOptions options = new FindReplaceOptions();
            for (String k : data.keySet()) {
                doc.getRange().replace("${" + k + "}", data.get(k) != null ? data.get(k).toString() : "", options);
            }
            doc.save(os, fileType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (template != null) {
                template.close();
            }
        }
    }

    /**
     * 替换文本（PDF ，DOC）
     * @param template 模板输入流
     * @param data     替换的文本内容
     * @param fileType 文件输出类型   SaveFormat.PDF SaveFormat.DOC 等常量
     * @param os       文件输出
     * @throws Exception
     */
    public static void wordReplace(InputStream template, Map<String, Object> data, int fileType, OutputStream os) throws Exception {
        try {
            Document doc = new Document(template);//读取模板
            FindReplaceOptions options = new FindReplaceOptions();
            for (String k : data.keySet()) {
                doc.getRange().replace(k, data.get(k) != null ? data.get(k).toString() : "", options);
            }
            doc.save(os, fileType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (template != null) {
                template.close();
            }
        }
    }



    /**
     * word转pdf
     *
     * @param template 模板输入流
     * @param os       文件输出
     * @throws Exception
     */
    public static void wordTransformPDF(InputStream template, OutputStream os) throws Exception {
        try {
            Document doc = new Document(template);//读取模板
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (template != null) {
                template.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * pdf加水印
     *
     * @param fromFilePath（源pdf文件路径）
     * @param waterFilePath（水印文件）
     * @param v4（坐标）
     * @param v5（坐标）
     * @param pageNum（需要加水印的页码）
     * @param toFilePath（输出pdf文件路径）
     * @throws Exception
     */
    public static void pdfWaterMark(String fromFilePath, String waterFilePath, float v4, float v5, int pageNum,
                                    String toFilePath) throws Exception {
        ByteArrayOutputStream putStream = new ByteArrayOutputStream();
        try {
            PdfReader reader = new PdfReader(new FileInputStream(new File(fromFilePath)));//指定将和 图片拼接的 PDF
            PdfStamper stamper = new PdfStamper(reader, putStream);//生成的PDF 路径
            PdfContentByte overContent = stamper.getOverContent(pageNum);
            Image image = Image.getInstance(waterFilePath);//图片名称
            image.setAbsolutePosition(v4, v5);//左边距、底边距
            overContent.addImage(image);
            overContent.stroke();
            stamper.close();
            reader.close();
            if (stamper != null) stamper.close();
            if (reader != null) reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (putStream == null) {
                putStream.close();
            }
        }
        org.apache.commons.io.FileUtils.copyInputStreamToFile(new ByteArrayInputStream(putStream.toByteArray()),
                new File(toFilePath));
    }

    /**
     * word插入图片（加水印）
     *
     * @param fromFilePath（源doc文件路径）
     * @param toFilePath（目标doc文件路径）
     * @param waterFilePath（水印文件路径）
     * @param top（坐标）
     * @param left（坐标）
     * @throws Exception
     */
    public static void wordWaterMark(String fromFilePath, String toFilePath, String waterFilePath, double top, double left)
            throws Exception {
        InputStream is = new FileInputStream(new File(fromFilePath));
        OutputStream os = new FileOutputStream(new File(toFilePath));
        InputStream waterFileIs = new FileInputStream(new File(waterFilePath));
        try {
            Document doc = new Document(is);//读取模板
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.moveToDocumentEnd();
            Shape shape = builder.insertImage(waterFileIs);
            shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
            shape.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
            shape.setWrapType(WrapType.NONE);
            shape.setTop(top);
            shape.setLeft(left);
            doc.save(os, SaveFormat.DOC);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (waterFileIs != null) {
                waterFileIs.close();
            }
        }
    }

    /**
     * 在文件中插入html内容
     *
     * @param html
     * @param path
     * @param outPath
     * @throws Exception
     */
    public static void insertHtml(String html, String path, String outPath) throws Exception {
        try (OutputStream out = new FileOutputStream(outPath)) {
            Document doc = new Document(path);//读取模板
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.moveToDocumentEnd();
            builder.insertBreak(BreakType.PAGE_BREAK);
            builder.insertHtml(html);
            doc.save(out, SaveOptions.createSaveOptions(SaveFormat.DOC));//生成doc文件
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据word文件中的图片进行替换
     *
     * @param in
     * @param data
     * @param fileType
     * @param img
     * @return
     * @throws Exception
     */
    public static synchronized Map<Integer, byte[]> getBytesReplaceTextAndImg(InputStream in, Map<String, Object> data, int fileType, String[] imgPath) throws Exception {
        Document doc = new Document(in);//读取模板
        Map<Integer, byte[]> map = new HashMap<>();
        FindReplaceOptions options = new FindReplaceOptions();
        for (String k : data.keySet()) {
            doc.getRange().replace("${" + k + "}", data.get(k) != null ? data.get(k).toString() : "", options);
        }
        NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);// 查询文档中所有wmf图片
        Node[] shapes = shapeCollection.toArray();// 序列化
        DocumentBuilder builder = new DocumentBuilder(doc);// 新建文档节点
        for (int i = 0; i < shapes.length; i++) {
            if (imgPath.length > i && imgPath[i] != null) {
                Shape shape = (Shape) shapes[i];
                builder.moveTo(shape);// 移动到图片位置
                builder.insertImage(imgPath[i]);
                shape.remove();// 移除图形
            }
        }
        getBytesMapByType(fileType, doc, map);
        return map;
    }

    /**
     * 在word 文档最后插入图片 并且每张图片会占据word一页
     *
     * @param in
     * @param data
     * @param fileType
     * @param img
     * @return
     * @throws Exception
     */
    public static synchronized byte[] getBytesAsposeInsertImg(InputStream in,  List<String> imgPath) throws Exception {
        try (ByteArrayOutputStream putStream = new ByteArrayOutputStream()) {
            Document doc = new Document(in);//读取模板
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.moveToDocumentEnd();
            Shape shape;
            builder.insertBreak(BreakType.PAGE_BREAK);
            for (String str : imgPath) {
                shape = builder.insertImage(str, 595, 842);
                shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
                shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
                shape.setWrapType(WrapType.TOP_BOTTOM);
                builder.insertBreak(BreakType.PAGE_BREAK);
            }
            doc.save(putStream, SaveOptions.createSaveOptions(SaveFormat.DOC));//生成doc文件
            return putStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据类型选择文件格式
     *
     * @param fileType
     * @param doc
     * @param map
     * @throws Exception
     */
    private static void getBytesMapByType(int fileType, Document doc, Map<Integer, byte[]> map) throws Exception {
        ByteArrayOutputStream putStream = null;
        try {
            if (fileType == SaveFormat.JPEG) {
                ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
//            iso.setResolution(200);
                for (int i = 0; i < doc.getPageCount(); i++) {
                    putStream = new ByteArrayOutputStream();
                    iso.setPageIndex(i);
                    doc.save(putStream, iso);
                    map.put((i + 1), putStream.toByteArray());
                    if (putStream != null) {
                        putStream.close();
                    }
                }
            } else {
                putStream = new ByteArrayOutputStream();
                doc.save(putStream, fileType);
                map.put(0, putStream.toByteArray());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (putStream != null) {
                putStream.close();
            }

        }
    }


    /**
     *将字节数组写入文件
     * @param bytes
     * @param targerFilePath
     * @param fileName
     * @return
     */
    public static String writerBytesToFile(byte[] bytes,String targerFilePath,String fileName){
        if(bytes==null)return null;
        File file=new File(targerFilePath);
        if(!file.exists())file.mkdirs();
        OutputStream out=null;
        InputStream is=null;
        try  {
            out = new FileOutputStream(targerFilePath+fileName);
            is= new ByteArrayInputStream(bytes);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(is!=null)is.close();
                if(out!=null)out.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return targerFilePath+fileName;
    }



    public static void main(String[] args) throws Exception {
           // wordWaterMark("c:/test/庭审笔录1.doc", "c:/test/庭审笔录.doc",
             //  "C:\\Users\\Administrator\\Desktop\\签名集\\签名集\\王玉莲.png", 700, 150);

        WordUtils.pdfWaterMark("C:\\Users\\Administrator\\Desktop\\1.pdf","C:\\Users\\Administrator\\Desktop\\签名集\\签名集\\陈辉满.png",200,340,1, "C:\\Users\\Administrator\\Desktop\\1.pdf");


    }

}
