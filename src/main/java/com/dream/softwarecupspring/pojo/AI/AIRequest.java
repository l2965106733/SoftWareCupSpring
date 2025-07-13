package com.dream.softwarecupspring.pojo.AI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIRequest {
    private String task;
    private Object data;
}
