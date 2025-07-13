package com.dream.softwarecupspring.Controller.TeacherController;

import com.dream.softwarecupspring.Service.TeacherService.TeacherStatsService;
import com.dream.softwarecupspring.pojo.Common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/teacher/stats")
public class TeacherStatsController {

    @Autowired
    private TeacherStatsService teacherStatsService;

    @GetMapping("/overview/{teacherId}")
    public Result getOverview(@PathVariable Integer teacherId) {
        return Result.success(teacherStatsService.getOverview(teacherId));
    }

    @GetMapping("/homeworkStats/{teacherId}")
    public Result getHomeworkStats(@PathVariable Integer teacherId) {
        return Result.success(teacherStatsService.getHomeworkStats(teacherId));
    }

    @GetMapping("/students/{teacherId}")
    public Result getStudentSummaries(@PathVariable Integer teacherId) {
        return Result.success(teacherStatsService.getStudentSummaries(teacherId));
    }

    @GetMapping("/resourceStats/{teacherId}")
    public Result getResourceStats(@PathVariable Integer teacherId) {
        return Result.success(teacherStatsService.getResourceStats(teacherId));
    }

    @GetMapping("/interactStats/{teacherId}")
    public Result getInteractStats(@PathVariable Integer teacherId) {
        return Result.success(teacherStatsService.getInteractStats(teacherId));
    }

    @GetMapping("/homeOverview/{teacherId}")
    public Result getTeacherOverview(@PathVariable Long teacherId) {
        return Result.success(teacherStatsService.getTeacherOverview(teacherId));
    }

    @GetMapping("/activities/{teacherId}")
    public Result getTeacherActivities(
            @PathVariable Long teacherId,
            @RequestParam(defaultValue = "10") int limit) {
        return Result.success(teacherStatsService.getTeacherActivities(teacherId, limit));
    }
}
