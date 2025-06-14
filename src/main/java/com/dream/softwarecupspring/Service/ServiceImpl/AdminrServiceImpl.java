package com.dream.softwarecupspring.Service.ServiceImpl;

import com.dream.softwarecupspring.Mapper.AdminMapper;
import com.dream.softwarecupspring.Service.AdminrService;
import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminrServiceImpl implements AdminrService {
    @Autowired
    AdminMapper adminMapper;

    @GetMapping
    public PageResult<User> pageQuery(UserQueryParam userQueryParam) {
        PageHelper.startPage(userQueryParam.getPage(),userQueryParam.getPageSize());
        Page<User> p = (Page<User>) adminMapper.pageSelect(userQueryParam);
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        adminMapper.deleteByIds(ids);
    }

    @Override
    public User getInfo(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void add(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        adminMapper.insert(user);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        adminMapper.updateById(user);
    }
}
