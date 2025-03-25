package com.river.malladmin.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 状态枚举
 */
@Getter
public enum StatusEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    // Mybatis-Plus 提供注解表示插入数据库时插入该值
    @EnumValue
    private final Integer value;
    private final String label;

    StatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
