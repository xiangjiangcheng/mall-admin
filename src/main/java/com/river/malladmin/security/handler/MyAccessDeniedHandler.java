package com.river.malladmin.security.handler;

import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.common.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 无权限访问处理器
 *
 * @author JiangCheng Xiang
 */
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_UNAUTHORIZED);
    }

}
