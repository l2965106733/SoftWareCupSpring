package com.dream.softwarecupspring.Mapper.AdminMapper;

import com.dream.softwarecupspring.pojo.Resource.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminResourceMapper {
    List<Resource> getAllResources();

    void deleteResource(Integer resourceId);

    void updateResource(Resource resource);

    Long getTotalResources();

    Long getTotalSize();

    Long getTotalDownloads();

    Long getTotalViews();

    Long getTeacherCount();
}
