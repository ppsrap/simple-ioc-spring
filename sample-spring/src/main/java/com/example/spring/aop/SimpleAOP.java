package com.example.spring.aop;

import java.lang.reflect.Proxy;

/**
 * 生成代理类
 */
public class SimpleAOP {
    public static Object getProxy(Object bean, Advice advice){
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(), bean.getClass().getInterfaces(), advice);
    }
}
