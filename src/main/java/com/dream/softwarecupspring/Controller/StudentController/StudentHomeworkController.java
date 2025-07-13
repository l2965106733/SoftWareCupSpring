package com.dream.softwarecupspring.Controller.StudentController;

import com.dream.softwarecupspring.Service.StudentService.StudentHomeworkService;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Homework.StudentHomework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student/homework")
public class StudentHomeworkController {

    @Autowired
    private StudentHomeworkService studentHomeworkService;

    @GetMapping("/{studentId}")
    public Result getHomeworkList(@PathVariable Integer studentId) {
        List<Map<String, Object>> homeworkList = studentHomeworkService.getHomeworkList(studentId);
        return Result.success(homeworkList);
    }

    @GetMapping("/detail/{homeworkId}")
    public Result getHomeworkDetail(@PathVariable Integer homeworkId) {
        List<Map<String, Object>> homeworkDetail = studentHomeworkService.getHomeworkDetail(homeworkId);
        return Result.success(homeworkDetail);
    }

    @PostMapping("/draft")
    public Result saveHomeworkDraft(@RequestBody StudentHomework studentHomework) {
        studentHomeworkService.saveHomeworkDraft(studentHomework);
        return Result.success("草稿保存成功");
    }

    @PostMapping("/submit")
    public Result submitHomework(@RequestBody StudentHomework studentHomework) {
        studentHomeworkService.submitHomework(studentHomework);
        return Result.success("作业提交成功");
    }

    @GetMapping("/stats/{studentId}")
    public Result getHomeworkStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentHomeworkService.getHomeworkStats(studentId);
        return Result.success(stats);
    }
}
