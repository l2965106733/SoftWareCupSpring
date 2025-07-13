package com.dream.softwarecupspring.Service.ServiceImpl.TeacherServiceImpl;

import com.dream.softwarecupspring.Mapper.StudentMapper.StudentHomeworkMapper;
import com.dream.softwarecupspring.Mapper.StudentMapper.StudentStudyMapper;
import com.dream.softwarecupspring.Mapper.TeacherMapper.TeacherHomeworkMapper;
import com.dream.softwarecupspring.Service.TeacherService.TeacherHomeworkService;
import com.dream.softwarecupspring.pojo.Homework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherHomeworkServiceImpl implements TeacherHomeworkService {
    @Autowired
    private TeacherHomeworkMapper teacherHomeworkMapper;

    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

    @Autowired
    private StudentStudyMapper studentStudyMapper;

    @Override
    public List<Question> saveQuestion(List<Question> questions) {
        for (Question question : questions) {
            question.setCreatedTime(LocalDateTime.now());
            question.setUpdatedTime(LocalDateTime.now());
        }
        teacherHomeworkMapper.saveAll(questions);
        return questions;
    }

    // ======================== 作业发布 ========================

    /**
     * 发布作业：创建作业、题目绑定、分配给学生
     */
    @Override
    @Transactional
    public void publishHomework(Homework param) {
        param.setCreatedTime(LocalDateTime.now());
        param.setUpdatedTime(LocalDateTime.now());
        param.setStatus(1);
        teacherHomeworkMapper.saveHomework(param);

        Integer homeworkId = param.getId();
        Integer teacherId = param.getTeacherId();
        List<Integer> questionIds = param.getQuestionIds();

        // 绑定题目顺序
        List<HomeworkQuestion> questionMappings = new ArrayList<>();
        for (int i = 0; i < questionIds.size(); i++) {
            HomeworkQuestion mapping = new HomeworkQuestion();
            mapping.setHomeworkId(homeworkId);
            mapping.setQuestionId(questionIds.get(i));
            mapping.setQuestionOrder(i + 1);
            questionMappings.add(mapping);
        }
        teacherHomeworkMapper.insertHomeworkQuestions(questionMappings);

        // 获取学生并初始化作业记录
        List<Integer> studentIds = studentStudyMapper.getStudentIdsByTeacherId(teacherId);
        List<StudentHomework> studentHomeworkList = studentIds.stream()
                .map(sid -> new StudentHomework(homeworkId, sid, 0,LocalDateTime.now()))
                .collect(Collectors.toList());
        studentHomeworkMapper.insertStudentHomework(studentHomeworkList);
    }

    // ======================== 作业查看与批改 ========================

    /**
     * 获取教师布置的作业列表
     */
    @Override
    public List<Homework> getHomeworkList(Integer teacherId) {
        return teacherHomeworkMapper.getHomeworkByTeacherId(teacherId);
    }

    /**
     * 获取作业详情（题目列表）
     */
    @Override
    public List<Question> getHomeworkDetail(Integer homeworkId) {
        return teacherHomeworkMapper.getHomeworkDetail(homeworkId);
    }

    @Override
    public List<Map<String, Object>> getHomeworkDetailWithAnswer(Long homeworkId, Long studentId) {
        return teacherHomeworkMapper.getHomeworkDetailWithAnswer(homeworkId, studentId);
    }

    /**
     * 获取学生提交的作业列表
     */
    @Override
    public List<StudentHomework> getStudentSubmissions(Integer homeworkId) {
        return teacherHomeworkMapper.getStudentSubmissions(homeworkId);
    }

    /**
     * 教师批改作业：设置状态 + 批量评分
     */
    @Override
    public String gradeHomework(StudentHomework studentHomework) {
        studentHomework.setStatus(2);
        studentHomework.setUpdatedTime(LocalDateTime.now());
        studentHomeworkMapper.updateHomeworkById(studentHomework);
        StudentHomework homework = studentHomeworkMapper.selectHomeworkByHomeworkId(studentHomework.getHomeworkId(), studentHomework.getStudentId());
        System.out.println(homework);
        System.out.println(studentHomework);
        updateStudentAnswers(studentHomework);
        if (homework == null) {
            return "作业提交记录不存在";
        }
        if (homework.getStatus() == 2) {
            return "该作业已经批改过了";
        }
        return "批改成功";
    }


    // 更新每道题的得分
    private void updateStudentAnswers(StudentHomework studentHomework) {
        for (Map.Entry<String, Integer> entry : studentHomework.getScores().entrySet()) {
            Integer questionId = Integer.parseInt(entry.getKey());
            Integer score = entry.getValue();

            StudentAnswer answer = new StudentAnswer();
            answer.setHomeworkId(studentHomework.getHomeworkId());
            answer.setStudentId(studentHomework.getStudentId());
            answer.setQuestionId(questionId);
            answer.setScore(score);
            answer.setUpdatedTime(LocalDateTime.now());

            studentHomeworkMapper.updateScoreByHomeworkAndQuestion(answer);
        }
    }


    private Integer getTotalHomeworkCount(Integer teacherId) {
        return studentStudyMapper.countTotalHomework(teacherId);
    }

    private Integer getPublishedHomeworkCount(Integer teacherId) {
        return studentStudyMapper.countPublishedHomework(teacherId);
    }

    private Integer getGradedHomeworkCount(Integer teacherId) {
        return studentStudyMapper.countGradedHomework(teacherId);
    }

    private Integer getPendingGradeCount(Integer teacherId) {
        return studentStudyMapper.countPendingGrade(teacherId);
    }

    private Double getSubmitRate(Integer teacherId) {
        return studentStudyMapper.getAverageSubmitRate(teacherId);
    }

    private List<Map<String, Object>> getRecentHomework(Integer teacherId) {
        return studentStudyMapper.getRecentHomeworkByTeacherId(teacherId);
    }

    private Map<String, Integer> getScoreDistribution(Integer teacherId) {
        List<Map<String, Object>> distribution = studentStudyMapper.getScoreDistributionByTeacherId(teacherId);
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> item : distribution) {
            String range = (String) item.get("score_range");
            Integer count = (Integer) item.get("count");
            result.put(range, count);
        }
        return result;
    }
}
