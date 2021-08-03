package com.example.spring.aop;


import java.lang.reflect.Proxy;

/**
 * 生成代理类
 */
public class SimpleAOP {
    public static Object getProxy(Object bean, Advice advice){
        /**
         * 使用反射的Proxy代理类创建新的实例
         * 传递三个参数：
         * 1. 代理类的ClassLoader
         * 2. 需要被代理的对象所实现的接口
         * 3. InvocationHandler的实现类
         */
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(), bean.getClass().getInterfaces(), advice);
    }
}
