package com.example.demo.jee.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SysConstants {
    //当前用户session中的key
    public final static String CURRENT_USER = "currentUser";

    //登录成功默认跳转页面
    public final static String DEFAULT_PAGE = "/index";

    //成功码
    public final static String SUCCESS_CODE = "200";
    //失败码
    public final static String FAILED_CODE = "200";

    //网易 vip邮箱相关配置
//    public final static String MY_EMAIL_ACCOUNT = "hnyxrmfy@vip.163.com";//邮箱账号
//    public final static String MY_EMAIL_PASSWORD = "yxfy0731";//邮箱密码或独立密码
//    public final static String MY_EMAIL_SMTPHOST = "smtp.vip.163.com";//邮箱服务器地址

    public final static String MY_EMAIL_ACCOUNT = "huangkewei@yinrenkeji.com";//邮箱账号
    public final static String MY_EMAIL_PASSWORD = "hkw0o522";//邮箱密码或独立密码
    public final static String MY_EMAIL_SMTPHOST = "smtp.ym.163.com";//邮箱服务器地址

    //漫画存放地址
    public final static String CARTOON_PATH = Configuration.getInstance().getValue("cartoon_path");
    //ftp存储地址
    public static final String SAVEPATHPDF = Configuration.getInstance().getValue("savePathPDF");

}
