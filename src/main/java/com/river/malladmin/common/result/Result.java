package com.river.malladmin.common.result;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一响应类
 *
 * @author JiangCheng Xiang
 */
@Getter
@Setter
public class Result<T> implements Serializable {
    // 响应码
    private String code;
    // 响应信息
    private String message;
    // 响应数据
    private T data;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> failed(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> Result<T> failed(String code, String message) {
        return new Result<>(code, message);
    }

    public static <T> Result<T> failed(String message) {
        return new Result<>(ResultCode.SYSTEM_ERROR.getCode(), message);
    }

    public static <T> Result<T> failed() {
        return new Result<>(ResultCode.SYSTEM_ERROR.getCode(), ResultCode.SYSTEM_ERROR.getMessage());
    }

}
