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

    // ==================== 【学习行为模块】 ====================

    /**
     * 获取学生课件学习列表
     */
    @Override
    public List<Map<String, Object>> getCoursewareList(Integer studentId) {
        return studentMapper.getCoursewareListByStudentId(studentId);
    }

    /**
     * 获取学生学习统计数据
     */
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

    /**
     * 插入 AI 提问记录
     */
    @Override
    public void recordAiQuestion(AiQuestion aiQuestion) {
        aiQuestion.setCreatedTime(LocalDateTime.now());
        aiQuestion.setUpdatedTime(LocalDateTime.now());
        studentMapper.insertAiQuestion(aiQuestion);
    }

    // ==================== 【作业模块】 ====================

    /**
     * 获取学生作业列表
     */
    @Override
    public List<Map<String, Object>> getHomeworkList(Integer studentId) {
        return studentMapper.getHomeworkListByStudentId(studentId);
    }

    /**
     * 获取作业详情
     */
    @Override
    public List<Map<String, Object>> getHomeworkDetail(Integer homeworkId) {
        return studentMapper.getHomeworkDetailById(homeworkId);
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

        saveStudentAnswers(studentHomework); // 保存答题记录
    }

    private void saveStudentAnswers(StudentHomework studentHomework) {
        Map<String, String> answerMap = studentHomework.getAnswers();
        if (answerMap != null && !answerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : answerMap.entrySet()) {
                Integer questionId = Integer.parseInt(entry.getKey());
                String content = entry.getValue();

                StudentAnswer answer = new StudentAnswer();
                answer.setHomeworkId(studentHomework.getId());
                answer.setStudentId(studentHomework.getStudentId());
                answer.setQuestionId(questionId);
                answer.setAnswer(content);
                answer.setUpdatedTime(LocalDateTime.now());
                answer.setCreatedTime(LocalDateTime.now());

                studentMapper.saveOrUpdateAnswer(answer);
            }
        }
    }


    /**
     * 获取学生作业统计信息
     */
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

    // ==================== 【互动问答模块】 ====================

    /**
     * 提交学生提问
     */
    @Override
    public void submitQuestion(StudentQuestion studentQuestion) {
        studentQuestion.setStatus(0); // 待回答
        studentQuestion.setCreatedTime(LocalDateTime.now());
        studentQuestion.setUpdatedTime(LocalDateTime.now());
        studentMapper.insertQuestion(studentQuestion);
    }

    /**
     * 获取学生提交的问题列表
     */
    @Override
    public List<StudentQuestion> getMyQuestions(Integer studentId) {
        return studentMapper.getQuestionsByStudentId(studentId);
    }

    /**
     * 获取问题详情
     */
    @Override
    public StudentQuestion getQuestionDetail(Integer questionId) {
        return studentMapper.selectQuestionById(questionId);
    }

    /**
     * 对问题回答进行评分
     */
    @Override
    public void rateAnswer(Integer questionId, Integer rating) {
        StudentQuestion question = new StudentQuestion();
        question.setId(questionId);
        question.setRating(rating);
        question.setUpdatedTime(LocalDateTime.now());
        studentMapper.updateQuestionById(question);
    }

    /**
     * 获取当前学生对应教师ID
     */
    @Override
    public Integer getTeacherId(Integer studentId) {
        return studentMapper.selectTeacherIdByStudentId(studentId);
    }

    /**
     * 获取互动统计信息
     */
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

    // ==================== 【评分模块】 ====================

    /**
     * 提交评分（直接更新问题评分）
     */
    @Override
    public void submitRating(Integer questionId, Integer rating) {
        StudentQuestion question = new StudentQuestion();
        question.setId(questionId);
        question.setRating(rating);
        question.setUpdatedTime(LocalDateTime.now());
        studentMapper.updateQuestionById(question);
    }

    /**
     * 获取单个问题评分
     */
    @Override
    public Integer getRating(Integer questionId) {
        StudentQuestion question = studentMapper.selectQuestionById(questionId);
        return question != null ? question.getRating() : null;
    }

    /**
     * 获取学生评分历史（已评分问题）
     */
    @Override
    public List<StudentQuestion> getRatingHistory(Integer studentId) {
        return studentMapper.getRatedQuestionsByStudentId(studentId);
    }

    /**
     * 获取学生评分统计信息
     */
    @Override
    public Map<String, Object> getRatingStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRatings", studentMapper.getTotalRatingsCount(studentId));
        stats.put("avgRating", studentMapper.getAverageRatingByStudent(studentId));
        stats.put("ratingDistribution", studentMapper.getRatingDistribution(studentId));
        stats.put("recentRatings", studentMapper.getRecentRatedQuestions(studentId));
        return stats;
    }

    @Override
    public boolean reportStudyRecord(StudyRecordDTO dto) {
        StudyRecord record = studentMapper.findByStudentAndResource(dto.getStudentId(), dto.getResourceId());

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
            studentMapper.insertStudyRecord(newRecord);
            return true;
        } else if (record != null) {
            int added = dto.getSessionDuration() != null ? dto.getSessionDuration() : 0;
            studentMapper.updateStudyRecord(dto.getStudentId(), dto.getResourceId(),dto.getAction(),
                    dto.getTimestamp(), added, dto.getStudyStatus());
            return true;
        }
        return false;
    }

}
