package com.dream.softwarecupspring.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {
    private Long id;
    private Long userId;
    private LocalDate loginDate;
    private Integer sessionDuration;
    private Integer pageViews;
    private Integer actionsCount;
    private LocalDateTime createdTime;
}
