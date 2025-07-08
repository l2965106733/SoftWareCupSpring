package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeworkStats {
    private Integer totalHomework;
    private Integer publishedHomework;
    private Integer gradedHomework;
    private Integer pendingGrade;
    private Double submitRate;
    private Map<String, Integer> scoreDistribution;
    private List<RecentHomework> recentHomework;
}
