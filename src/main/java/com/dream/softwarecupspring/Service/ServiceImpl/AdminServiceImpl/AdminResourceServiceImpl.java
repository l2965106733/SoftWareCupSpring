package com.dream.softwarecupspring.Service.ServiceImpl.AdminServiceImpl;

import com.dream.softwarecupspring.Mapper.AdminMapper.AdminResourceMapper;
import com.dream.softwarecupspring.Service.AdminService.AdminResourceService;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import com.dream.softwarecupspring.pojo.Resource.ResourceStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminResourceServiceImpl implements AdminResourceService {
    @Autowired
    AdminResourceMapper adminResourceMapper;

    @Override
    public List<Resource> getAllResources() {
        return adminResourceMapper.getAllResources();
    }

    @Override
    public void deleteResource(Integer resourceId) {
        adminResourceMapper.deleteResource(resourceId);
    }

    @Override
    public void updateResource(Resource resource) {
        adminResourceMapper.updateResource(resource);
    }

    @Override
    public ResourceStats getResourceStats() {
        ResourceStats stats = new ResourceStats();
        stats.setTotalResources(adminResourceMapper.getTotalResources());
        stats.setTotalSize(adminResourceMapper.getTotalSize());
        stats.setDownloadCount(adminResourceMapper.getTotalDownloads());
        stats.setViewCount(adminResourceMapper.getTotalViews());
        stats.setTeacherCount(adminResourceMapper.getTeacherCount());
        return stats;
    }
}