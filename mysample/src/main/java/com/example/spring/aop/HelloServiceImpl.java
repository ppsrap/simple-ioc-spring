package com.example.spring.aop;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
