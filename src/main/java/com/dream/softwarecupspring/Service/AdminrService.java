package com.dream.softwarecupspring.Service;

import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;

import java.util.List;

public interface AdminrService {
    PageResult<User> pageQuery(UserQueryParam empQueryParam);

    void deleteByIds(List<Integer> ids);

    User getInfo(Integer id);

    void add(User user);

    void update(User user);
}
