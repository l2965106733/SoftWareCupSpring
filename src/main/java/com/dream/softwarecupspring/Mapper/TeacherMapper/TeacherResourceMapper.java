package com.dream.softwarecupspring.Mapper.TeacherMapper;

import com.dream.softwarecupspring.pojo.Homework.*;
import com.dream.softwarecupspring.pojo.Interact.AnswerQueryParam;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import com.dream.softwarecupspring.pojo.Overall.StudentSummary;
import com.dream.softwarecupspring.pojo.Resource.PopularResource;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherResourceMapper {
    void insertResource(Resource resource);

    void updateResource(Resource resource);

    List<Resource> selectResourcesByTeacherId(Long teacherId);

    void deleteResourceById(Long resourceId);

}
