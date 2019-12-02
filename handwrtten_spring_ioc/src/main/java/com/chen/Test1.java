package com.chen;

import com.chen.service.UserService;
import com.chen.spring.ChenClassPathXmlApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Test1 {
    public static void main(String[] args) throws Exception {
        ChenClassPathXmlApplicationContext context = new ChenClassPathXmlApplicationContext("com.chen");
        UserService userServiceImpl = (UserService)context.getBean("userServiceImpl");
        userServiceImpl.add();
    }
    public void test1(){

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        Object bean = context.getBean("");
    }

}
