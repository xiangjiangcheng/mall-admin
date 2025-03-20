package com.river.malladmin.common.result;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author JiangCheng Xiang
 */
@Getter
public enum ResultCode implements Serializable {

    SUCCESS("00000", "操作成功"),
    TOKEN_INVALID("A0230", "Token 无效或已过期"),
    ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
    SYSTEM_ERROR("B0001", "系统错误");

    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
