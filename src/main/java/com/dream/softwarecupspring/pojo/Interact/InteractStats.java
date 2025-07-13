package com.dream.softwarecupspring.pojo.Interact;

import com.dream.softwarecupspring.pojo.Homework.RecentQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InteractStats {
    private Integer totalQuestions;
    private Integer answeredQuestions;
    private Integer pendingQuestions;
    private Double avgRating;
    private Double avgResponseTime;
    private List<RecentQuestion> recentQuestions;
}
