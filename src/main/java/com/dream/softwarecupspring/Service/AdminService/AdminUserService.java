package com.dream.softwarecupspring.Service.AdminService;

import com.dream.softwarecupspring.pojo.Common.PageResult;
import com.dream.softwarecupspring.pojo.User.User;
import com.dream.softwarecupspring.pojo.User.UserQueryParam;

import java.util.List;

public interface AdminUserService {
    PageResult<User> pageQuery(UserQueryParam empQueryParam);

    void deleteByIds(List<Integer> ids);

    User getInfo(Integer id);

    void add(User user);

    void update(User user);


}
