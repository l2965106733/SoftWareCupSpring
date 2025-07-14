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
        // 获取当前用户ID，处理空值情况
        Long userId = null;
        try {
            userId = Long.valueOf(CurrentHolder.getCurrentId());  // 获取当前用户ID
        } catch (NullPointerException e) {
            // 如果用户 ID 为空（用户未登录），可以设置为默认值，如 0L
            userId = 0L;  // 或者 null，根据业务需求
        }

        // 获取资源ID，这里假设资源ID是从请求参数中获取
        Long resourceId = 456L;  // 从请求中获取资源ID的逻辑可以继续按需实现
        // 如果有其他获取 resourceId 的方式，可以替换此行

        // 假设操作为“VIEW”，你也可以根据请求动态获取这个值
        String action = "VIEW";

        // 记录资源访问
        resourceAccessLogger.logResourceAccess(userId, resourceId, action);

        // 继续处理请求
        return true;
    }
}
