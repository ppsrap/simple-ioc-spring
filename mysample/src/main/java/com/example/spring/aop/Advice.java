package com.example.spring.aop;

import java.lang.reflect.InvocationHandler;

/**
 * 继承了 InvocationHandler 接口
 * InvocationHandler is the interface implemented by the invocation handler of a proxy instance.
 * Each proxy instance has an associated invocation handler. When a method is invoked on a proxy
 * instance, the method invocation is encoded and dispatched to the invoke method of its invocation
 * handler.
 */
public interface Advice extends InvocationHandler {
}
