package com.example.demo.manipulation.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo.manipulation.entity.base.User;
import com.example.demo.manipulation.service.base.MenuService;
import com.example.demo.manipulation.service.base.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource  
    private UserService userService;
	@Resource
	private MenuService menuService;
	
      
    @RequestMapping("/showUser")  
    public String toIndex(HttpServletRequest request,Model model){  
        String userId = "1";  
        User user = userService.findById(userId);
        model.addAttribute("user", user);  
        return "showUser";
    }  
    
    /**
     * 用户登录
     * 
     * @param user
     * @param result
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/login")
    public String login(User user, BindingResult result, Model model) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        // 已登陆则 跳到首页
        if (subject.isAuthenticated()) {
            return "redirect:/index";
        }
        if (result.hasErrors()) {
            model.addAttribute("error", "参数错误！");
            return "redirect:/login";
        }
        //判断是否有当前用户，没有则调回登录页
        if(null == session.getAttribute("currentUser"))
        	return "redirect:/login";
        // 身份验证
        subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
    	
        return "redirect:/index";
    }
    
    /**
     * 测试登录后是否可正常访问方法
     * 
     * @param user
     * @param result
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1(@Valid User user, BindingResult result, Model model, HttpServletRequest request) throws Exception {
    	return "test1";
    }
}
