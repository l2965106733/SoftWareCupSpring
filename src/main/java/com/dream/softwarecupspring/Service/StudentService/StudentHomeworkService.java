package com.dream.softwarecupspring.Service.StudentService;

import com.dream.softwarecupspring.pojo.Homework.StudentHomework;

import java.util.List;
import java.util.Map;

public interface StudentHomeworkService {
    List<Map<String, Object>> getHomeworkList(Integer studentId);

    List<Map<String, Object>> getHomeworkDetail(Integer homeworkId);

    void saveHomeworkDraft(StudentHomework studentHomework);

    void submitHomework(StudentHomework studentHomework);

    Map<String, Object> getHomeworkStats(Integer studentId);

}
