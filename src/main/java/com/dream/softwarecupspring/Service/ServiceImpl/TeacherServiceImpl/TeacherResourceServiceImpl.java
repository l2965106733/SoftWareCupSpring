package com.dream.softwarecupspring.Service.ServiceImpl.TeacherServiceImpl;

import com.dream.softwarecupspring.Mapper.TeacherMapper.TeacherResourceMapper;
import com.dream.softwarecupspring.Service.TeacherService.TeacherResourceService;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherResourceServiceImpl implements TeacherResourceService {
    
    @Autowired
    private TeacherResourceMapper teacherResourceMapper;

    @Override
    public Long uploadResource(Resource resource) {
        resource.setUploadTime(LocalDateTime.now());
        teacherResourceMapper.insertResource(resource);
        return resource.getId();
    }

    /**
     * 更新教学资源
     */
    @Override
    public void updateResource(Resource resource) {
        teacherResourceMapper.updateResource(resource);
    }

    /**
     * 获取教师上传的所有资源
     */
    @Override
    public List<Resource> getResourceList(Long teacherId) {
        return teacherResourceMapper.selectResourcesByTeacherId(teacherId);
    }

    /**
     * 删除资源
     */
    @Override
    public void deleteResource(Long resourceId) {
        teacherResourceMapper.deleteResourceById(resourceId);
    }

}
