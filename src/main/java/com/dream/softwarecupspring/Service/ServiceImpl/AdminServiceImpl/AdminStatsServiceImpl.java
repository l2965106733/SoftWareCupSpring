package com.dream.softwarecupspring.Service.ServiceImpl.AdminServiceImpl;

import com.dream.softwarecupspring.Mapper.AdminMapper.AdminStatsMapper;
import com.dream.softwarecupspring.Service.AdminService.AdminStatsService;
import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLog;
import com.dream.softwarecupspring.pojo.User.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminStatsServiceImpl implements AdminStatsService {
    @Autowired
    AdminStatsMapper adminStatsMapper;


    @Override
    public void insertUserActivity(UserActivity activity) {
        adminStatsMapper.insertUserActivity(activity);
    }

    @Override
    public void insertResourceAccessLog(ResourceAccessLog log) {
        adminStatsMapper.insertResourceAccessLog(log);
    }

    @Override
    public Map<String, Object> getSystemOverview() {
        return adminStatsMapper.getSystemOverview();
    }

    @Override
    public Map<String, Object> getUserActivity() {
        return adminStatsMapper.getUserActivity();
    }

    @Override
    public List<Map<String, Object>> getUserActivityTrend(String startDate, String endDate) {
        return adminStatsMapper.selectActiveUserTrendByDay(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getRecentActivities() {
        return adminStatsMapper.getRecentActivities();
    }

    @Override
    public List<Map<String, Object>> getKnowledgeDistribution(String knowledgeName) {
        return adminStatsMapper.getKnowledgeDistribution(knowledgeName);
    }

    @Override
    public List<Map<String, Object>> getTopKnowledgeScore() {
        return adminStatsMapper.getTopKnowledgeScore();
    }
}
