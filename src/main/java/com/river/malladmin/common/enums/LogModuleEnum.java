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
    ROLE("权限"),
    DICT("数据字典"),
    OTHER("其他"),
    ;

    private final String moduleName;

    LogModuleEnum(String moduleName) {
        this.moduleName = moduleName;
    }
}
