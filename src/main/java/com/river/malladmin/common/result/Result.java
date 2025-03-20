package com.river.malladmin.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应类
 *
 * @author JiangCheng Xiang
 */
@Data
public class Result<T> implements Serializable {
    // 响应码
    private String code;
    // 响应数据
    private T data;
    // 响应信息
    private String msg;

    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> failed(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMessage());
        return result;
    }

    /**
     * 失败响应(系统默认错误)
     */
    public static <T> Result<T> failed() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SYSTEM_ERROR.getCode());
        result.setMsg(ResultCode.SYSTEM_ERROR.getMessage());
        return result;
    }

}
