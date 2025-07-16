package com.dream.softwarecupspring.Mapper.StudentMapper;

import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.Overall.StudyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudentStudyMapper {
    List<Map<String, Object>> getCoursewareListByStudentId(Integer studentId);
    List<Integer> getStudentIdsByTeacherId(Integer teacherId);
    Integer countTotalHomework(Integer teacherId);
    Integer countPublishedHomework(Integer teacherId);
    Integer countGradedHomework(Integer teacherId);
    Integer countPendingGrade(Integer teacherId);
    Double getAverageSubmitRate(Integer teacherId);
    List<Map<String, Object>> getRecentHomeworkByTeacherId(Integer teacherId);
    List<Map<String, Object>> getScoreDistributionByTeacherId(Integer teacherId);
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
    void updateStudyRecord(Long studentId, Long resourceId, String action, LocalDateTime timestamp, int added, Integer studyStatus);

    @Select("select teacher_id from teacher_student where student_id = #{studentId}")
    Integer selectTeacherIdByStudentId(Integer studentId);

    List<Map<String, Object>> getAiQuestions(@Param("studentId") Long studentId, @Param("limit") int limit);
    void insertAiQuestion(AiQuestion aiQuestion);

    List<Map<String, Object>> getStudyRecords(Long studentId, String type);

    List<Map<String, Object>> getStudyTimeTrend(@Param("studentId") Long studentId, @Param("days") int days);

    void updateResourceViewCount();

    void updateResourceDownloadCount();
}
