package com.dream.softwarecupspring.Service.TeacherService;

import com.dream.softwarecupspring.pojo.Interact.AnswerQueryParam;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;

import java.util.List;
import java.util.Map;

public interface TeacherInteractService {
    void sendStudentAnswer(AnswerQueryParam answerQueryParam);

    List<StudentQuestion> getStudentQuestions(Integer teacherId);

    List<Map<String,Object>> getTeacherRatings(Long teacherId);
}
