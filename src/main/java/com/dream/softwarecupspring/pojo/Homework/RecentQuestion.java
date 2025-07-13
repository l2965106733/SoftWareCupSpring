package com.dream.softwarecupspring.pojo.Homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecentQuestion {
    private Integer id;
    private String title;
    private String studentName;
    private String createdTime;
}
