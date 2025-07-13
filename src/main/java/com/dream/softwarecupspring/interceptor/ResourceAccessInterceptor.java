package com.dream.softwarecupspring.interceptor;

import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class ResourceAccessInterceptor implements HandlerInterceptor {

    @Autowired
    private ResourceAccessLogger resourceAccessLogger;

    // 自动记录资源访问日志
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long userId = 123L; // 获取当前用户ID
        Long resourceId = 456L; // 获取资源ID（通常从请求中获取）
        String action = "VIEW"; // 用户执行的操作（例如查看、下载）

        resourceAccessLogger.logResourceAccess(userId, resourceId, action); // 记录资源访问

        return true;
    }
}
