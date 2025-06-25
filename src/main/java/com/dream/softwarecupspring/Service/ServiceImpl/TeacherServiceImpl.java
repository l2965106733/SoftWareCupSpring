package com.dream.softwarecupspring.Service.ServiceImpl;

import com.dream.softwarecupspring.Mapper.StudentMapper;
import com.dream.softwarecupspring.Mapper.TeacherMapper;
import com.dream.softwarecupspring.Service.TeacherService;
import com.dream.softwarecupspring.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void saveQuestion(List<Question> questions) {
        for (Question question : questions) {
            question.setCreateTime(LocalDateTime.now());
            question.setUpdateTime(LocalDateTime.now());
        }
        teacherMapper.saveAll(questions);
    }

    @Override
    @Transactional
    public void publishHomework(Homework param) {
        param.setCreateTime(LocalDateTime.now());
        param.setUpdateTime(LocalDateTime.now());
        param.setStatus(1);
        teacherMapper.saveHomework(param);

        Integer homeworkId = param.getId();
        Integer teacherId = param.getTeacherId();
        List<Integer> questionIds = param.getQuestionIds();

        List<HomeworkQuestion> questionMappings = new ArrayList<>();
        for (int i = 0; i < questionIds.size(); i++) {
            HomeworkQuestion mapping = new HomeworkQuestion();
            mapping.setHomeworkId(homeworkId);
            mapping.setQuestionId(questionIds.get(i));
            mapping.setQuestionOrder(i + 1);
            questionMappings.add(mapping);
        }
        teacherMapper.insertHomeworkQuestions(questionMappings);

        List<Integer> studentIds = studentMapper.getStudentIdsByTeacherId(teacherId);

        List<StudentHomework> studentHomeworkList = studentIds.stream()
                .map(sid -> new StudentHomework(homeworkId, sid, 0))
                .collect(Collectors.toList());
        studentMapper.insertStudentHomework(studentHomeworkList);
    }

    @Override
    public List<Homework> getHomeworkList(Integer teacherId) {
        return teacherMapper.getHomeworkByTeacherId(teacherId);
    }

    @Override
    public List<StudentQuestion> getStudentQuestions(Integer teacherId) {
        return teacherMapper.getStudentQuestions(teacherId);
    }

    @Override
    public void sendStudentAnswer(AnswerQueryParam answerQueryParam) {
        teacherMapper.addTeacherAnswer(answerQueryParam);
    }

    @Override
    public List<Question> getHomeworkDetail(Integer homeworkId) {
        return teacherMapper.getHomeworkDetail(homeworkId);
    }

    @Override
    public List<StudentHomework> getStudentSubmissions(Integer homeworkId) {
        return teacherMapper.getStudentSubmissions(homeworkId);
    }

    @Override
    public Long uploadResource(Resource resource) {
        resource.setUploadTime(LocalDateTime.now());
        teacherMapper.insertResource(resource);
        return resource.getId();
    }

    @Override
    public void updateResource(Resource resource) {
        teacherMapper.updateResource(resource);
    }

    @Override
    public List<Resource> getResourceList(Long teacherId) {
        return teacherMapper.selectResourcesByTeacherId(teacherId);
    }

    @Override
    public void deleteResource(Long resourceId) {
        teacherMapper.deleteResourceById(resourceId);
    }

    @Override
    public void gradeHomework(StudentHomework studentHomework) {
        StudentHomework homework = studentMapper.selectById(studentHomework.getId());
        if (homework == null) {
            throw new RuntimeException("作业提交记录不存在");
        }
        if (homework.getStatus() == 2) {
            throw new RuntimeException("该作业已经批改过了");
        }

        updateStudentHomework(studentHomework);
        updateStudentAnswers(studentHomework);
    }

    private void updateStudentHomework(StudentHomework studentHomework) {
        studentHomework.setStatus(2); // 2: 已批改
        studentHomework.setUpdatedTime(LocalDateTime.now());
        studentMapper.updateById(studentHomework);
    }

    private void updateStudentAnswers(StudentHomework studentHomework) {
        for (Map.Entry<String, Integer> entry : studentHomework.getScores().entrySet()) {
            Integer questionId = Integer.parseInt(entry.getKey());
            Integer score = entry.getValue();

            StudentAnswer answer = new StudentAnswer();
            answer.setHomeworkId(studentHomework.getId());
            answer.setQuestionId(questionId);
            answer.setScore(score);
            answer.setUpdatedTime(LocalDateTime.now());

            studentMapper.updateScoreByHomeworkAndQuestion(answer);
        }
    }

    @Override
    public Map<String, Object> getHomeworkStats(Integer teacherId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalHomework", getTotalHomeworkCount(teacherId));
        stats.put("publishedHomework", getPublishedHomeworkCount(teacherId));
        stats.put("gradedHomework", getGradedHomeworkCount(teacherId));
        stats.put("pendingGrade", getPendingGradeCount(teacherId));
        stats.put("submitRate", getSubmitRate(teacherId));
        stats.put("recentHomework", getRecentHomework(teacherId));
        stats.put("scoreDistribution", getScoreDistribution(teacherId));
        return stats;
    }

    private Integer getTotalHomeworkCount(Integer teacherId) {
        return studentMapper.countTotalHomework(teacherId);
    }

    private Integer getPublishedHomeworkCount(Integer teacherId) {
        return studentMapper.countPublishedHomework(teacherId);
    }

    private Integer getGradedHomeworkCount(Integer teacherId) {
        return studentMapper.countGradedHomework(teacherId);
    }

    private Integer getPendingGradeCount(Integer teacherId) {
        return studentMapper.countPendingGrade(teacherId);
    }

    private Double getSubmitRate(Integer teacherId) {
        return studentMapper.getAverageSubmitRate(teacherId);
    }

    private List<Map<String, Object>> getRecentHomework(Integer teacherId) {
        return studentMapper.getRecentHomeworkByTeacherId(teacherId);
    }

    private Map<String, Integer> getScoreDistribution(Integer teacherId) {
        List<Map<String, Object>> distribution = studentMapper.getScoreDistributionByTeacherId(teacherId);
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> item : distribution) {
            String range = (String) item.get("score_range");
            Integer count = (Integer) item.get("count");
            result.put(range, count);
        }
        return result;
    }
}
