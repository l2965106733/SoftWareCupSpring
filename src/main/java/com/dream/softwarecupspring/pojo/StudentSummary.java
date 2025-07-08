package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSummary {
    private Integer id;
    private String name;
    private String studentId;
    private Integer progress;
    private Double avgScore;
    private Integer homeworkCount;
    private Integer totalHomework;
    private String lastActive;
    private String className;
}
