package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAnswer {
    private Integer id;
    private Integer homeworkId;  // 学生作业ID
    private Integer questionId;         // 题目ID
    private String studentAnswer;       // 学生答案
    private Integer score;              // 得分
    private Integer isCorrect;          // 是否正确：1-正确，0-错误
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    // 查询关联字段（非数据库字段，用于前端显示）
    private String questionContent;     // 题目内容
    private String questionType;        // 题目类型
    private Integer questionScore;      // 题目满分
}