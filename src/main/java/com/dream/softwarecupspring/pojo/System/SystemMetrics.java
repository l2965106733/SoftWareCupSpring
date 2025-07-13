package com.dream.softwarecupspring.pojo.System;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemMetrics {
    private Long id;
    private BigDecimal cpuUsage;
    private BigDecimal memoryUsage;
    private BigDecimal diskUsage;
    private Long networkTraffic;
    private Integer databaseConnections;
    private Integer apiRequests;
    private BigDecimal responseTime;
    private BigDecimal errorRate;
    private LocalDateTime recordedTime;
}
