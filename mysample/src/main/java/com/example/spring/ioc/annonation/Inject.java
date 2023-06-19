package com.example.spring.ioc.annonation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*@Inject用于标识需要进行依赖注入的字段和方法参数，*/
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
}
