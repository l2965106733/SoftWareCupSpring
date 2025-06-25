package com.dream.softwarecupspring.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuestion {
    private Integer id;
    private Integer studentId;      // 学生ID
    private Integer teacherId;      // 教师ID
    private String title;           // 问题标题
    private String content;         // 问题内容
    private String type;            // 问题类型：java, vue, database, frontend, other
    private Integer status;         // 状态：0-待回答，1-已回答
    private String answer;          // 教师回答内容
    private Integer rating;         // 学生评分：1-5分
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;  // 提问时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerTime; // 回答时间
    private LocalDateTime updatedTime;

    // 查询关联字段
    private String studentName;     // 学生姓名
    private String teacherName;     // 教师姓名
}