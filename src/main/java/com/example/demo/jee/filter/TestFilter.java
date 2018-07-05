package com.example.demo.jee.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 自定义Filter
 * Login Filter
 * @Description:
 * @Author:libin
 * @Date: Created in 16:40 2017/11/13
 */
@WebFilter(filterName = "testFilter",urlPatterns = "/*")
@Order(1)
public class TestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("filter============================================> 我是自定义过滤器!");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}