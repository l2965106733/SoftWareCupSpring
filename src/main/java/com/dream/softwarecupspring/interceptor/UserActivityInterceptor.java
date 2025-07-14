package com.dream.softwarecupspring.interceptor;

import com.dream.softwarecupspring.pojo.User.UserActivityTracker;
import com.dream.softwarecupspring.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class UserActivityInterceptor implements HandlerInterceptor {

    @Autowired
    private UserActivityTracker userActivityTracker;

    // 会话开始时记录时间
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录会话开始时间
        LocalDateTime sessionStart = LocalDateTime.now();
        request.setAttribute("sessionStart", sessionStart);
        return true;
    }

    // 会话结束时记录时长
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 获取会话开始时间
        LocalDateTime sessionStart = (LocalDateTime) request.getAttribute("sessionStart");
        // 获取会话结束时间
        LocalDateTime sessionEnd = LocalDateTime.now();

        // 从 CurrentHolder 获取当前用户 ID，注意处理空值
        Long userId = null;
        try {
            userId = Long.valueOf(CurrentHolder.getCurrentId());  // 从 CurrentHolder 获取当前用户 ID
        } catch (NullPointerException e) {
            // 如果没有用户 ID，设置为 null 或 0L
            userId = 0L;  // 或者 null，取决于你对未登录用户的处理方式
        }

        // 如果是登录、注册等不需要记录用户活动的页面，直接返回
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login") || requestURI.contains("/register")) {
            return;  // 登录/注册请求不记录用户活动
        }

        // 记录会话时长
        if (userId != null) {
            userActivityTracker.recordSessionEnd(userId, sessionStart, sessionEnd);
        }
    }
}
