package com.dream.softwarecupspring.AdminMapper;

import com.dream.softwarecupspring.pojo.PageResult;
import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> pageSelect(UserQueryParam userQueryParam);
}
