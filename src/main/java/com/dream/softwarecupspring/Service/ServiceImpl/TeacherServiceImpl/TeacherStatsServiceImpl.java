package com.dream.softwarecupspring.Service.ServiceImpl.TeacherServiceImpl;

import com.dream.softwarecupspring.Mapper.TeacherMapper.TeacherStatsMapper;
import com.dream.softwarecupspring.Service.TeacherService.TeacherStatsService;
import com.dream.softwarecupspring.pojo.Homework.HomeworkStats;
import com.dream.softwarecupspring.pojo.Homework.RecentHomework;
import com.dream.softwarecupspring.pojo.Interact.InteractStats;
import com.dream.softwarecupspring.pojo.Overall.StudentSummary;
import com.dream.softwarecupspring.pojo.Overall.TeacherOverview;
import com.dream.softwarecupspring.pojo.Resource.ResourceStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherStatsServiceImpl implements TeacherStatsService {
    @Autowired
    private TeacherStatsMapper teacherStatsMapper;
    
    @Override
    public TeacherOverview getOverview(Integer teacherId) {
        Integer totalStudents = teacherStatsMapper.getTotalStudents(teacherId);
        Integer newStudentsWeek = teacherStatsMapper.getNewStudentsThisWeek(teacherId);
        Double homeworkRate = teacherStatsMapper.getHomeworkRate(teacherId);
        Double homeworkTrend = teacherStatsMapper.getHomeworkTrend(teacherId);
        Double avgScore = teacherStatsMapper.getAvgScore(teacherId);
        Double scoreTrend = teacherStatsMapper.getScoreTrend(teacherId);
        Double activeRate = teacherStatsMapper.getActiveRate(teacherId);
        Double activeIncrease = teacherStatsMapper.getActiveIncrease(teacherId);

        TeacherOverview vo = new TeacherOverview();
        vo.setTotalStudents(totalStudents);
        vo.setNewStudentsWeek(newStudentsWeek);
        vo.setHomeworkRate(homeworkRate);
        vo.setHomeworkTrend(homeworkTrend);
        vo.setAvgScore(avgScore);
        vo.setScoreTrend(scoreTrend);
        vo.setActiveRate(activeRate);
        vo.setActiveIncrease(activeIncrease);
        return vo;
    }

    @Override
    public List<StudentSummary> getStudentSummaries(Integer teacherId) {
        return teacherStatsMapper.getStudentSummaries(teacherId);
    }

    @Override
    public ResourceStats getResourceStats(Integer teacherId) {
        ResourceStats stats = new ResourceStats();
        stats.setTotalResources(teacherStatsMapper.getTotalResources(teacherId));
        stats.setWeeklyUploads(teacherStatsMapper.getWeeklyUploads(teacherId));
        stats.setViewCount(teacherStatsMapper.getResourceViewCount(teacherId));
        stats.setDownloadCount(teacherStatsMapper.getResourceDownloadCount(teacherId));
        stats.setPopularResources(teacherStatsMapper.getPopularResources(teacherId));
        return stats;
    }

    @Override
    public InteractStats getInteractStats(Integer teacherId) {
        InteractStats stats = new InteractStats();
        stats.setTotalQuestions(teacherStatsMapper.getTotalQuestions(teacherId));
        stats.setAnsweredQuestions(teacherStatsMapper.getAnsweredQuestions(teacherId));
        stats.setPendingQuestions(teacherStatsMapper.getPendingQuestions(teacherId));
        stats.setAvgRating(teacherStatsMapper.getAvgRating(teacherId));
        stats.setAvgResponseTime(teacherStatsMapper.getAvgResponseTime(teacherId));
        stats.setRecentQuestions(teacherStatsMapper.getRecentQuestions(teacherId));
        return stats;
    }

    @Override
    public HomeworkStats getHomeworkStats(Integer teacherId) {
        HomeworkStats stats = new HomeworkStats();
        stats.setTotalHomework(teacherStatsMapper.getTotalHomework(teacherId));
        stats.setPublishedHomework(teacherStatsMapper.getPublishedHomework(teacherId));
        stats.setGradedHomework(teacherStatsMapper.getGradedHomework(teacherId));
        stats.setPendingGrade(teacherStatsMapper.getPendingGrade(teacherId));
        stats.setSubmitRate(teacherStatsMapper.getSubmitRate(teacherId));

        Map<String, Integer> scoreDist = new LinkedHashMap<>();
        scoreDist.put("优秀(得分90%以上)", teacherStatsMapper.getScoreRangeCount(teacherId, 0.9, 1.0));
        scoreDist.put("良好(得分60%~90%)", teacherStatsMapper.getScoreRangeCount(teacherId, 0.6, 0.9));
        scoreDist.put("不及格(得分30%~60%)", teacherStatsMapper.getScoreRangeCount(teacherId, 0.3, 0.6));
        scoreDist.put("极低(得分30%以下)", teacherStatsMapper.getScoreRangeCount(teacherId, 0.0, 0.3));
        stats.setScoreDistribution(scoreDist);

        List<RecentHomework> recent = teacherStatsMapper.getRecentHomework(teacherId);
        stats.setRecentHomework(recent);
        return stats;
    }

    @Override
    public Map<String, Object> getTeacherOverview(Long teacherId) {
        return teacherStatsMapper.getTeacherOverview(teacherId);
    }

    @Override
    public List<Map<String, Object>> getTeacherActivities(Long teacherId, int limit) {
        return teacherStatsMapper.getTeacherActivities(teacherId, limit);
    }
}
