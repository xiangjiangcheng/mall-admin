package com.river.malladmin.common.exception;

import com.river.malladmin.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author JiangCheng Xiang
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public <T> Result<T> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        if (e.getResultCode() != null) {
            return Result.failed(e.getResultCode());
        }
        return Result.failed(e.getMessage());
    }

    /**
     * 处理所有未捕获的异常
     * <p>
     * 兜底异常处理，处理未被捕获的异常。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> Result<T> handleException(Exception e) throws Exception {
        log.error(e.getMessage(), e);
        // 如果是 Spring Security 的认证异常或授权异常，直接抛出，交由 Spring Security 的异常处理器处理
        if (e instanceof AccessDeniedException
                || e instanceof AuthenticationException) {
            throw e;
        }
        return Result.failed("系统异常:" + e.getMessage());
    }

}
