package com.example.demo.jee.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @Description:
 * @Author:hkw
 * @Date: Created in 17:46 2017/11/13
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("执行preHandle==============》");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("执行postHandle==============》");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("执行afterCompletion==============》");
    }
    /*

    *//**
     * 登陆拦截器
     *//*
    public class LoginInterceptor implements HandlerInterceptor {
        @Autowired
        private UserService userService;

        @Override
        public void afterCompletion(HttpServletRequest request,
                                    HttpServletResponse response, Object obj, Exception ex)
                throws Exception {
        }

        @Override
        public void postHandle(HttpServletRequest request,
                               HttpServletResponse response, Object obj, ModelAndView modelAndView)
                throws Exception {
        }


        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response, Object handler) throws Exception {
            WebUser webUser = CookieUtil.getLoginUser(request);
            if (null == webUser) {
                String basePath = request.getScheme() + "//:" + request.getServerName() + ":" + request.getServerPort();
                response.sendRedirect(basePath + "/login");
                return false;
            }
            return true;
        }

    }*/

}