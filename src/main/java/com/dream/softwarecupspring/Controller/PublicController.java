package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.Service.PublicService;
import com.dream.softwarecupspring.pojo.LoginInfo;
import com.dream.softwarecupspring.pojo.ResetQueryParam;
import com.dream.softwarecupspring.pojo.Result;
import com.dream.softwarecupspring.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    @Autowired
    private PublicService publicService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        LoginInfo info = publicService.login(user);
        if (info != null) {
            return Result.success(info);
        }
        else {
            return Result.error("用户名或密码错误");
        }
    }


    @PutMapping("/reset")
    public Result reset(@RequestBody ResetQueryParam resetQueryParam) {
        Integer rows = publicService.reset(resetQueryParam);
        if (rows > 0) {
            return Result.success();  // 成功更新
        } else {
            return Result.error("更新失败，请检查信息"); // 更新失败
        }
    }
}
