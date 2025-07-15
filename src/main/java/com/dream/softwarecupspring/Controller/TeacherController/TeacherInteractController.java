package com.dream.softwarecupspring.Controller.TeacherController;

import com.dream.softwarecupspring.Service.TeacherService.TeacherInteractService;
import com.dream.softwarecupspring.pojo.Interact.AnswerQueryParam;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Homework.TanswerQueryParam;
import com.dream.softwarecupspring.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/teacher/interact")
public class TeacherInteractController {
    @Autowired
    private AIUtils aiUtils;

    @Autowired
    private TeacherInteractService teacherInteractService;

    @GetMapping("/studentQuestion/{teacherId}")
    public Result getStudentQuestions(@PathVariable Integer teacherId) {
        return Result.success(teacherInteractService.getStudentQuestions(teacherId));
    }

    @PostMapping("/studentAnswer")
    public Result sendStudentAnswer(@RequestBody AnswerQueryParam param) {
        teacherInteractService.sendStudentAnswer(param);
        return Result.success();
    }

    @PostMapping("/aiAnswer")
    public Result generateAIAnswer(@RequestBody TanswerQueryParam param) {
        System.out.println(1111);
        return Result.success(aiUtils.callAI("generateTeacherAnswer", param, "/ai").getData());
    }

    @GetMapping("/ratings/{teacherId}")
    public Result getTeacherRatings(@PathVariable Long teacherId) {
        List<Map<String, Object>> ratings = teacherInteractService.getTeacherRatings(teacherId);
        return Result.success(ratings);
    }

}
