package com.dream.softwarecupspring.Service.ServiceImpl.StudentServiceImpl;

import com.dream.softwarecupspring.Mapper.StudentMapper.StudentInteractMapper;
import com.dream.softwarecupspring.Service.StudentService.StudentInteractService;
import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentInteractServiceImpl implements StudentInteractService {

    @Autowired
    private StudentInteractMapper studentInteractMapper;


    @Override
    public void submitQuestion(StudentQuestion studentQuestion) {
        studentQuestion.setStatus(0); // 待回答
        studentQuestion.setCreatedTime(LocalDateTime.now());
        studentQuestion.setUpdatedTime(LocalDateTime.now());
        studentInteractMapper.insertQuestion(studentQuestion);
    }

    @Override
    public List<StudentQuestion> getMyQuestions(Integer studentId) {
        return studentInteractMapper.getQuestionsByStudentId(studentId);
    }

    @Override
    public StudentQuestion getQuestionDetail(Integer questionId) {
        return studentInteractMapper.selectQuestionById(questionId);
    }

    @Override
    public void rateAnswer(Integer questionId, Integer rating) {
        StudentQuestion question = new StudentQuestion();
        question.setId(questionId);
        question.setRating(rating);
        question.setUpdatedTime(LocalDateTime.now());
        studentInteractMapper.updateQuestionById(question);
    }

    @Override
    public Map<String, Object> getInteractStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalQuestions", studentInteractMapper.getTotalQuestionsCount(studentId));
        stats.put("answeredQuestions", studentInteractMapper.getAnsweredQuestionsCount(studentId));
        stats.put("pendingQuestions", studentInteractMapper.getPendingQuestionsCount(studentId));
        stats.put("avgRating", studentInteractMapper.getAverageRating(studentId));
        stats.put("questionsByType", studentInteractMapper.getQuestionsByType(studentId));
        stats.put("recentQuestions", studentInteractMapper.getRecentQuestions(studentId));
        return stats;
    }

    @Override
    public void submitRating(Integer questionId, Integer rating) {
        StudentQuestion question = new StudentQuestion();
        question.setId(questionId);
        question.setRating(rating);
        question.setUpdatedTime(LocalDateTime.now());
        studentInteractMapper.updateQuestionById(question);
    }

    @Override
    public Integer getRating(Integer questionId) {
        StudentQuestion question = studentInteractMapper.selectQuestionById(questionId);
        return question != null ? question.getRating() : null;
    }

    @Override
    public List<StudentQuestion> getRatingHistory(Integer studentId) {
        return studentInteractMapper.getRatedQuestionsByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getRatingStats(Integer studentId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRatings", studentInteractMapper.getTotalRatingsCount(studentId));
        stats.put("avgRating", studentInteractMapper.getAverageRatingByStudent(studentId));
        stats.put("ratingDistribution", studentInteractMapper.getRatingDistribution(studentId));
        stats.put("recentRatings", studentInteractMapper.getRecentRatedQuestions(studentId));
        return stats;
    }

    @Override
    public Map<String, Object> getHomeStats(Long studentId) {
        return studentInteractMapper.getHomeStats(studentId);
    }

    @Override
    public List<Map<String, Object>> getRecentActivities(Long studentId) {
        return studentInteractMapper.getRecentActivities(studentId);
    }



    @Override
    public Object getTeacherId(Long studentId) {
        return studentInteractMapper.getTeacherIdByStudentId(studentId);
    }
}
