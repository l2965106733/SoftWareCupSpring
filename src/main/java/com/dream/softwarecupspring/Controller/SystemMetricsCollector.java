package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.Mapper.AdminMapper.AdminStatsMapper;
import com.dream.softwarecupspring.pojo.System.SystemMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@EnableScheduling // 开启定时任务功能
public class SystemMetricsCollector {

    @Autowired
    private AdminStatsMapper adminStatsMapper;

    @Scheduled(fixedRate = 60000) // 每60秒执行一次
    public void collectSystemMetrics() {
        SystemMetrics metrics = new SystemMetrics();
        metrics.setCpuUsage(randomDecimal(50, 90));
        metrics.setMemoryUsage(randomDecimal(60, 95));
        metrics.setDiskUsage(randomDecimal(40, 80));
        metrics.setNetworkTraffic(randomLong(500000, 5000000L));
        metrics.setDatabaseConnections(randomInt(10, 100));
        metrics.setApiRequests(randomInt(50, 300));
        metrics.setResponseTime(randomDecimal(100, 500));
        metrics.setErrorRate(randomDecimal(0, 5));
        metrics.setRecordedTime(LocalDateTime.now());

        adminStatsMapper.insert(metrics); // 写入数据库
        System.out.println("[SystemMetricsCollector] 插入系统性能数据成功");
    }

    // ---------- 随机模拟数据 ----------
    private BigDecimal randomDecimal(double min, double max) {
        return BigDecimal.valueOf(min + Math.random() * (max - min));
    }

    private long randomLong(long min, long max) {
        return min + (long) (Math.random() * (max - min));
    }

    private int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}
