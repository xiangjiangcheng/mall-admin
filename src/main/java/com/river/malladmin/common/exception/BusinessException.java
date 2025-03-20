package com.river.malladmin.common.exception;

import com.river.malladmin.common.result.ResultCode;
import lombok.Getter;

/**
 * @author JiangCheng Xiang
 */
@Getter
public class BusinessException extends RuntimeException {

    public ResultCode resultCode;

    public BusinessException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.resultCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
    }

}