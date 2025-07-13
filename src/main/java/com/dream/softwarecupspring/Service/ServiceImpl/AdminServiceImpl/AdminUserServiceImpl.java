package com.dream.softwarecupspring.Service.ServiceImpl.AdminServiceImpl;

import com.dream.softwarecupspring.Mapper.AdminMapper.AdminUserMapper;
import com.dream.softwarecupspring.Service.AdminService.AdminUserService;
import com.dream.softwarecupspring.pojo.Common.PageResult;
import com.dream.softwarecupspring.pojo.User.User;
import com.dream.softwarecupspring.pojo.User.UserQueryParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    AdminUserMapper adminUserMapper;

    @GetMapping
    public PageResult<User> pageQuery(UserQueryParam userQueryParam) {
        PageHelper.startPage(userQueryParam.getPage(),userQueryParam.getPageSize());
        Page<User> p = (Page<User>) adminUserMapper.pageSelect(userQueryParam);
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        adminUserMapper.deleteByIds(ids);
    }

    @Override
    public User getInfo(Integer id) {
        return adminUserMapper.getById(id);
    }

    @Override
    public void add(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        adminUserMapper.insert(user);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        adminUserMapper.updateById(user);
    }
}
