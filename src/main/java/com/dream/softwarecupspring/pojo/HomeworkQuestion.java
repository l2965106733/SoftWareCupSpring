package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkQuestion {
    private Integer homeworkId;
    private Integer questionId;
    private Integer questionOrder;
}