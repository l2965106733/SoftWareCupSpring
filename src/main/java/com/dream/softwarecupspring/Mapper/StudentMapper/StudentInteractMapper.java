package com.dream.softwarecupspring.Mapper.StudentMapper;

import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Homework.RatingCount;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentInteractMapper {
    void insertQuestion(StudentQuestion question);
    List<StudentQuestion> getQuestionsByStudentId(Integer studentId);
    StudentQuestion selectQuestionById(Integer questionId);
    void updateQuestionById(StudentQuestion question);
    Integer getTotalQuestionsCount(Integer studentId);
    Integer getAnsweredQuestionsCount(Integer studentId);
    Integer getPendingQuestionsCount(Integer studentId);
    Double getAverageRating(Integer studentId);
    List<Map<String, Object>> getQuestionsByType(Integer studentId);
    List<StudentQuestion> getRecentQuestions(Integer studentId);
    List<StudentQuestion> getRatedQuestionsByStudentId(Integer studentId);
    Object getTotalRatingsCount(Integer studentId);
    List<RatingCount> getRatingDistribution(Integer studentId);
    Object getAverageRatingByStudent(Integer studentId);
    List<StudentQuestion> getRecentRatedQuestions(Integer studentId);
    Map<String, Object> getHomeStats(@Param("studentId") Long studentId);
    List<Map<String, Object>> getRecentActivities(@Param("studentId") Long studentId);

    Object getTeacherIdByStudentId(Long studentId);
}
