package com.dream.softwarecupspring.Mapper.StudentMapper;

import com.dream.softwarecupspring.pojo.Homework.Question;
import com.dream.softwarecupspring.pojo.Homework.StudentAnswer;
import com.dream.softwarecupspring.pojo.Homework.StudentHomework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentHomeworkMapper {
    List<Map<String, Object>> getHomeworkListByStudentId(Integer studentId);
    List<Map<String, Object>> getHomeworkDetailById(Integer homeworkId,Integer studentId);

    StudentHomework getByHomeworkAndStudent(@Param("homeworkId") Integer homeworkId,
                                            @Param("studentId") Integer studentId);

    void insertHomework(StudentHomework studentHomework); // 学生提交或草稿
    void insertStudentHomework(List<StudentHomework> studentHomeworkList); // 教师批量布置

    void updateHomeworkById(StudentHomework studentHomework);
    StudentHomework selectHomeworkByHomeworkId(Integer homeworkId,Integer studentId);

    void updateScoreByHomeworkAndQuestion(StudentAnswer answer);

    Integer getTotalHomeworkCount(Integer studentId);
    Integer getSubmittedHomeworkCount(Integer studentId);
    Integer getGradedHomeworkCount(Integer studentId);
    Double getAverageScore(Integer studentId);
    Integer getBestScore(Integer studentId);

    List<Map<String, Object>> getRecentHomeworkByStudentId(Integer studentId);
    Map<String, Object> getScoreDistributionByStudentId(Integer studentId);

    void saveOrUpdateAnswer(StudentAnswer answer);

}
