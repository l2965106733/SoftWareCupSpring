package com.dream.softwarecupspring.pojo.Homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Delete;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResponse {
    private Integer score;
    private String feedback;
}
