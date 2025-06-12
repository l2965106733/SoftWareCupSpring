package com.dream.softwarecupspring.AdminMapper;

import com.dream.softwarecupspring.pojo.User;
import com.dream.softwarecupspring.pojo.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> pageSelect(UserQueryParam userQueryParam);


    void deleteByIds(List<Integer> ids);

    User getById(Integer id);

    void insert(User user);

    void updateById(User user);
}
