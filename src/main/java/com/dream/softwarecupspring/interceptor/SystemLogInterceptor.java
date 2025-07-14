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
        Integer currentId = CurrentHolder.getCurrentId();
        Long userId = currentId != null ? currentId.longValue() : 0L;  // ✅ 判空处理

        String uri = request.getRequestURI();
        if (uri.contains("/register") || uri.contains("/login")) {
            return true; // 登录/注册不记录日志
        }

        String action = "Accessing " + uri;
        String description = "User accessed " + uri;
        systemLogTracker.recordSystemLog(action, description, userId);
        return true;
    }
}
