package com.dream.softwarecupspring.AdminController;

import com.dream.softwarecupspring.AdminService.UserService;
import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.Result;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public Result page(UserQueryParam empQueryParam) {
        PageResult<User> pageResult = userService.pageQuery(empQueryParam);
        return Result.success(pageResult);
    }

}
