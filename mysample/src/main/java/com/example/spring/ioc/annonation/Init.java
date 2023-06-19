package com.example.spring.ioc.annonation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*@Init用于标识需要在Bean初始化时调用的方法*/

@Retention(RetentionPolicy.RUNTIME)
public @interface Init {
}