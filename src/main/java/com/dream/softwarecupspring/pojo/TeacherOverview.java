package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherOverview {
    private Integer totalStudents;
    private Integer newStudentsWeek;
    private Double homeworkRate;
    private Double homeworkTrend;
    private Double avgScore;
    private Double scoreTrend;
    private Double activeRate;
    private Double activeIncrease;
}