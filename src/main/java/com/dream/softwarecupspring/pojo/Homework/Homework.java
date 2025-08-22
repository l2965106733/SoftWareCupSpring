package com.dream.softwarecupspring.pojo.Homework;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homework {
    private Integer id;
    private String title;
    private Integer teacherId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String remark;
    private Integer totalScore;
    private List<Integer> questionIds;
    private Integer status;
    private LocalDateTime createdTime; // 注册时间
    private LocalDateTime updatedTime; // 最后修改时间
}
