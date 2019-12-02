package com.chen.service.impl;

import com.chen.service.UserService;
import com.chen.spring.anno.ChenResource;
import com.chen.spring.anno.ChenService;

import javax.annotation.Resource;

@ChenService
public class UserServiceImpl implements UserService {


    @ChenResource
    private OrderServiceImpl orderServiceImpl;

    public void add() {
        orderServiceImpl.addOrder();
        System.out.println("使用java反射机制初始化对象..");
    }

    public UserServiceImpl() {
        System.out.println("UserServiceImpl构造成功");
    }
}
