package com.dream.softwarecupspring.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Resource {
    private Long id;
    private Long teacherId;
    private String resourceName;
    private String resourceUrl;
    private String resourceType;
    private Long fileSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

    private String description;
    private Integer downloadCount;
}