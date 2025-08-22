package com.dream.softwarecupspring.Service.ServiceImpl.StudentServiceImpl;

import com.dream.softwarecupspring.Mapper.StudentMapper.StudentStudyMapper;
import com.dream.softwarecupspring.Service.StudentService.StudentStudyService;
import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Homework.Question;
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
        if (dto.getStudentId() == null || dto.getResourceId() == null) return false;
        if (dto.getTimestamp() == null) dto.setTimestamp(LocalDateTime.now());

        StudyRecord record = studentStudyMapper.findByStudentAndResource(dto.getStudentId(), dto.getResourceId());
        int added = dto.getSessionDuration() != null ? dto.getSessionDuration() : 0;

        if ("download".equals(dto.getAction())) {
            // 下载时：仅更新下载次数，如果记录不存在则创建状态为 0（未观看）
            studentStudyMapper.updateResourceDownloadCount(dto.getResourceId());

            if (record == null) {
                StudyRecord newRecord = new StudyRecord();
                newRecord.setStudentId(dto.getStudentId());
                newRecord.setResourceId(dto.getResourceId());
                newRecord.setStudyStatus(0); // 未观看
                newRecord.setFirstViewTime(dto.getTimestamp());
                newRecord.setLastViewTime(dto.getTimestamp());
                newRecord.setViewCount(1);
                newRecord.setStudyDuration(0);
                newRecord.setCreatedTime(LocalDateTime.now());
                newRecord.setUpdatedTime(LocalDateTime.now());
                studentStudyMapper.insertStudyRecord(newRecord);
            } else {
                // 更新为未观看状态
                studentStudyMapper.updateStudyRecord(
                        dto.getStudentId(),
                        dto.getResourceId(),
                        dto.getAction(),
                        dto.getTimestamp(),
                        0, // 无学习时长
                        0  // 未观看
                );
            }
            return true;

        }
        else if ("start_study".equals(dto.getAction())) {
            // 开始学习：更新或插入为学习中状态 1
            if (record == null) {
                StudyRecord newRecord = new StudyRecord();
                newRecord.setStudentId(dto.getStudentId());
                newRecord.setResourceId(dto.getResourceId());
                newRecord.setStudyStatus(1); // 学习中
                newRecord.setFirstViewTime(dto.getTimestamp());
                newRecord.setLastViewTime(dto.getTimestamp());
                newRecord.setViewCount(1);
                newRecord.setStudyDuration(added);
                newRecord.setCreatedTime(LocalDateTime.now());
                newRecord.setUpdatedTime(LocalDateTime.now());
                studentStudyMapper.insertStudyRecord(newRecord);
            } else {
                // 更新已有记录为学习中状态
                studentStudyMapper.updateStudyRecord(
                        dto.getStudentId(),
                        dto.getResourceId(),
                        dto.getAction(),
                        dto.getTimestamp(),
                        added,
                        1 // 学习中
                );
            }

//            studentStudyMapper.updateResourceViewCount(dto.getResourceId());
            return true;

        }
        else if ("end_study".equals(dto.getAction())) {
            // 结束学习：更新或插入为已完成状态 2
            if (record == null) {
                // 首次结束学习也要插入记录
                StudyRecord newRecord = new StudyRecord();
                newRecord.setStudentId(dto.getStudentId());
                newRecord.setResourceId(dto.getResourceId());
                newRecord.setStudyStatus(2); // 已完成
                newRecord.setFirstViewTime(dto.getTimestamp());
                newRecord.setLastViewTime(dto.getTimestamp());
                newRecord.setViewCount(1);
                newRecord.setStudyDuration(added);
                newRecord.setCreatedTime(LocalDateTime.now());
                newRecord.setUpdatedTime(LocalDateTime.now());
                studentStudyMapper.insertStudyRecord(newRecord);
            } else {
                // 更新为已完成状态
                studentStudyMapper.updateStudyRecord(
                        dto.getStudentId(),
                        dto.getResourceId(),
                        dto.getAction(),
                        dto.getTimestamp(),
                        added,
                        2 // 已完成
                );
            }

            studentStudyMapper.updateResourceViewCount(dto.getResourceId());
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
//        stats.put("aiQuestions", studentStudyMapper.getAiQuestionsCount(studentId));
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

    @Override
    public List<AiQuestion> getChatList(Long userId) {
        return studentStudyMapper.getChatList(userId);
    }


    @Override
    public List<AiQuestion> getChatDetailById(Long id) {
        return studentStudyMapper.getChatDetailById(id);
    }

    @Override
    public List<Question> getMistakes(Long studentId) {
        return studentStudyMapper.getMistakes(studentId);
    }

    @Override
    public String getChatName(Integer chatId) {
        return studentStudyMapper.getChatName(chatId);
    }

    @Override
    public void setChatName(Integer chatId, String name) {
        studentStudyMapper.setChatName(chatId,name);
    }

    @Override
    public void deleteChat(Integer chatId) {
        studentStudyMapper.deleteChat(chatId);
    }

}
