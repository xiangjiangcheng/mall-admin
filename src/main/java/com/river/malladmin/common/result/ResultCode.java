package com.river.malladmin.common.result;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author JiangCheng Xiang
 */
@Getter
public enum ResultCode implements Serializable {

    SUCCESS("00000", "操作成功"),
    ACCESS_TOKEN_INVALID("A0230", "Token 无效或已过期"),
    ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
    USER_PASSWORD_ERROR("A0403", "用户名或密码错误"),

    USER_VERIFICATION_CODE_EXPIRED("A0500", "验证码已过期"),
    USER_VERIFICATION_CODE_ERROR("A0501", "验证码错误"),

    SYSTEM_ERROR("B0001", "系统错误"),
    USER_NOT_EXIST("B0100", "用户不存在"),
    ROLE_NOT_EXIST("B0101", "角色不存在"),
    ROLE_REPEATED("B0102", "角色名称或角色编码已存在，请修改后重试！"),
    MENU_NOT_EXIST("B0203", "角色不存在"),
    ;

    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
