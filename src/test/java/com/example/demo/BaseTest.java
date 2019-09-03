package com.example.demo;

import com.example.demo.jee.utils.http.HttpHelper;
import com.example.demo.jee.utils.http.RequestType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class BaseTest {

    @Test
    public void testHttp(){
        HttpHelper.doCommHttp(RequestType.GET , "http://www.baidu.com" , null);
    }
}
