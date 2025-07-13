package com.dream.softwarecupspring.Mapper.AdminMapper;

import com.dream.softwarecupspring.pojo.Resource.Resource;
import com.dream.softwarecupspring.pojo.User.User;
import com.dream.softwarecupspring.pojo.User.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminUserMapper {
    List<User> pageSelect(UserQueryParam userQueryParam);

    void deleteByIds(List<Integer> ids);

    User getById(Integer id);

    void insert(User user);

    void updateById(User user);

}
