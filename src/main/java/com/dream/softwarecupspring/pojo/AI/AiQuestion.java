package com.dream.softwarecupspring.pojo.AI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiQuestion {
    private Integer id;
    private Integer studentId;      // 学生ID
    private String question;        // 提问内容
    private String answer;          // AI回答
    private String category;        // 问题分类：java, vue, database, other
    private Integer satisfaction;   // 满意度评分（1-5）
    private LocalDateTime createdTime;

    // 查询关联字段
    private String studentName;     // 学生姓名
}