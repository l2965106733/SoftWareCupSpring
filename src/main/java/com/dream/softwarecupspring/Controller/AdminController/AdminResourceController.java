package com.dream.softwarecupspring.Controller.AdminController;

import com.dream.softwarecupspring.Service.AdminService.AdminResourceService;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员端 - 教学资源管理控制器
 */
@RestController
@RequestMapping("/admin/resource")
public class AdminResourceController {

    @Autowired
    private AdminResourceService adminResourceService;

    @GetMapping
    public Result getAllResources() {
        return Result.success(adminResourceService.getAllResources());
    }

    @DeleteMapping("/{resourceId}")
    public Result deleteResource(@PathVariable Integer resourceId) {
        adminResourceService.deleteResource(resourceId);
        return Result.success("资源删除成功");
    }

    @PutMapping("/update")
    public Result updateResource(@RequestBody Resource resource) {
        adminResourceService.updateResource(resource);
        return Result.success("资源信息更新成功");
    }

    @GetMapping("/stats")
    public Result getResourceStats() {
        return Result.success(adminResourceService.getResourceStats());
    }
}
