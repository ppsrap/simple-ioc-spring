package com.example.spring.aop;


import com.example.spring.ioc.annonation.Init;
import com.example.spring.ioc.annonation.Inject;

public class HelloServiceImpl implements HelloService {
    @Inject
    private MessageService messageService;

    @Override
    public void sayHello() {
        System.out.println("Hello, I am UserServiceImpl.");
    }

    @Override
    public void sendMessage() {
        messageService.sendMessage("Hello from UserServiceImpl!");
    }

    @Init
    private void init() {
        System.out.println("Initializing UserServiceImpl...");
    }
}
