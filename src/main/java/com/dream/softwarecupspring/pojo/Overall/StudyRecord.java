package com.dream.softwarecupspring.pojo.Overall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class StudyRecord {
    private Long id;
    private Long studentId;
    private Long resourceId;
    private Integer studyStatus;
    private LocalDateTime firstViewTime;
    private LocalDateTime lastViewTime;
    private Integer viewCount;
    private Integer studyDuration;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}