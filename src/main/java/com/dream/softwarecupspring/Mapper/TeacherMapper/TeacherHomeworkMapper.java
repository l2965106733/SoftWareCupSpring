package com.dream.softwarecupspring.Mapper.TeacherMapper;

import com.dream.softwarecupspring.pojo.Homework.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherHomeworkMapper {
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

    List<Map<String, Object>> getHomeworkDetailWithAnswer(Long homeworkId, Long studentId);
}
