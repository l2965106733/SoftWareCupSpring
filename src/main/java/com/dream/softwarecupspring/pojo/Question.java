package com.dream.softwarecupspring.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer id;           // 题目ID
    private String content;       // 题目内容
    private String answer;        // 参考答案
    private String explain;       // 解析说明
    private String type;    // 题目类型（枚举）
    private Integer score;        // 分值
    private LocalDateTime createTime; // 注册时间
    private LocalDateTime updateTime; // 最后修改时间
}

