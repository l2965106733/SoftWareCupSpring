package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentHomework {
    private Integer id;
    private String title;
    private Integer submittedCount;
    private Integer totalStudents;
    private Integer gradedCount;
}