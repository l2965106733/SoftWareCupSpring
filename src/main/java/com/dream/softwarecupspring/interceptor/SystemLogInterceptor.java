package com.dream.softwarecupspring.interceptor;

import com.dream.softwarecupspring.pojo.System.SystemLogTracker;
import com.dream.softwarecupspring.utils.CurrentHolder;
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
        // 获取当前用户 ID，检查是否为空
        Long userId = null;
        try {
            userId = Long.valueOf(CurrentHolder.getCurrentId());  // 尝试获取用户ID
        } catch (NullPointerException e) {
            // 如果用户 ID 不存在，设置为 null 或 0L
            userId = 0L;  // 或者可以选择 0L 表示未登录用户
        }

        // 判断是否为需要登录的页面（例如注册页面可以放行）
        if (request.getRequestURI().contains("/register") || request.getRequestURI().contains("/login")) {
            // 如果是注册/登录请求，不记录日志，直接放行
            return true;
        }

        // 获取用户的操作描述
        String action = "Accessing " + request.getRequestURI();  // 用户的操作描述
        String description = "User accessed " + request.getRequestURI();  // 操作描述

        // 记录系统日志
        systemLogTracker.recordSystemLog(action, description, userId);

        // 继续处理请求
        return true;
    }
}
