package com.example.spring.aop;

import java.lang.reflect.Method;

/**
 * 前置通知
 */
public class BeforeAdvice implements Advice{
    private Object object;
    private MethodInvocation methodInvocation;

    public BeforeAdvice(Object bean, MethodInvocation methodInvocation ){
        this.object = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在目标方法执行前调用通知
        methodInvocation.invoke();
        return method.invoke(object, args);
    }
}
