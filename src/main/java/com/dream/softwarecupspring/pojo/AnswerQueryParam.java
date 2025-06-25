package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerQueryParam {
    private Integer questionId;
    private Integer teacherId;
    private String answer;
}
