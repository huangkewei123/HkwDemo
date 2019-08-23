package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.manipulation.entity.base.Person;
import com.example.demo.manipulation.service.base.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DemoApplicationTests {

    Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);
    @Resource
    PersonService personService;

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void findAll(){
        List<Person> personList = personService.findAll();
        System.out.print(personList);
        Person person = new Person();
        person.setName("asdfa");
        person.setAddress("打发斯蒂芬");
        JSONObject json = (JSONObject)JSONObject.toJSON(person);
        redisTemplate.opsForValue().set("name" , json);

        logger.info("infoA-------------测试日志");
        logger.warn("infoA-------------测试日志");
        logger.debug("infoA-------------测试日志");
        logger.error("infoA-------------测试日志");

        /*List<String> list =new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("v");
        stringRedisTemplate.opsForValue().set("abc", "测试");
        stringRedisTemplate.opsForList().leftPushAll("qq",list); // 向redis存入List
        stringRedisTemplate.opsForList().range("qwe",0,-1).forEach(value ->{
            System.out.println(value);
        });*/
    }

    @Test
    public void insert(){
        Person person = new Person();
        person.setAddress("test");
        person.setAge(4);
        person.setName("huang");
        personService.insert(person);
    }

    @Test
    public void update(){
        Person person = new Person();
        person.setName("212");
        person.setId(1);
        personService.update(person);
    }

    @Test
    public void findById(){
        personService.findPersonById(1);
    }

}
