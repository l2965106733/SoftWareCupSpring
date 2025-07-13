package com.dream.softwarecupspring.pojo.Interact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerQueryParam {
    private Integer questionId;
    private Integer teacherId;
    private String answer;
    private LocalDateTime answerTime;
}
