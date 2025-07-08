package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceStats {
    private Integer totalResources;
    private Integer weeklyUploads;
    private Integer viewCount;
    private Integer downloadCount;
    private List<PopularResource> popularResources;
}