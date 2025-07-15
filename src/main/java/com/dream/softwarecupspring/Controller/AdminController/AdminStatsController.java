package com.dream.softwarecupspring.Controller.AdminController;

import com.dream.softwarecupspring.Service.AdminService.AdminStatsService;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Resource.ResourceAccessLog;
import com.dream.softwarecupspring.pojo.User.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理员端 - 系统日志与监控控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminStatsController {

    @Autowired
    private AdminStatsService adminStatsService;


    @PostMapping("/userActivity")
    public Result insertUserActivity(@RequestBody UserActivity activity) {
        adminStatsService.insertUserActivity(activity);
        return Result.success();
    }

    @PostMapping("/resourceAccessLog")
    public Result insertResourceAccessLog(@RequestBody ResourceAccessLog log) {
        adminStatsService.insertResourceAccessLog(log);
        return Result.success();
    }

    // 系统状态与统计
    @GetMapping("/systemOverview")
    public Result getSystemOverview() {
        return Result.success(adminStatsService.getSystemOverview());
    }

    @GetMapping("/userActivity")
    public Result getUserActivity() {
        return Result.success(adminStatsService.getUserActivity());
    }

    @GetMapping("/systemUsage")
    public Result getSystemUsage() {
        return Result.success(adminStatsService.getSystemUsage());
    }

    @GetMapping("/systemHealth")
    public Result getSystemHealth() {
        return Result.success(adminStatsService.getSystemHealth());
    }

    @GetMapping("/userActivityTrend")
    public Result getUserActivityTrend(@RequestParam String startDate,
                                       @RequestParam String endDate) {
        List<Map<String, Object>> data = adminStatsService.getUserActivityTrend(startDate, endDate);
        return Result.success(data);
    }

    @GetMapping("/recentActivities")
    public Result getRecentActivities() {
        List<Map<String, Object>> activities = adminStatsService.getRecentActivities();
        return Result.success(activities);
    }
}
