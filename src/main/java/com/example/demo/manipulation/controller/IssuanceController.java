package com.example.demo.manipulation.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Bytes;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;

@Controller
@RequestMapping("/issuance")
public class IssuanceController {

    /*
    传参{username:xxx,password:xxx}
    返回格式{code:200或500,msg:xxx,data:xxx}*/


    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject sign(String username, String password){
        System.out.println(username);
        System.out.println(password);
        JSONObject json = new JSONObject();
        json.put("code" , 200);
        json.put("msg" , "校验通过");
        json.put("data" , null);
        return json;
    }

    /*
    那个图片上传的格式为{username:xxx,file:xxx}*/
    @RequestMapping(value = "/uploadIssPic" , method = RequestMethod.GET)
    @ResponseBody
    public JSONObject uploadIssPic(String userName , MultipartFile multiFile){
        System.out.println(userName);
        System.out.println(multiFile.getName());
        try {
            byte[] b = multiFile.getBytes();
            try{
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File("e://pis.png"));//打开输入流
                imageOutput.write(b, 0, b.length);//将byte写入硬盘
                imageOutput.close();
                System.out.println("Make Picture success,Please find image in e://pis.png" );
            } catch(IOException ex) {
                System.out.println("Exception: " + ex);
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.put("code" , 200);
        json.put("msg" , "上传成功");
        json.put("data" , null);
        return json;
    }
}
