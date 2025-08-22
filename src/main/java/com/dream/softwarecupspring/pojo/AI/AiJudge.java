package com.dream.softwarecupspring.pojo.AI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiJudge {
    private String score;
    private String feedback;
}
