package com.dream.softwarecupspring.Mapper.AdminMapper;

import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLog;
import com.dream.softwarecupspring.pojo.User.UserActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminStatsMapper {
    void insertLogin(UserActivity userActivity);
    void updateSession(UserActivity userActivity);

    void insertUserActivity(UserActivity activity);

    void insertResourceAccessLog(ResourceAccessLog log);

    Map<String, Object> getSystemOverview();
    Map<String, Object> getUserActivity();
    Map<String, Object> getSystemUsage();
    Map<String, Object> getSystemHealth();


    List<Map<String, Object>> selectActiveUserTrendByDay(String startDate, String endDate);

    List<Map<String, Object>> getRecentActivities();
}
