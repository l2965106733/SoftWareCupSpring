package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PopularResource {
    private Integer id;
    private String fileName;
    private Integer viewCount;
    private Integer downloadCount;
}