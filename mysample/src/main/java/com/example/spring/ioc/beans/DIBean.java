package com.example.spring.ioc.beans;

import com.example.spring.ioc.annonation.AutoWired;

public class DIBean {
    @AutoWired
    public User user;
    @AutoWired
    public Table table;
}