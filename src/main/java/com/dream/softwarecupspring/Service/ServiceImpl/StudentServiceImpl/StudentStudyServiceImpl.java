package com.dream.softwarecupspring.Service.ServiceImpl.StudentServiceImpl;

import com.dream.softwarecupspring.Mapper.StudentMapper.StudentStudyMapper;
import com.dream.softwarecupspring.Service.StudentService.StudentStudyService;
import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Overall.StudyRecord;
import com.dream.softwarecupspring.pojo.Overall.StudyRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentStudyServiceImpl implements StudentStudyService {
    @Autowired
    private StudentStudyMapper studentStudyMapper;
    
    @Override
    public List<Map<String, Object>> getCoursewareList(Integer studentId) {
        return studentStudyMapper.getCoursewareListByStudentId(studentId);
    }

    @Override
    public boolean reportStudyRecord(StudyRecordDTO dto) {
        StudyRecord record = studentStudyMapper.findByStudentAndResource(dto.getStudentId(), dto.getResourceId());
        if ("download".equals(dto.getAction())) {
            studentStudyMapper.updateResourceDownloadCount(dto.getResourceId());
        }
        else {
            studentStudyMapper.updateResourceViewCount(dto.getResourceId());
        }
        if (record == null && ("start_study".equals(dto.getAction()) || "download".equals(dto.getAction()))) {
            StudyRecord newRecord = new StudyRecord();
            newRecord.setStudentId(dto.getStudentId());
            newRecord.setResourceId(dto.getResourceId());
            newRecord.setStudyStatus(0);
            newRecord.setFirstViewTime(dto.getTimestamp());
            newRecord.setLastViewTime(dto.getTimestamp());
            newRecord.setViewCount(1);
            newRecord.setStudyDuration(0);
            newRecord.setCreatedTime(LocalDateTime.now());
            newRecord.setUpdatedTime(LocalDateTime.now());
            studentStudyMapper.insertStudyRecord(newRecord);
            return true;
        } else if (record != null) {
            int added = dto.getSessionDuration() != null ? dto.getSessionDuration() : 0;
            studentStudyMapper.updateStudyRecord(dto.getStudentId(), dto.getResourceId(),dto.getAction(),
                    dto.getTimestamp(), added, dto.getStudyStatus());
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getStudyRecords(Long studentId, String type) {
        return studentStudyMapper.getStudyRecords(studentId, type);
    }

    @Override
    public List<Map<String, Object>> getStudyTimeTrend(Long studentId, int days) {
        return studentStudyMapper.getStudyTimeTrend(studentId, days);
    }

    @Override
    public Integer getTeacherId(Integer studentId) {
        return studentStudyMapper.selectTeacherIdByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getStudyStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudyTime", studentStudyMapper.getTotalStudyTime(studentId));
        stats.put("todayStudyTime", studentStudyMapper.getTodayStudyTime(studentId));
        stats.put("weekStudyTime", studentStudyMapper.getWeekStudyTime(studentId));
        stats.put("completedCourseware", studentStudyMapper.getCompletedCoursewareCount(studentId));
        stats.put("totalCourseware", studentStudyMapper.getTotalCoursewareCount(studentId));
        stats.put("avgProgress", studentStudyMapper.getAverageProgress(studentId));
        stats.put("studyDays", studentStudyMapper.getStudyDaysCount(studentId));
        stats.put("aiQuestions", studentStudyMapper.getAiQuestionsCount(studentId));
        stats.put("recentStudyRecords", studentStudyMapper.getRecentStudyRecords(studentId));
        return stats;
    }

    @Override
    public List<Map<String, Object>> getAiQuestions(Long studentId, int limit) {
        return studentStudyMapper.getAiQuestions(studentId, limit);
    }

    @Override
    public void recordAiQuestion(AiQuestion aiQuestion) {
        aiQuestion.setCreatedTime(LocalDateTime.now());
        studentStudyMapper.insertAiQuestion(aiQuestion);
    }
}
