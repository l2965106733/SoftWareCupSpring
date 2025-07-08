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

    // ======================== 题库管理 ========================

    /**
     * 保存教师添加的题目（支持 AI 生成后再确认）
     */
    @Override
    public List<Question> saveQuestion(List<Question> questions) {
        for (Question question : questions) {
            question.setCreateTime(LocalDateTime.now());
            question.setUpdateTime(LocalDateTime.now());
        }
        teacherMapper.saveAll(questions);
        return questions;
    }

    // ======================== 作业发布 ========================

    /**
     * 发布作业：创建作业、题目绑定、分配给学生
     */
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

        // 绑定题目顺序
        List<HomeworkQuestion> questionMappings = new ArrayList<>();
        for (int i = 0; i < questionIds.size(); i++) {
            HomeworkQuestion mapping = new HomeworkQuestion();
            mapping.setHomeworkId(homeworkId);
            mapping.setQuestionId(questionIds.get(i));
            mapping.setQuestionOrder(i + 1);
            questionMappings.add(mapping);
        }
        teacherMapper.insertHomeworkQuestions(questionMappings);

        // 获取学生并初始化作业记录
        List<Integer> studentIds = studentMapper.getStudentIdsByTeacherId(teacherId);
        List<StudentHomework> studentHomeworkList = studentIds.stream()
                .map(sid -> new StudentHomework(homeworkId, sid, 0))
                .collect(Collectors.toList());
        studentMapper.insertStudentHomework(studentHomeworkList);
    }

    // ======================== 作业查看与批改 ========================

    /**
     * 获取教师布置的作业列表
     */
    @Override
    public List<Homework> getHomeworkList(Integer teacherId) {
        return teacherMapper.getHomeworkByTeacherId(teacherId);
    }

    /**
     * 获取作业详情（题目列表）
     */
    @Override
    public List<Question> getHomeworkDetail(Integer homeworkId) {
        return teacherMapper.getHomeworkDetail(homeworkId);
    }

    /**
     * 获取学生提交的作业列表
     */
    @Override
    public List<StudentHomework> getStudentSubmissions(Integer homeworkId) {
        return teacherMapper.getStudentSubmissions(homeworkId);
    }

    /**
     * 教师批改作业：设置状态 + 批量评分
     */
    @Override
    public void gradeHomework(StudentHomework studentHomework) {
        StudentHomework homework = studentMapper.selectHomeworkById(studentHomework.getId());
        if (homework == null) {
            throw new RuntimeException("作业提交记录不存在");
        }
        if (homework.getStatus() == 2) {
            throw new RuntimeException("该作业已经批改过了");
        }

        updateStudentHomework(studentHomework);
        updateStudentAnswers(studentHomework);
    }

    // 修改作业批改状态
    private void updateStudentHomework(StudentHomework studentHomework) {
        studentHomework.setStatus(2); // 2: 已批改
        studentHomework.setUpdatedTime(LocalDateTime.now());
        studentMapper.updateHomeworkById(studentHomework);
    }

    // 更新每道题的得分
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

    // ======================== 学生互动问答 ========================

    /**
     * 获取学生向教师提出的问题列表
     */
    @Override
    public List<StudentQuestion> getStudentQuestions(Integer teacherId) {
        return teacherMapper.getStudentQuestions(teacherId);
    }

    /**
     * 教师为学生问题添加 AI 生成的答案
     */
    @Override
    public void sendStudentAnswer(AnswerQueryParam answerQueryParam) {
        teacherMapper.addTeacherAnswer(answerQueryParam);
    }

    // ======================== 教学资源管理 ========================

    /**
     * 上传教学资源
     */
    @Override
    public Long uploadResource(Resource resource) {
        resource.setUploadTime(LocalDateTime.now());
        teacherMapper.insertResource(resource);
        return resource.getId();
    }

    /**
     * 更新教学资源
     */
    @Override
    public void updateResource(Resource resource) {
        teacherMapper.updateResource(resource);
    }

    /**
     * 获取教师上传的所有资源
     */
    @Override
    public List<Resource> getResourceList(Long teacherId) {
        return teacherMapper.selectResourcesByTeacherId(teacherId);
    }

    /**
     * 删除资源
     */
    @Override
    public void deleteResource(Long resourceId) {
        teacherMapper.deleteResourceById(resourceId);
    }

    // ======================== 作业统计（图表） ========================

    /**
     * 获取教师的作业布置与批改数据统计
     */
    @Override
    public HomeworkStats getHomeworkStats(Integer teacherId) {
        HomeworkStats stats = new HomeworkStats();
        stats.setTotalHomework(teacherMapper.getTotalHomework(teacherId));
        stats.setPublishedHomework(teacherMapper.getPublishedHomework(teacherId));
        stats.setGradedHomework(teacherMapper.getGradedHomework(teacherId));
        stats.setPendingGrade(teacherMapper.getPendingGrade(teacherId));
        stats.setSubmitRate(teacherMapper.getSubmitRate(teacherId));

        Map<String, Integer> scoreDist = new LinkedHashMap<>();
        scoreDist.put("90-100分", teacherMapper.getScoreRangeCount(teacherId, 90, 100));
        scoreDist.put("80-89分", teacherMapper.getScoreRangeCount(teacherId, 80, 89));
        scoreDist.put("70-79分", teacherMapper.getScoreRangeCount(teacherId, 70, 79));
        scoreDist.put("60-69分", teacherMapper.getScoreRangeCount(teacherId, 60, 69));
        scoreDist.put("60分以下", teacherMapper.getScoreRangeCount(teacherId, 0, 59));
        stats.setScoreDistribution(scoreDist);

        List<RecentHomework> recent = teacherMapper.getRecentHomework(teacherId);
        stats.setRecentHomework(recent);
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
    @Override
    public TeacherOverview getOverview(Integer teacherId) {
        Integer totalStudents = teacherMapper.getTotalStudents(teacherId);
        Integer newStudentsWeek = teacherMapper.getNewStudentsThisWeek(teacherId);
        Double homeworkRate = teacherMapper.getHomeworkRate(teacherId);
        Double homeworkTrend = teacherMapper.getHomeworkTrend(teacherId);
        Double avgScore = teacherMapper.getAvgScore(teacherId);
        Double scoreTrend = teacherMapper.getScoreTrend(teacherId);
        Double activeRate = teacherMapper.getActiveRate(teacherId);
        Double activeIncrease = teacherMapper.getActiveIncrease(teacherId);

        TeacherOverview vo = new TeacherOverview();
        vo.setTotalStudents(totalStudents);
        vo.setNewStudentsWeek(newStudentsWeek);
        vo.setHomeworkRate(homeworkRate);
        vo.setHomeworkTrend(homeworkTrend);
        vo.setAvgScore(avgScore);
        vo.setScoreTrend(scoreTrend);
        vo.setActiveRate(activeRate);
        vo.setActiveIncrease(activeIncrease);
        return vo;
    }

    @Override
    public List<StudentSummary> getStudentSummaries(Integer teacherId) {
        return teacherMapper.getStudentSummaries(teacherId);
    }

    @Override
    public ResourceStats getResourceStats(Integer teacherId) {
        ResourceStats stats = new ResourceStats();
        stats.setTotalResources(teacherMapper.getTotalResources(teacherId));
        stats.setWeeklyUploads(teacherMapper.getWeeklyUploads(teacherId));
        stats.setViewCount(teacherMapper.getResourceViewCount(teacherId));
        stats.setDownloadCount(teacherMapper.getResourceDownloadCount(teacherId));
        stats.setPopularResources(teacherMapper.getPopularResources(teacherId));
        return stats;
    }

    @Override
    public InteractStats getInteractStats(Integer teacherId) {
        InteractStats stats = new InteractStats();
        stats.setTotalQuestions(teacherMapper.getTotalQuestions(teacherId));
        stats.setAnsweredQuestions(teacherMapper.getAnsweredQuestions(teacherId));
        stats.setPendingQuestions(teacherMapper.getPendingQuestions(teacherId));
        stats.setAvgRating(teacherMapper.getAvgRating(teacherId));
        stats.setAvgResponseTime(teacherMapper.getAvgResponseTime(teacherId));
        stats.setRecentQuestions(teacherMapper.getRecentQuestions(teacherId));
        return stats;
    }
}
