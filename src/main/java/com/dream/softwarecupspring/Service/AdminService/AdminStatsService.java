package com.dream.softwarecupspring.Service.AdminService;

import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLog;
import com.dream.softwarecupspring.pojo.User.UserActivity;

import java.util.List;
import java.util.Map;

public interface AdminStatsService {
    void insertUserActivity(UserActivity activity);

    void insertResourceAccessLog(ResourceAccessLog log);


    Object getSystemOverview();

    Object getUserActivity();

    Object getSystemUsage();

    Object getSystemHealth();

    List<Map<String, Object>> getUserActivityTrend(String startDate, String endDate);

    List<Map<String, Object>> getRecentActivities();
}
