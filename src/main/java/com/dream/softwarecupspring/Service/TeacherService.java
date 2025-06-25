package com.dream.softwarecupspring.Service;

import com.dream.softwarecupspring.pojo.*;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    void saveQuestion(List<Question> questions);

    void publishHomework(Homework homeworkQueryParams);

    List<Homework> getHomeworkList(Integer teacherId);

    List<StudentQuestion> getStudentQuestions(Integer teacherId);

    void sendStudentAnswer(AnswerQueryParam answerQueryParam);

    List<Question> getHomeworkDetail(Integer homeworkId);

    List<StudentHomework> getStudentSubmissions(Integer homeworkId);

    Long uploadResource(Resource resource);

    void updateResource(Resource resource);

    List<Resource> getResourceList(Long teacherId);

    void deleteResource(Long resourceId);

    void gradeHomework(StudentHomework studentHomework);

    Map<String, Object> getHomeworkStats(Integer teacherId);
}
