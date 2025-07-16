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
        LocalDateTime sessionStart = (LocalDateTime) request.getAttribute("sessionStart");
        LocalDateTime sessionEnd = LocalDateTime.now();

        Integer currentId = CurrentHolder.getCurrentId();
        long userId = currentId != null ? currentId.longValue() : 0L;  // ✅ 判空处理


        if (userId != 0L) {
            userActivityTracker.recordSessionEnd(userId, sessionStart, sessionEnd);
        }
    }
}
