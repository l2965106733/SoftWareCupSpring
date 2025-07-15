package com.dream.softwarecupspring.Mapper.TeacherMapper;

import com.dream.softwarecupspring.pojo.Homework.*;
import com.dream.softwarecupspring.pojo.Interact.AnswerQueryParam;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import com.dream.softwarecupspring.pojo.Overall.StudentSummary;
import com.dream.softwarecupspring.pojo.Resource.PopularResource;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherStatsMapper {
    Integer getTotalStudents(Integer teacherId);

    Integer getNewStudentsThisWeek(Integer teacherId);

    Double getHomeworkRate(Integer teacherId);

    Double getHomeworkTrend(Integer teacherId);

    Double getAvgScore(Integer teacherId);

    Double getScoreTrend(Integer teacherId);

    Double getActiveRate(Integer teacherId);

    Double getActiveIncrease(Integer teacherId);

    Integer getTotalHomework(Integer teacherId);
    Integer getPublishedHomework(Integer teacherId);
    Integer getGradedHomework(Integer teacherId);
    Integer getPendingGrade(Integer teacherId);
    Double getSubmitRate(Integer teacherId);
    Integer getScoreRangeCount(Integer teacherId, Double min, Double max);
    List<RecentHomework> getRecentHomework(Integer teacherId);

    List<StudentSummary> getStudentSummaries(Integer teacherId);

    Long getTotalResources(Integer teacherId);

    Long getWeeklyUploads(Integer teacherId);

    Long getResourceViewCount(Integer teacherId);

    Long getResourceDownloadCount(Integer teacherId);

    List<PopularResource> getPopularResources(Integer teacherId);


    Integer getTotalQuestions(Integer teacherId);

    Integer getAnsweredQuestions(Integer teacherId);

    Integer getPendingQuestions(Integer teacherId);

    Double getAvgRating(Integer teacherId);

    List<RecentQuestion> getRecentQuestions(Integer teacherId);

    Double getAvgResponseTime(Integer teacherId);

    Map<String, Object> getTeacherOverview(Long teacherId);

    List<Map<String, Object>> getTeacherActivities(Long teacherId, int limit);
}
