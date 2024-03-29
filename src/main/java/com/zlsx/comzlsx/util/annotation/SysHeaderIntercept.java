package com.zlsx.comzlsx.util.annotation;

import java.lang.annotation.*;

/**
 * @author : houxm
 * @date : 2018/11/19 14:39
 * @description :
 */
@Target({ElementType.METHOD, ElementType.TYPE})//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Retention(RetentionPolicy.RUNTIME)
@Documented//说明该注解将被包含在javadoc中
public @interface SysHeaderIntercept {

}
