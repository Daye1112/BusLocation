package com.ky.gps.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限加载注解类
 *
 * @author Darren
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionName {
    /**
     * 操作显示名
     */
    String displayName();

    /**
     * 操作组
     */
    String group();
}
