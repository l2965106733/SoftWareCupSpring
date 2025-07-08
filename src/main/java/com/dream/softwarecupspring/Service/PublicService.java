package com.dream.softwarecupspring.Service;

import com.dream.softwarecupspring.pojo.LoginInfo;
import com.dream.softwarecupspring.pojo.ResetQueryParam;
import com.dream.softwarecupspring.pojo.User;

public interface PublicService {
    LoginInfo login(User user);

    Integer reset(ResetQueryParam resetQueryParam);
}
