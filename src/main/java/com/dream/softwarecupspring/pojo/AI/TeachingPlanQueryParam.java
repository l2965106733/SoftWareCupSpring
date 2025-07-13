package com.dream.softwarecupspring.pojo.AI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachingPlanQueryParam {
    private String remark;
    private List<String> fileUrls;
}