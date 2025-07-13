package com.dream.softwarecupspring.pojo.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceStats {
    private Long totalResources;
    private Long weeklyUploads;
    private Long viewCount;
    private Long downloadCount;
    private List<PopularResource> popularResources;
    private Long totalSize;
    private Long teacherCount;
}