package com.dream.softwarecupspring.pojo.System;

import com.dream.softwarecupspring.Mapper.AdminMapper.AdminStatsMapper;
import com.dream.softwarecupspring.pojo.System.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemLogTracker {

    @Autowired
    private AdminStatsMapper adminStatsMapper;

    // 自动记录系统日志
    public void recordSystemLog(String action, String description, Long userId) {
        SystemLog log = new SystemLog();
        log.setAction(action);
        log.setDescription(description);
        log.setUserId(userId);
        log.setCreatedTime(LocalDateTime.now());

        adminStatsMapper.insertSystemLog(log); // 插入日志到数据库
    }
}
