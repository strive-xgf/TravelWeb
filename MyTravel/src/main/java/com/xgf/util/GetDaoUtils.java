package com.xgf.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//获取bean工具类
public class GetDaoUtils {

    private static ApplicationContext applicationContext = null;

    //静态代码块 只加载一次
    static {
        //加载配置文件
        applicationContext = new ClassPathXmlApplicationContext("com/xgf/config/applicationContext.xml");
    }

    public static <T> T getMapper(Class classFile) {
       return (T) applicationContext.getBean(classFile);
    }
}
