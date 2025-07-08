package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.Service.AdminrService;
import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.Result;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员端用户管理控制器
 */
@RestController
@RequestMapping(value = "/admin/user")
public class AdminController {

    @Autowired
    private AdminrService adminrService;

    // ==================== 用户分页查询 ====================

    /**
     * 根据查询条件分页获取用户列表
     */
    @GetMapping
    public Result page(UserQueryParam empQueryParam) {
        PageResult<User> pageResult = adminrService.pageQuery(empQueryParam);
        return Result.success(pageResult);
    }

    // ==================== 用户新增与删除 ====================

    /**
     * 批量删除用户
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Integer> ids) {
        adminrService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        adminrService.add(user);
        return Result.success();
    }

    // ==================== 用户信息查询与修改 ====================

    /**
     * 根据 ID 获取用户信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        User user = adminrService.getInfo(id);
        return Result.success(user);
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public Result update(@RequestBody User user) {
        adminrService.update(user);
        return Result.success();
    }
}
