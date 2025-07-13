package com.dream.softwarecupspring.pojo.User;

import com.dream.softwarecupspring.Mapper.AdminMapper.AdminStatsMapper;
import com.dream.softwarecupspring.pojo.User.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UserActivityTracker {

    @Autowired
    private AdminStatsMapper adminStatsMapper;

    // 登录时记录
    public void recordLogin(Long userId) {
        UserActivity userActivity = new UserActivity();
        userActivity.setUserId(userId);
        userActivity.setLoginDate(LocalDate.now());
        userActivity.setCreatedTime(LocalDateTime.now());

        adminStatsMapper.insertLogin(userActivity); // 插入登录记录
    }

    // 会话结束时记录
    public void recordSessionEnd(Long userId, LocalDateTime sessionStart, LocalDateTime sessionEnd) {
        UserActivity userActivity = new UserActivity();
        userActivity.setUserId(userId);
        userActivity.setLoginDate(sessionStart.toLocalDate());
        userActivity.setCreatedTime(sessionEnd);
        userActivity.setSessionDuration((int) java.time.Duration.between(sessionStart, sessionEnd).getSeconds());

        adminStatsMapper.updateSession(userActivity); // 更新会话时长
    }
}
