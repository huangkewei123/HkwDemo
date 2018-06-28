package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/redis/set")
    public Object redisSet(@RequestParam("value") String value){
        JSONObject json = new JSONObject();

        List<Person> list2 = personService.findAll();

        Person person2 = personService.findPersonById(1);
        json.put("person2" , JSONObject.toJSONString(person2));
        json.put("list2" , JSONObject.toJSONString(list2));

        System.out.print(person2.getName());
        System.out.print(person2.getName());
        Person person = new Person();
        person.setId(1);
        person.setName("huangkewei");
        personService.update(person);

        List<Person> list = personService.findAll();

        Person person1 = personService.findPersonById(1);

        json.put("person1" , JSONObject.toJSONString(person1));
        json.put("list" , JSONObject.toJSONString(list));

        return json;
    }

    @RequestMapping("/redis/delete")
    public Object delete(Integer id){
        personService.delete(id);

        return JSONObject.toJSON(personService.findAll());
    }

    @RequestMapping("/findById")
    public Object findById(Integer id){
        return personService.findPersonById(id);
    }
}
