package com.dream.softwarecupspring.Service.ServiceImpl;

import com.dream.softwarecupspring.Service.StudentService;
import com.dream.softwarecupspring.Mapper.StudentMapper;
import com.dream.softwarecupspring.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    // ==================== 学习相关方法 ====================

    @Override
    public List<Map<String, Object>> getCoursewareList(Integer studentId) {
        return studentMapper.getCoursewareListByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getStudyStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudyTime", studentMapper.getTotalStudyTime(studentId));
        stats.put("todayStudyTime", studentMapper.getTodayStudyTime(studentId));
        stats.put("weekStudyTime", studentMapper.getWeekStudyTime(studentId));
        stats.put("completedCourseware", studentMapper.getCompletedCoursewareCount(studentId));
        stats.put("totalCourseware", studentMapper.getTotalCoursewareCount(studentId));
        stats.put("avgProgress", studentMapper.getAverageProgress(studentId));
        stats.put("studyDays", studentMapper.getStudyDaysCount(studentId));
        stats.put("recentStudyRecords", studentMapper.getRecentStudyRecords(studentId));
        return stats;
    }

    @Override
    public void recordStudyBehavior(StudyRecord studyRecord) {
        studyRecord.setCreatedTime(LocalDateTime.now());
        studyRecord.setUpdatedTime(LocalDateTime.now());
        studentMapper.insertStudyRecord(studyRecord);
    }

    @Override
    public void recordAiQuestion(AiQuestion aiQuestion) {
        aiQuestion.setCreatedTime(LocalDateTime.now());
        aiQuestion.setUpdatedTime(LocalDateTime.now());
        studentMapper.insertAiQuestion(aiQuestion);
    }

    // ==================== 作业相关方法 ====================

    @Override
    public List<Map<String, Object>> getHomeworkList(Integer studentId) {
        return studentMapper.getHomeworkListByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getHomeworkDetail(Integer homeworkId) {
        return studentMapper.getHomeworkDetailById(homeworkId);
    }

    @Override
    public void saveHomeworkDraft(StudentHomework studentHomework) {
        studentHomework.setStatus(0); // 草稿
        studentHomework.setUpdatedTime(LocalDateTime.now());

        StudentHomework existing = studentMapper.getByHomeworkAndStudent(
                studentHomework.getHomeworkId(), studentHomework.getStudentId());

        if (existing != null) {
            studentHomework.setId(existing.getId());
            studentMapper.updateHomeworkById(studentHomework);
        } else {
            studentHomework.setCreatedTime(LocalDateTime.now());
            studentMapper.insertHomework(studentHomework);
        }

        saveStudentAnswers(studentHomework);
    }

    @Override
    public void submitHomework(StudentHomework studentHomework) {
        studentHomework.setStatus(1); // 提交
        studentHomework.setSubmitTime(LocalDateTime.now());
        studentHomework.setUpdatedTime(LocalDateTime.now());

        StudentHomework existing = studentMapper.getByHomeworkAndStudent(
                studentHomework.getHomeworkId(), studentHomework.getStudentId());

        if (existing != null) {
            studentHomework.setId(existing.getId());
            studentMapper.updateHomeworkById(studentHomework);
        } else {
            studentHomework.setCreatedTime(LocalDateTime.now());
            studentMapper.insertHomework(studentHomework);
        }

        saveStudentAnswers(studentHomework);
    }

    @Override
    public Map<String, Object> getHomeworkStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalHomework", studentMapper.getTotalHomeworkCount(studentId));
        stats.put("submittedHomework", studentMapper.getSubmittedHomeworkCount(studentId));
        stats.put("gradedHomework", studentMapper.getGradedHomeworkCount(studentId));
        stats.put("avgScore", studentMapper.getAverageScore(studentId));
        stats.put("bestScore", studentMapper.getBestScore(studentId));
        stats.put("recentHomework", studentMapper.getRecentHomeworkByStudentId(studentId));
        stats.put("scoreDistribution", studentMapper.getScoreDistributionByStudentId(studentId));
        return stats;
    }

    // ==================== 互动问答相关方法 ====================

    @Override
    public void submitQuestion(StudentQuestion studentQuestion) {
        studentQuestion.setStatus(0); // 待回答
        studentQuestion.setCreatedTime(LocalDateTime.now());
        studentQuestion.setUpdatedTime(LocalDateTime.now());
        studentMapper.insertQuestion(studentQuestion);
    }

    @Override
    public List<StudentQuestion> getMyQuestions(Integer studentId) {
        return studentMapper.getQuestionsByStudentId(studentId);
    }

    @Override
    public StudentQuestion getQuestionDetail(Integer questionId) {
        return studentMapper.selectQuestionById(questionId);
    }

    @Override
    public void rateAnswer(Integer questionId, Integer rating) {
        StudentQuestion question = new StudentQuestion();
        question.setId(questionId);
        question.setRating(rating);
        question.setUpdatedTime(LocalDateTime.now());
        studentMapper.updateQuestionById(question);
    }

    @Override
    public Integer getTeacherId(Integer studentId) {
        return  studentMapper.selectTeacherIdByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getInteractStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalQuestions", studentMapper.getTotalQuestionsCount(studentId));
        stats.put("answeredQuestions", studentMapper.getAnsweredQuestionsCount(studentId));
        stats.put("pendingQuestions", studentMapper.getPendingQuestionsCount(studentId));
        stats.put("avgRating", studentMapper.getAverageRating(studentId));
        stats.put("questionsByType", studentMapper.getQuestionsByType(studentId));
        stats.put("recentQuestions", studentMapper.getRecentQuestions(studentId));
        return stats;
    }

    // ==================== 私有辅助方法 ====================

    private void saveStudentAnswers(StudentHomework studentHomework) {
        if (studentHomework.getScores() != null) {
            for (Map.Entry<String, Integer> entry : studentHomework.getScores().entrySet()) {
                // TODO: 这里可以调用 studentMapper.saveOrUpdateAnswer(...)（待扩展）
            }
        }
    }
}
