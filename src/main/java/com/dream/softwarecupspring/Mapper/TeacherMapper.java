package com.dream.softwarecupspring.Mapper;

import com.dream.softwarecupspring.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {

    void saveAll(List<Question> questions);

    void saveHomework(Homework homework);

    void insertHomeworkQuestions(List<HomeworkQuestion> questionMappings);

    List<Homework> getHomeworkByTeacherId(Integer teacherId);

    List<StudentQuestion> getStudentQuestions(Integer teacherId);

    void addTeacherAnswer(AnswerQueryParam answerQueryParam);

    List<Question> getHomeworkDetail(Integer homeworkId);

    List<StudentHomework> getStudentSubmissions(Integer homeworkId);

    void insertResource(Resource resource);

    void updateResource(Resource resource);

    List<Resource> selectResourcesByTeacherId(Long teacherId);

    void deleteResourceById(Long resourceId);
}
