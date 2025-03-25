package com.river.malladmin.common.annotation;

import com.river.malladmin.common.enums.LogModuleEnum;

import java.lang.annotation.*;

/**
 * @author JiangCheng Xiang
 */
@Retention(RetentionPolicy.RUNTIME) // 何时使用该注解，一般是RUNTIME>CLASS>SOURCE
@Target({ElementType.METHOD}) // 注解用于什么地方，此处为作用于方法上
@Documented // 文档生成时，该注解将被包含在javadoc中，可去掉
public @interface Log {

    /**
     * 日志描述
     * 如：新增用户
     *
     * @return 日志描述
     */
    String value() default "";

    /**
     * 日志模块
     *
     * @return 日志模块
     */

    LogModuleEnum module();

    /**
     * 是否记录请求参数
     *
     * @return 是否记录请求参数
     */
    boolean params() default true;

    /**
     * 是否记录响应结果
     * <br/>
     * 响应结果默认不记录，避免日志过大
     *
     * @return 是否记录响应结果
     */
    boolean result() default false;

}
