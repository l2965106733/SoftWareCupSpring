package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TquestionQueryParam {
    private String knowledge;
    private String type;
    private Integer count;
    private String remark;
}
