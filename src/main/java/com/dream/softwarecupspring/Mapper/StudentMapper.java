package com.dream.softwarecupspring.Mapper;

import com.dream.softwarecupspring.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    // ==================== 教师端功能：学生作业统计 ====================

    List<Integer> getStudentIdsByTeacherId(Integer teacherId);
    Integer countTotalHomework(Integer teacherId);
    Integer countPublishedHomework(Integer teacherId);
    Integer countGradedHomework(Integer teacherId);
    Integer countPendingGrade(Integer teacherId);
    Double getAverageSubmitRate(Integer teacherId);
    List<Map<String, Object>> getRecentHomeworkByTeacherId(Integer teacherId);
    List<Map<String, Object>> getScoreDistributionByTeacherId(Integer teacherId);

    // ==================== 学生端功能：课件学习 ====================

    List<Map<String, Object>> getCoursewareListByStudentId(Integer studentId);

    // ==================== 学生端功能：学习统计 ====================

    Long getTotalStudyTime(Integer studentId);
    Long getTodayStudyTime(Integer studentId);
    Long getWeekStudyTime(Integer studentId);
    Integer getCompletedCoursewareCount(Integer studentId);
    Integer getTotalCoursewareCount(Integer studentId);
    Double getAverageProgress(Integer studentId);
    Integer getStudyDaysCount(Integer studentId);
    List<Map<String, Object>> getRecentStudyRecords(Integer studentId);
    void insertStudyRecord(StudyRecord record);
    StudyRecord findByStudentAndResource(Long studentId, Long resourceId);
    void updateStudyRecord(Long studentId, Long resourceId,String action,LocalDateTime timestamp, int added, Integer studyStatus);


    // ==================== 学生端功能：AI 提问记录 ====================

    void insertAiQuestion(AiQuestion aiQuestion);

    // ==================== 学生端功能：作业处理 ====================

    List<Map<String, Object>> getHomeworkListByStudentId(Integer studentId);
    List<Map<String, Object>> getHomeworkDetailById(Integer homeworkId);

    StudentHomework getByHomeworkAndStudent(@Param("homeworkId") Integer homeworkId,
                                            @Param("studentId") Integer studentId);

    void insertHomework(StudentHomework studentHomework); // 学生提交或草稿
    void insertStudentHomework(List<StudentHomework> studentHomeworkList); // 教师批量布置

    void updateHomeworkById(StudentHomework studentHomework);
    StudentHomework selectHomeworkById(Integer studentHomeworkId);

    void updateScoreByHomeworkAndQuestion(StudentAnswer answer);

    Integer getTotalHomeworkCount(Integer studentId);
    Integer getSubmittedHomeworkCount(Integer studentId);
    Integer getGradedHomeworkCount(Integer studentId);
    Double getAverageScore(Integer studentId);
    Integer getBestScore(Integer studentId);

    List<Map<String, Object>> getRecentHomeworkByStudentId(Integer studentId);
    Map<String, Object> getScoreDistributionByStudentId(Integer studentId);

    // ==================== 学生端功能：互动问答 ====================

    void insertQuestion(StudentQuestion question);
    List<StudentQuestion> getQuestionsByStudentId(Integer studentId);
    StudentQuestion selectQuestionById(Integer questionId);
    void updateQuestionById(StudentQuestion question);

    Integer getTotalQuestionsCount(Integer studentId);
    Integer getAnsweredQuestionsCount(Integer studentId);
    Integer getPendingQuestionsCount(Integer studentId);
    Double getAverageRating(Integer studentId);
    List<Map<String, Object>> getQuestionsByType(Integer studentId);
    List<StudentQuestion> getRecentQuestions(Integer studentId);

    // 获取绑定教师ID
    @Select("select teacher_id from teacher_student where student_id = #{studentId}")
    Integer selectTeacherIdByStudentId(Integer studentId);

    // ==================== 学生端功能：评分统计 ====================

    List<StudentQuestion> getRatedQuestionsByStudentId(Integer studentId);
    Object getTotalRatingsCount(Integer studentId);
    Object getAverageRatingByStudent(Integer studentId);
    Object getRatingDistribution(Integer studentId);
    Object getRecentRatedQuestions(Integer studentId);

    void saveOrUpdateAnswer(StudentAnswer answer);

}
