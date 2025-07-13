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

        // 从 CurrentHolder 获取当前用户 ID
        Long userId = Long.valueOf(CurrentHolder.getCurrentId()); // 从 CurrentHolder 获取当前登录用户的 ID

        // 记录会话时长
        userActivityTracker.recordSessionEnd(userId, sessionStart, sessionEnd);
    }
}
