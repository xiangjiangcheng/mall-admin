package com.river.malladmin.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @author JiangCheng Xiang
 */
public class GlobalInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("start_timestamp", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long startTimestamp = (Long) request.getAttribute("start_timestamp");
        long endTimestamp = System.currentTimeMillis();
        String method = request.getMethod();
        int status = response.getStatus();
        StringBuffer requestURL = request.getRequestURL();
        if (status != 200) {
            logger.warn("[{}][{}] took {} ms {}", method, status, (endTimestamp - startTimestamp), requestURL);
        } else {
            logger.info("[{}][{}] took {} ms {}", method, status, (endTimestamp - startTimestamp), requestURL);
        }
    }
}
