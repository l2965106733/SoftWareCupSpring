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
        scoreDist.put("90-100分", teacherStatsMapper.getScoreRangeCount(teacherId, 90, 100));
        scoreDist.put("80-89分", teacherStatsMapper.getScoreRangeCount(teacherId, 80, 89));
        scoreDist.put("70-79分", teacherStatsMapper.getScoreRangeCount(teacherId, 70, 79));
        scoreDist.put("60-69分", teacherStatsMapper.getScoreRangeCount(teacherId, 60, 69));
        scoreDist.put("60分以下", teacherStatsMapper.getScoreRangeCount(teacherId, 0, 59));
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
