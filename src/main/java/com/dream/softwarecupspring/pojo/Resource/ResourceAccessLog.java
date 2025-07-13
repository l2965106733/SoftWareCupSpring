package com.dream.softwarecupspring.pojo.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceAccessLog {
    private Long id;
    private Long resourceId;
    private Long userId;
    private String actionType;
    private LocalDateTime accessTime;
    private String ipAddress;
}
