package com.example.spring.aop;

public class HelloServiceImpl implements HelloService{
    @Override
    public void sayHelloWorld() {
        System.out.println("hello World!");
    }
}
