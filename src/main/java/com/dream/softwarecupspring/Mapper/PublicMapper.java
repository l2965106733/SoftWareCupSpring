package com.dream.softwarecupspring.Mapper;

import com.dream.softwarecupspring.pojo.ResetQueryParam;
import com.dream.softwarecupspring.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PublicMapper {
    @Select("select id, username, name, role from users where username = #{username} and password = #{password}")
    User selectByUsernameAndPassword(User user);

    @Update("update users set password = #{newPassword} where identifier = #{identifier} and role = #{role}")
    Integer updatePasswordByIdentifier(ResetQueryParam resetQueryParam);
    @Update("update users set password = #{newPassword} where password = #{oldPassword} and id = #{id}")
    Integer updatePassword(ResetQueryParam  resetQueryParam);
}
