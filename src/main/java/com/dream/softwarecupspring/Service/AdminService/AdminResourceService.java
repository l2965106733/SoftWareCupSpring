package com.dream.softwarecupspring.Service.AdminService;

import com.dream.softwarecupspring.pojo.Resource.Resource;
import com.dream.softwarecupspring.pojo.Resource.ResourceStats;

import java.util.List;

public interface AdminResourceService {
    List<Resource> getAllResources();

    void deleteResource(Integer resourceId);

    void updateResource(Resource resource);

    ResourceStats getResourceStats();


}
