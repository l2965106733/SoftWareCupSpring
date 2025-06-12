package com.dream.softwarecupspring.AdminService;

import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;

import java.util.List;

public interface UserService {
    PageResult<User> pageQuery(UserQueryParam empQueryParam);

    void deleteByIds(List<Integer> ids);

    User getInfo(Integer id);

    void add(User user);

    void update(User user);
}
