package com.dream.softwarecupspring.interceptor;

import com.dream.softwarecupspring.pojo.System.SystemLogTracker;
import com.dream.softwarecupspring.utils.CurrentHolder;  // 用于获取当前用户ID
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SystemLogInterceptor implements HandlerInterceptor {

    @Autowired
    private SystemLogTracker systemLogTracker;  // 注入 SystemLogTracker

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前用户 ID（通过 CurrentHolder 获取）
        Long userId = Long.valueOf(CurrentHolder.getCurrentId());  // 从 CurrentHolder 获取用户ID

        // 获取用户的操作描述
        String action = "Accessing " + request.getRequestURI();  // 用户的操作描述
        String description = "User accessed " + request.getRequestURI();  // 操作描述

        // 记录系统日志
        systemLogTracker.recordSystemLog(action, description, userId);

        // 继续处理请求
        return true;
    }
}
