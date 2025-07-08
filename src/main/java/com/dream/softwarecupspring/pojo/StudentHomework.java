package com.dream.softwarecupspring.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentHomework {
    private Integer id;
    private Integer homeworkId;
    private Integer studentId;
    private Integer status; // 0:未提交, 1:已提交, 2:已批改
    private Integer totalScore; // 重命名为totalScore，与前端保持一致
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submitTime;
    private Map<String, String> answers;
    private String feedback;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String studentName;      // 学生姓名
    private Map<String, Integer> scores;  // 各题得分 {questionId: score}

    public StudentHomework(Integer homeworkId, Integer studentId, Integer status) {
        this.homeworkId = homeworkId;
        this.studentId = studentId;
        this.status = status;
    }
}