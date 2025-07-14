package com.dream.softwarecupspring.Service.StudentService;

import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Overall.StudyRecordDTO;

import java.util.List;
import java.util.Map;

public interface StudentStudyService {
    Integer getTeacherId(Integer studentId);

    boolean reportStudyRecord(StudyRecordDTO dto);

    Object getStudyRecords(Long studentId, String type);

    List<Map<String, Object>> getStudyTimeTrend(Long studentId, int days);

    List<Map<String, Object>> getCoursewareList(Integer studentId);

    Map<String, Object> getStudyStats(Integer studentId);

    Object getAiQuestions(Long studentId, int limit);

    void recordAiQuestion(AiQuestion aiQuestion);
}
