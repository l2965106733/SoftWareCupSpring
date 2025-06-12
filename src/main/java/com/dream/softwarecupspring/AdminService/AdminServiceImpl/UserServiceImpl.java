package com.dream.softwarecupspring.AdminService.AdminServiceImpl;

import com.dream.softwarecupspring.AdminMapper.UserMapper;
import com.dream.softwarecupspring.AdminService.UserService;
import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @GetMapping
    public PageResult<User> pageQuery(UserQueryParam userQueryParam) {
        PageHelper.startPage(userQueryParam.getPage(),userQueryParam.getPageSize());
        Page<User> p = (Page<User>) userMapper.pageSelect(userQueryParam);
        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
