package com.dream.softwarecupspring.Service.ServiceImpl.StudentServiceImpl;

import com.dream.softwarecupspring.Mapper.StudentMapper.StudentHomeworkMapper;
import com.dream.softwarecupspring.Service.StudentService.StudentHomeworkService;
import com.dream.softwarecupspring.pojo.Homework.StudentAnswer;
import com.dream.softwarecupspring.pojo.Homework.StudentHomework;
import com.dream.softwarecupspring.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentHomeworkServiceImpl implements StudentHomeworkService {
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

    /**
     * 获取学生作业列表
     */
    @Override
    public List<Map<String, Object>> getHomeworkList(Integer studentId) {
        return studentHomeworkMapper.getHomeworkListByStudentId(studentId);
    }

    /**
     * 获取作业详情
     */
    @Override
    public List<Map<String, Object>> getHomeworkDetail(Integer homeworkId) {
        Integer studentId = CurrentHolder.getCurrentId();
        return studentHomeworkMapper.getHomeworkDetailById(homeworkId,studentId);
    }

    @Override
    public void saveHomeworkDraft(StudentHomework studentHomework) {
        saveOrUpdateHomework(studentHomework, false); // false = 草稿
    }

    @Override
    public void submitHomework(StudentHomework studentHomework) {
        saveOrUpdateHomework(studentHomework, true);  // true = 提交
    }

    private void saveOrUpdateHomework(StudentHomework studentHomework, boolean isSubmit) {
        studentHomework.setStatus(isSubmit ? 1 : 0);
        if (isSubmit) {
            studentHomework.setSubmitTime(LocalDateTime.now());
        }

        StudentHomework existing = studentHomeworkMapper.getByHomeworkAndStudent(
                studentHomework.getHomeworkId(), studentHomework.getStudentId());

        if (existing != null) {
            studentHomework.setId(existing.getId());
            studentHomeworkMapper.updateHomeworkById(studentHomework);
        } else {
            studentHomework.setCreatedTime(LocalDateTime.now());
            studentHomeworkMapper.insertHomework(studentHomework);
        }

        saveStudentAnswers(studentHomework); // 保存答题记录
    }

    private void saveStudentAnswers(StudentHomework studentHomework) {
        Map<String, String> answerMap = studentHomework.getAnswers();
        if (answerMap != null && !answerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : answerMap.entrySet()) {
                Integer questionId = Integer.parseInt(entry.getKey());
                String content = entry.getValue();

                StudentAnswer answer = new StudentAnswer();
                answer.setHomeworkId(studentHomework.getHomeworkId());
                answer.setStudentId(studentHomework.getStudentId());
                answer.setQuestionId(questionId);
                answer.setAnswer(content);
                answer.setUpdatedTime(LocalDateTime.now());
                answer.setCreatedTime(LocalDateTime.now());

                studentHomeworkMapper.saveOrUpdateAnswer(answer);
            }
        }
    }

    @Override
    public Map<String, Object> getHomeworkStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalHomework", studentHomeworkMapper.getTotalHomeworkCount(studentId));
        stats.put("submittedHomework", studentHomeworkMapper.getSubmittedHomeworkCount(studentId));
        stats.put("gradedHomework", studentHomeworkMapper.getGradedHomeworkCount(studentId));
        stats.put("avgScore", studentHomeworkMapper.getAverageScore(studentId));
        stats.put("bestScore", studentHomeworkMapper.getBestScore(studentId));
        stats.put("recentHomework", studentHomeworkMapper.getRecentHomeworkByStudentId(studentId));
        stats.put("scoreDistribution", studentHomeworkMapper.getScoreDistributionByStudentId(studentId));
        return stats;
    }
}
