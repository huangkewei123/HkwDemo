package com.example.demo.manipulation.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.manipulation.entity.base.Menu;
import com.example.demo.manipulation.entity.base.Person;
import com.example.demo.manipulation.entity.base.Role;
import com.example.demo.manipulation.entity.base.User;
import com.example.demo.manipulation.service.base.MenuService;
import com.example.demo.manipulation.service.base.PersonService;
import com.example.demo.manipulation.service.base.RoleService;
import com.example.demo.manipulation.service.base.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/redis/set")
    @ResponseBody
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
    @ResponseBody
    public Object delete(Integer id){
        personService.delete(id);

        return JSONObject.toJSON(personService.findAll());
    }

    @RequestMapping("/findById")
    @ResponseBody
    public Object findById(Integer id){
        return personService.findPersonById(id);
    }

    /**
     * 用户登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        Menu menu = new Menu();
        //menu.setHref("aaaa");
        PageInfo<Menu> page = menuService.findPage(1,2,menu);
        model.addAttribute("menuList",page);
        logger.info("aaaaaaaaaaaa"+          JSONObject.toJSON(page).toString());
        return "index";
    }

    /**
     * 用户登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bootIndex")
    public String bootIndex(Model model) {
        Menu menu = new Menu();
        //menu.setHref("aaaa");
        PageInfo<Menu> page = menuService.findPage(1,2,menu);
        model.addAttribute("menuList",page);
        logger.info("aaaaaaaaaaaa"+          JSONObject.toJSON(page).toString());
        return "bootIndex";
    }

    /**
     * 用户登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page")
    @ResponseBody
    public Object page(Integer pageNum,Integer pageSize) {
        Menu menu = new Menu();
        //menu.setHref("aaaa");
        PageInfo<Menu> page = menuService.findPage(pageNum,pageSize,menu);
        logger.info("aaaaaaaaaaaa"+          JSONObject.toJSON(page).toString());
        return page;
    }

    @RequestMapping(value = "/rolePage")
    @ResponseBody
    public Object rolePage(Integer pageNum,Integer pageSize,Role role) {
        PageInfo<Role> page = roleService.selectAllRoles(pageNum,pageSize,role);
        logger.info("111111");
        logger.info("bbbbbbbbbbbb"+          JSONObject.toJSON(page).toString());
        return page;
    }

    @RequestMapping(value="/login")
    public String login(Model model){
        model.addAttribute("title","测试1123332");
        return "login";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    @RequestMapping(value="/add")
    @ResponseBody
    public String addUserAndMenu(){
        String a = this.generateString(new Random(), "SOURCES", 10);
        System.out.println(a);
        User user = new User();
        user.setId(a);
        user.setUsername("testMycat");
        user.setPassword("1231234");
        user.setState(1);
        userService.insert(user);

        /*Menu menu = new Menu();
        menu.setPname("12");
        menu.setHref("testMycat");
        menu.setHref("testMycat");
        menu.setPid(1);
        menu.setTitle("mycat");
        menuService.saveMenu(menu);*/
        return "succese";
    }

    @RequestMapping(value="/findUserAndMenu")
    @ResponseBody
    public Object findUserAndMenu(){
        List list = new ArrayList();
        list.add(userService.findById("1"));
        for (int i = 0 ; i < 10000 ;i++){
            userService.findById("1");
        }

        //list.add(menuService.selectById(1));
        return list;
    }

    /**
     * Generate a random string.
     *
     * @param random the random number generator.
     * @param characters the characters for generating string.
     * @param length the length of the generated string.
     * @return
     */
    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     * 404页
     */
    @RequestMapping("/404")
    public String error404() {
        return "404";
    }

    /**
     * 401页
     */
    @RequestMapping("/401")
    public String error401() {
        return "401";
    }

    /**
     * 500页
     */
    @RequestMapping("/500")
    public String error500() {
        return "500";
    }
}
