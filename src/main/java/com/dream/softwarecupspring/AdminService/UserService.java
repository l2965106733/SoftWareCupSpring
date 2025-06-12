package com.dream.softwarecupspring.AdminService;

import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;

public interface UserService {
    PageResult<User> pageQuery(UserQueryParam empQueryParam);
}
