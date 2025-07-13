package com.dream.softwarecupspring.Service.ServiceImpl.TeacherServiceImpl;

import com.dream.softwarecupspring.Mapper.TeacherMapper.TeacherInteractMapper;
import com.dream.softwarecupspring.Service.TeacherService.TeacherInteractService;
import com.dream.softwarecupspring.pojo.Interact.AnswerQueryParam;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TeacherInteractServiceImpl implements TeacherInteractService {
    @Autowired
    private TeacherInteractMapper teacherInteractMapper;
    
    @Override
    public List<StudentQuestion> getStudentQuestions(Integer teacherId) {
        return teacherInteractMapper.getStudentQuestions(teacherId);
    }

    /**
     * 教师为学生问题添加 AI 生成的答案
     */
    @Override
    public void sendStudentAnswer(AnswerQueryParam answerQueryParam) {
        answerQueryParam.setAnswerTime(LocalDateTime.now());
        teacherInteractMapper.addTeacherAnswer(answerQueryParam);
    }

    @Override
    public List<Map<String, Object>> getTeacherRatings(Long teacherId) {
        return teacherInteractMapper.getTeacherRatings(teacherId);
    }
}
