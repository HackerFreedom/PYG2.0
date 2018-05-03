package com.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LonginController {

    //登录操作
    @RequestMapping("/name")
    public Map login(){
        //获取spring_security中上下文,从而取值
        System.out.println("--调用了login控制层--");

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        //通过key和value方式来,获取对象的值,显示在页面中
        Map map= new HashMap();
        map.put("loginName",name);
        return map;
    }
}
