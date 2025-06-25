package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class StudyRecord {
    private Integer id;
    private Integer studentId;      // 学生ID
    private Integer resourceId;     // 资源ID
    private String resourceType;    // 资源类型：courseware-课件, video-视频, document-文档
    private Integer studyDuration;  // 学习时长（秒）
    private Integer progress;       // 学习进度（百分比）
    private LocalDateTime startTime;    // 开始时间
    private LocalDateTime endTime;      // 结束时间
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    // 查询关联字段
    private String resourceTitle;   // 资源标题
    private String teacherName;     // 教师姓名
}