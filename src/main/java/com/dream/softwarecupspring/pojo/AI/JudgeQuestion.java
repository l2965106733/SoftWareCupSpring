package com.dream.softwarecupspring.pojo.AI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeQuestion {
    private String question;
    private String studentAnswer;
    private String standardAnswer;
    private String totalScore;
}
