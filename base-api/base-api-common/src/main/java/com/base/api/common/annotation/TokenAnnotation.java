package com.base.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 会员权限注解
 * 部分接口支持会员和非会员访问
 * @author xiaohaizi
 * @date   2017年3月10日 上午9:23:57
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenAnnotation {

}
