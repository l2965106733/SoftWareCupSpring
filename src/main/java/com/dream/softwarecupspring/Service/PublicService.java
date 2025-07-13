package com.dream.softwarecupspring.Service;

import com.dream.softwarecupspring.pojo.User.LoginInfo;
import com.dream.softwarecupspring.pojo.Common.ResetQueryParam;
import com.dream.softwarecupspring.pojo.User.User;

public interface PublicService {
    LoginInfo login(User user);

    Integer reset(ResetQueryParam resetQueryParam);
}
