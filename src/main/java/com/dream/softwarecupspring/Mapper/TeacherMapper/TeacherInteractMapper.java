package com.dream.softwarecupspring.Mapper.TeacherMapper;

import com.dream.softwarecupspring.pojo.Homework.*;
import com.dream.softwarecupspring.pojo.Interact.AnswerQueryParam;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import com.dream.softwarecupspring.pojo.Overall.StudentSummary;
import com.dream.softwarecupspring.pojo.Resource.PopularResource;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherInteractMapper {

    /**
     * 获取该教师收到的学生问题列表
     */
    List<StudentQuestion> getStudentQuestions(Integer teacherId);

    /**
     * 教师为学生添加 AI 生成的回答
     */
    void addTeacherAnswer(AnswerQueryParam answerQueryParam);

    List<Map<String, Object>> getTeacherRatings(Long teacherId);
}
