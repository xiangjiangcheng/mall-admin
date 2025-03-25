package com.river.malladmin.common.enums;

import lombok.Getter;

/**
 * @author JiangCheng Xiang
 */
@Getter
public enum LogModuleEnum {

    AUTH("登录"),
    EXCEPTION("异常"),
    USER("用户"),
    ;

    private final String moduleName;

    LogModuleEnum(String moduleName) {
        this.moduleName = moduleName;
    }
}
