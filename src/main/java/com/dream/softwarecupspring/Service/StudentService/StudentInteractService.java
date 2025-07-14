package com.dream.softwarecupspring.Service.StudentService;

import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;

import java.util.List;
import java.util.Map;

public interface StudentInteractService {
    void submitQuestion(StudentQuestion studentQuestion);

    List<StudentQuestion> getMyQuestions(Integer studentId);

    StudentQuestion getQuestionDetail(Integer questionId);

    void rateAnswer(Integer questionId, Integer rating);

    Map<String, Object> getInteractStats(Integer studentId);

    void submitRating(Integer questionId, Integer rating);

    Integer getRating(Integer questionId);

    List<StudentQuestion> getRatingHistory(Integer studentId);

    Map<String, Object> getRatingStats(Integer studentId);

    Object getHomeStats(Long studentId);

    Object getRecentActivities(Long studentId);

    Object getTeacherId(Long studentId);
}
