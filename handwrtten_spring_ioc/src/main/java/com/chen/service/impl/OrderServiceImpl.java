package com.chen.service.impl;

import com.chen.service.OrderService;
import com.chen.spring.anno.ChenService;

@ChenService
public class OrderServiceImpl implements OrderService {

    public void addOrder() {
        System.out.println("正在调用OrderServiceImpl的addOrder");
    }

    public OrderServiceImpl() {
        System.out.println("正在生成OrderServiceImpl");
    }
}
