package com.dream.softwarecupspring.Service.TeacherService;

import com.dream.softwarecupspring.pojo.Resource.Resource;

import java.util.List;

public interface TeacherResourceService {

    Long uploadResource(Resource resource);

    void updateResource(Resource resource);

    List<Resource> getResourceList(Long teacherId);

    void deleteResource(Long resourceId);
}
