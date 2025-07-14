package com.dream.softwarecupspring.interceptor;

import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLogger;
import com.dream.softwarecupspring.utils.CurrentHolder;
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
        Integer currentId = CurrentHolder.getCurrentId();
        Long userId = currentId != null ? currentId.longValue() : 0L;  // ✅ 判空处理

        // TODO: 获取资源ID的逻辑仍需根据实际实现完善
        Long resourceId = 456L;
        String action = "VIEW";

        resourceAccessLogger.logResourceAccess(userId, resourceId, action);
        return true;
    }
}
