package com.dream.softwarecupspring.Service;

import com.dream.softwarecupspring.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentService {
    List<Map<String, Object>> getCoursewareList(Integer studentId);

    Map<String, Object> getStudyStats(Integer studentId);

    void recordAiQuestion(AiQuestion aiQuestion);

    List<Map<String, Object>> getHomeworkList(Integer studentId);

    List<Map<String, Object>> getHomeworkDetail(Integer homeworkId);

    void saveHomeworkDraft(StudentHomework studentHomework);

    void submitHomework(StudentHomework studentHomework);

    Map<String, Object> getHomeworkStats(Integer studentId);

    void submitQuestion(StudentQuestion studentQuestion);

    List<StudentQuestion> getMyQuestions(Integer studentId);

    StudentQuestion getQuestionDetail(Integer questionId);

    void rateAnswer(Integer questionId, Integer rating);

    Map<String, Object> getInteractStats(Integer studentId);

    Integer getTeacherId(Integer studentId);

    void submitRating(Integer questionId, Integer rating);

    Integer getRating(Integer questionId);

    List<StudentQuestion> getRatingHistory(Integer studentId);

    Map<String, Object> getRatingStats(Integer studentId);

    boolean reportStudyRecord(StudyRecordDTO dto);
}
