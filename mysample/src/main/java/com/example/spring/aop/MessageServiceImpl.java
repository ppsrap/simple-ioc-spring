package com.example.spring.aop;

public class MessageServiceImpl implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending message: " + message);
    }
}