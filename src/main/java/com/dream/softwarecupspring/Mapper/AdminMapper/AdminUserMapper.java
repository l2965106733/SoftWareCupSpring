package com.dream.softwarecupspring.Mapper.AdminMapper;

import com.dream.softwarecupspring.pojo.Resource.Resource;
import com.dream.softwarecupspring.pojo.User.User;
import com.dream.softwarecupspring.pojo.User.UserQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminUserMapper {
    List<User> getAllStudents();

    List<User> getRelatedStudents(Integer teacherId);

    List<User> pageSelect(UserQueryParam userQueryParam);

    void deleteByIds(List<Integer> ids);

    User getById(Integer id);

    void insert(User user);

    void updateById(User user);

    @Delete("delete from teacher_student where teacher_id = #{teacherId}")
    void deleteStudentdsByTeacherId(Integer teacherId);

    void submitSelectedStudents(List<Integer> selectedStudentIds,Integer teacherId);
}
