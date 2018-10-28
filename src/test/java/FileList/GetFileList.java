package FileList;
import com.example.demo.pdf.PdfReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetFileList {
    private static int fileNum = 0;
    private static int errorFileNum = 0;
    static String folderName = null;
    static List<String> list = new ArrayList<String>();


    public static void getFilePath(String path){
        File file=new File(path);
        File[] tempList = file.listFiles();
        //System.out.println("该目录下对象个数："+tempList.length);

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                //System.out.println("文     件："+tempList[i]);
                String filename = tempList[i].getName();
                if(filename.endsWith("pdf")){
                    fileNum = fileNum + 1;  //计数
                    //调用PDF读取
                    //System.out.println("文件夹名称"+folderName);
                    //System.out.println("文件地址"+tempList[i].getPath());
                    String content = PdfReader.getPdfContent(tempList[i].getPath());
                    //判断文件夹名称在文件中是否存在
                    boolean isOk = content.replaceAll(" ","").contains(folderName);
                    if(!isOk){
                        //System.out.println("---------------------文件不匹配:" + tempList[i]);
                        list.add(tempList[i].getPath());
                        errorFileNum = errorFileNum + 1;//错误文件计数
                    }
                }

            }
            if (tempList[i].isDirectory()) {
                //System.out.println("文件夹："+tempList[i]);
                folderName = tempList[i].getName();
                //System.out.println("载入文件夹名称" + folderName);
                getFilePath(tempList[i].getPath());
            }
        }
    }

    public static void main(String[] args) {
        String path="E:\\case";
        getFilePath(path);
        System.out.println(fileNum);
        System.out.println(errorFileNum);
        for (int i = 0 ; i < list.size() ; i++){
            System.out.println(list.get(i));
        }
    }
}
