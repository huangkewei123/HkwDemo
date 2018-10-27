package com.example.demo.jee.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil{

    private static Logger logger;

    public static Logger getLogger(Class<?> beanClass,String logLevel,Object logContext){
        logger = LoggerFactory.getLogger(beanClass);
        //logger.
        return logger;
    }
}
