package com.chen.entity;

import com.chen.spring.anno.ChenResource;
import com.chen.spring.anno.ChenService;

@ChenService
public class User {

    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
