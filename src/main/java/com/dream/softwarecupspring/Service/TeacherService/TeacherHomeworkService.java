package com.dream.softwarecupspring.Service.TeacherService;

import com.dream.softwarecupspring.pojo.Homework.Homework;
import com.dream.softwarecupspring.pojo.Homework.Question;
import com.dream.softwarecupspring.pojo.Homework.StudentHomework;

import java.util.List;
import java.util.Map;

public interface TeacherHomeworkService {
    List<Question> saveQuestion(List<Question> questions);

    Integer publishHomework(Homework homeworkQueryParams);

    List<Homework> getHomeworkList(Integer teacherId);

    List<Question> getHomeworkDetail(Integer homeworkId);

    List<StudentHomework> getStudentSubmissions(Integer homeworkId);

    String gradeHomework(StudentHomework studentHomework);

    List<Map<String, Object>> getHomeworkDetailWithAnswer(Long homeworkId, Long studentId);
}
