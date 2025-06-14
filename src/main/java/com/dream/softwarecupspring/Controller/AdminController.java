package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.Service.AdminrService;
import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.Result;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/user")
public class AdminController {
    @Autowired
    AdminrService adminrService;

    @GetMapping
    public Result page(UserQueryParam empQueryParam) {
        PageResult<User> pageResult = adminrService.pageQuery(empQueryParam);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Integer> ids) {
        adminrService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody User user) {
        adminrService.add(user);
        return Result.success();
    }


    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        User user = adminrService.getInfo(id);
        return Result.success(user);
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        adminrService.update(user);
        return Result.success();
    }

}
