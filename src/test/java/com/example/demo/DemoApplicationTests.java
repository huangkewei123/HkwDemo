package com.example.demo;

import com.example.demo.manipulation.entity.base.Person;
import com.example.demo.manipulation.service.base.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebAppConfiguration
@Configuration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
// 加入 AutoConfigureTestDatabase 注解
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DemoApplicationTests {

    /**
     * Interface to provide configuration for a web application.
     */
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;


//    @Autowired
//    PersonService personService;

    /*@Autowired
    private StringRedisTemplate stringRedisTemplate;*/

//    @Test
//    public void findAll(){
//        List<Person> personList = personService.findAll();
//        System.out.print(personList);
//        /*List<String> list =new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("v");
//        stringRedisTemplate.opsForValue().set("abc", "测试");
//        stringRedisTemplate.opsForList().leftPushAll("qq",list); // 向redis存入List
//        stringRedisTemplate.opsForList().range("qwe",0,-1).forEach(value ->{
//            System.out.println(value);
//        });*/
//    }
//
//    @Test
//    public void insert(){
//        Person person = new Person();
//        person.setAddress("test");
//        person.setAge(4);
//        person.setName("huang");
//        personService.insert(person);
//    }
//
//    @Test
//    public void update(){
//        Person person = new Person();
//        person.setName("212");
//        person.setId(1);
//        personService.update(person);
//    }
//
//    @Test
//    public void findById(){
//        personService.findPersonById(1);
//    }

}
