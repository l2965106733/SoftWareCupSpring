package com.dream.softwarecupspring.Controller.AdminController;

import com.dream.softwarecupspring.Service.AdminService.AdminUserService;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.User.User;
import com.dream.softwarecupspring.pojo.User.UserQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理员端 - 用户管理控制器
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public Result page(UserQueryParam empQueryParam) {
        return Result.success(adminUserService.pageQuery(empQueryParam));
    }

    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Integer> ids) {
        adminUserService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/getAllStudents/{teacherId}")
    public Result getAllStudentsByTeacherId(@PathVariable Integer teacherId) {
        Map<String, List<User>> students = adminUserService.getStudentsByTeacherId(teacherId);
        return Result.success(students);
    }

    @PostMapping
    public Result add(@RequestBody User user) {
        adminUserService.add(user);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        return Result.success(adminUserService.getInfo(id));
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        adminUserService.update(user);
        return Result.success();
    }

    @PutMapping("/submitSelectedStudents/{teacherId}")
    public Result submitSelectedStudents(@PathVariable Integer teacherId,@RequestBody List<Integer> selectedStudentIds) {
        adminUserService.submitSelectedStudents(selectedStudentIds,teacherId);
        return Result.success();
    }
}
