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
    private String answer;          // AI回答
    private LocalDateTime createdTime;
    private String questionContent;
    private String aiResponse;
    private String chatName;
    private Integer chatId;
    // 查询关联字段
    private String studentName;     // 学生姓名
}