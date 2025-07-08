package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyRecordDTO {
    private Long studentId;
    private Long resourceId;
    private String action; // start_study | update_progress | end_study
    private Integer sessionDuration;
    private Integer studyStatus;
    private LocalDateTime timestamp;
}
