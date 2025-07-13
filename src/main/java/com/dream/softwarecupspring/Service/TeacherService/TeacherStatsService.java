package com.dream.softwarecupspring.Service.TeacherService;

import com.dream.softwarecupspring.pojo.Homework.HomeworkStats;
import com.dream.softwarecupspring.pojo.Interact.InteractStats;
import com.dream.softwarecupspring.pojo.Overall.StudentSummary;
import com.dream.softwarecupspring.pojo.Overall.TeacherOverview;
import com.dream.softwarecupspring.pojo.Resource.ResourceStats;

import java.util.List;

public interface TeacherStatsService {

    HomeworkStats getHomeworkStats(Integer teacherId);

    TeacherOverview getOverview(Integer teacherId);

    List<StudentSummary> getStudentSummaries(Integer teacherId);

    ResourceStats getResourceStats(Integer teacherId);

    InteractStats getInteractStats(Integer teacherId);

    Object getTeacherOverview(Long teacherId);

    Object getTeacherActivities(Long teacherId, int limit);
}
