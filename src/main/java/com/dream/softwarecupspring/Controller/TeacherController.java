package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.Service.TeacherService;
import com.dream.softwarecupspring.pojo.*;
import com.dream.softwarecupspring.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AIUtils aiUtils;

    @PostMapping("/resourceUpload")
    public Result uploadResource(@RequestBody Resource resource) {
        Long id = teacherService.uploadResource(resource);
        return Result.success(id);
    }

    @PutMapping("/resourceUpdate")
    public Result updateResource(@RequestBody Resource resource) {
        teacherService.updateResource(resource);
        return Result.success();
    }

    @GetMapping("/resource/{teacherId}")
    public Result getResourceList(@PathVariable Long teacherId) {
        List<Resource> list = teacherService.getResourceList(teacherId);
        return Result.success(list);
    }

    @DeleteMapping("/resource/{resourceId}")
    public Result deleteResource(@PathVariable Long resourceId) {
        teacherService.deleteResource(resourceId);
        return Result.success();
    }

    @PostMapping("/homeworkGrade")
    public Result gradeHomework(@RequestBody StudentHomework studentHomework) {
        teacherService.gradeHomework(studentHomework);
        return Result.success("批改成功");
    }

    @GetMapping("/homeworkStats/{teacherId}")
    public Result getHomeworkStats(@PathVariable Integer teacherId) {
        try {
            Map<String, Object> stats = teacherService.getHomeworkStats(teacherId);
            return Result.success(stats);
        } catch (Exception e) {
            return Result.error("获取统计数据失败：" + e.getMessage());
        }
    }

    @PostMapping("/teachingPlan")
    public Result getTeachingPlan(@RequestBody TeachingPlanQueryParam teachingPlanQueryParam) {
        return aiUtils.callAI("generateTeachingPlan", teachingPlanQueryParam,"/teachingPlan");
    }

    @PostMapping("/question")
    public Result getQuestion(@RequestBody TquestionQueryParam tquestionQueryParam) {
        return aiUtils.callAI("generateQuestion", tquestionQueryParam,"/question");
    }

    @PostMapping("/answer")
    public Result getAnswer(@RequestBody TanswerQueryParam tanswerQueryParam) {
        return aiUtils.callAI("generateTeacherAnswer", tanswerQueryParam,"/answer");
    }

    @PostMapping("/questions")
    public Result saveHomework(@RequestBody List<Question> questions){
        teacherService.saveQuestion(questions);
        return Result.success();
    }

    @PostMapping("/publish")
    public Result publishHomework(@RequestBody Homework homework){
        teacherService.publishHomework(homework);
        return Result.success();
    }

    @GetMapping("/homework/{teacherId}")
    public Result getHomeworkList(@PathVariable Integer teacherId) {
        List<Homework> homeworks = teacherService.getHomeworkList(teacherId);
        return Result.success(homeworks);
    }

    @GetMapping("/studentQuestion/{teacherId}")
    public Result getStudentQuestions(@PathVariable Integer teacherId) {
        List<StudentQuestion> studentQuestions = teacherService.getStudentQuestions(teacherId);
        return Result.success(studentQuestions);
    }

    @PostMapping("/studentAnswer")
    public Result sendStudentAnswer(@RequestBody AnswerQueryParam answerQueryParam){
        teacherService.sendStudentAnswer(answerQueryParam);
        return Result.success();
    }

    @GetMapping("/detail/{homeworkId}")
    public Result getHomeworkDetail(@PathVariable Integer homeworkId) {
        List<Question> questions = teacherService.getHomeworkDetail(homeworkId);
        return Result.success(questions);
    }

    @GetMapping("/submissions/{homeworkId}")
    public Result getStudentSubmissions(@PathVariable Integer homeworkId){
        List<StudentHomework> studentHomeworks = teacherService.getStudentSubmissions(homeworkId);
        return Result.success(studentHomeworks);
    }
}
