package com.dream.softwarecupspring.Mapper;

import com.dream.softwarecupspring.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {

    // ==================== 题库管理 ====================

    /**
     * 批量保存教师添加的题目
     */
    void saveAll(List<Question> questions);

    // ==================== 作业发布与关联 ====================

    /**
     * 保存作业主信息（基本作业记录）
     */
    void saveHomework(Homework homework);

    /**
     * 插入作业与题目的关联关系（含题目顺序）
     */
    void insertHomeworkQuestions(List<HomeworkQuestion> questionMappings);

    /**
     * 获取教师布置的作业列表
     */
    List<Homework> getHomeworkByTeacherId(Integer teacherId);

    /**
     * 获取作业详情（返回题目列表）
     */
    List<Question> getHomeworkDetail(Integer homeworkId);

    /**
     * 获取学生提交的作业列表
     */
    List<StudentHomework> getStudentSubmissions(Integer homeworkId);

    // ==================== 学生提问与教师答复 ====================

    /**
     * 获取该教师收到的学生问题列表
     */
    List<StudentQuestion> getStudentQuestions(Integer teacherId);

    /**
     * 教师为学生添加 AI 生成的回答
     */
    void addTeacherAnswer(AnswerQueryParam answerQueryParam);

    // ==================== 教学资源管理 ====================

    /**
     * 插入教学资源
     */
    void insertResource(Resource resource);

    /**
     * 更新教学资源
     */
    void updateResource(Resource resource);

    /**
     * 根据教师 ID 查询其上传的所有资源
     */
    List<Resource> selectResourcesByTeacherId(Long teacherId);

    /**
     * 删除资源
     */
    void deleteResourceById(Long resourceId);

    Integer getTotalStudents(Integer teacherId);

    Integer getNewStudentsThisWeek(Integer teacherId);

    Double getHomeworkRate(Integer teacherId);

    Double getHomeworkTrend(Integer teacherId);

    Double getAvgScore(Integer teacherId);

    Double getScoreTrend(Integer teacherId);

    Double getActiveRate(Integer teacherId);

    Double getActiveIncrease(Integer teacherId);

    Integer getTotalHomework(Integer teacherId);
    Integer getPublishedHomework(Integer teacherId);
    Integer getGradedHomework(Integer teacherId);
    Integer getPendingGrade(Integer teacherId);
    Double getSubmitRate(Integer teacherId);
    Integer getScoreRangeCount(Integer teacherId, Integer min, Integer max);
    List<RecentHomework> getRecentHomework(Integer teacherId);

    List<StudentSummary> getStudentSummaries(Integer teacherId);

    Integer getTotalResources(Integer teacherId);

    Integer getWeeklyUploads(Integer teacherId);

    Integer getResourceViewCount(Integer teacherId);

    Integer getResourceDownloadCount(Integer teacherId);

    List<PopularResource> getPopularResources(Integer teacherId);


    Integer getTotalQuestions(Integer teacherId);

    Integer getAnsweredQuestions(Integer teacherId);

    Integer getPendingQuestions(Integer teacherId);

    Double getAvgRating(Integer teacherId);

    List<RecentQuestion> getRecentQuestions(Integer teacherId);

    Double getAvgResponseTime(Integer teacherId);
}
