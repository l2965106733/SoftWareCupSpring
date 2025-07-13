package com.dream.softwarecupspring.pojo.Resource;


import com.dream.softwarecupspring.Mapper.AdminMapper.AdminStatsMapper;
import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResourceAccessLogger {

    @Autowired
    private AdminStatsMapper adminStatsMapper;

    // 自动记录资源访问日志
    public void logResourceAccess(Long userId, Long resourceId, String actionType) {
        ResourceAccessLog log = new ResourceAccessLog();
        log.setUserId(userId);
        log.setResourceId(resourceId);
        log.setActionType(actionType);  // 用户操作（如“下载”、“查看”）
        log.setAccessTime(LocalDateTime.now());

        adminStatsMapper.insertResourceAccessLog(log); // 插入日志到数据库
    }
}
