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

    // ==================== 教学资源管理 ====================

    /**
     * 上传教学资源
     */
    @PostMapping("/resourceUpload")
    public Result uploadResource(@RequestBody Resource resource) {
        Long id = teacherService.uploadResource(resource);
        return Result.success(id);
    }

    /**
     * 更新教学资源
     */
    @PutMapping("/resourceUpdate")
    public Result updateResource(@RequestBody Resource resource) {
        teacherService.updateResource(resource);
        return Result.success();
    }

    /**
     * 获取教师上传的资源列表
     */
    @GetMapping("/resource/{teacherId}")
    public Result getResourceList(@PathVariable Long teacherId) {
        List<Resource> list = teacherService.getResourceList(teacherId);
        return Result.success(list);
    }

    /**
     * 删除教学资源
     */
    @DeleteMapping("/resource/{resourceId}")
    public Result deleteResource(@PathVariable Long resourceId) {
        teacherService.deleteResource(resourceId);
        return Result.success();
    }

    // ==================== 作业发布与批改 ====================

    /**
     * 批改学生作业
     */
    @PostMapping("/homeworkGrade")
    public Result gradeHomework(@RequestBody StudentHomework studentHomework) {
        teacherService.gradeHomework(studentHomework);
        return Result.success("批改成功");
    }

    /**
     * 发布作业
     */
    @PostMapping("/publish")
    public Result publishHomework(@RequestBody Homework homework) {
        teacherService.publishHomework(homework);
        return Result.success();
    }

    /**
     * 获取教师布置的作业列表
     */
    @GetMapping("/homework/{teacherId}")
    public Result getHomeworkList(@PathVariable Integer teacherId) {
        List<Homework> homeworks = teacherService.getHomeworkList(teacherId);
        return Result.success(homeworks);
    }

    /**
     * 获取作业详情（题目列表）
     */
    @GetMapping("/detail/{homeworkId}")
    public Result getHomeworkDetail(@PathVariable Integer homeworkId) {
        List<Question> questions = teacherService.getHomeworkDetail(homeworkId);
        return Result.success(questions);
    }

    /**
     * 获取学生提交的作业列表
     */
    @GetMapping("/submissions/{homeworkId}")
    public Result getStudentSubmissions(@PathVariable Integer homeworkId) {
        List<StudentHomework> studentHomeworks = teacherService.getStudentSubmissions(homeworkId);
        return Result.success(studentHomeworks);
    }

    // ==================== AI 能力调用 ====================

    /**
     * 根据教学关键字生成教学计划
     */
    @PostMapping("/teachingPlan")
    public Result getTeachingPlan(@RequestBody TeachingPlanQueryParam teachingPlanQueryParam) {
        return aiUtils.callAI("generateTeachingPlan", teachingPlanQueryParam, "/ai");
    }

    /**
     * 调用 AI 自动生成题目
     */
    @PostMapping("/question")
    public Result getQuestion(@RequestBody TquestionQueryParam tquestionQueryParam) {
        return aiUtils.callAI("generateQuestion", tquestionQueryParam, "/ai");
    }

    /**
     * 调用 AI 自动生成参考答案
     */
    @PostMapping("/answer")
    public Result getAnswer(@RequestBody TanswerQueryParam tanswerQueryParam) {
        tanswerQueryParam.setType("数据库技术");
        return aiUtils.callAI("generateTeacherAnswer", tanswerQueryParam, "/ai");
    }

    /**
     * 保存教师编辑后的题目列表
     */
    @PostMapping("/questions")
    public Result saveHomework(@RequestBody List<Question> questions) {
        List<Question> questionIds = teacherService.saveQuestion(questions);
        return Result.success(questionIds);
    }

    // ==================== 学生互动问答 ====================

    /**
     * 获取当前教师收到的学生问题列表
     */
    @GetMapping("/studentQuestion/{teacherId}")
    public Result getStudentQuestions(@PathVariable Integer teacherId) {
        List<StudentQuestion> studentQuestions = teacherService.getStudentQuestions(teacherId);
        return Result.success(studentQuestions);
    }

    /**
     * 教师为学生问题发送 AI 参考答案
     */
    @PostMapping("/studentAnswer")
    public Result sendStudentAnswer(@RequestBody AnswerQueryParam answerQueryParam) {
        teacherService.sendStudentAnswer(answerQueryParam);
        return Result.success();
    }

    @GetMapping("/overview/{teacherId}")
    public Result getOverview(@PathVariable Integer teacherId) {
        TeacherOverview vo = teacherService.getOverview(teacherId);
        return Result.success(vo);
    }

    @GetMapping("/homeworkStats/{teacherId}")
    public Result getHomeworkStats(@PathVariable Integer teacherId) {
        HomeworkStats stats = teacherService.getHomeworkStats(teacherId);
        return Result.success(stats);
    }

    @GetMapping("/students/{teacherId}")
    public Result getStudentSummaries(@PathVariable Integer teacherId) {
        List<StudentSummary> list = teacherService.getStudentSummaries(teacherId);
        return Result.success(list);
    }

    @GetMapping("/resourceStats/{teacherId}")
    public Result getResourceStats(@PathVariable Integer teacherId) {
        ResourceStats stats = teacherService.getResourceStats(teacherId);
        return Result.success(stats);
    }

    @GetMapping("/interactStats/{teacherId}")
    public Result getInteractStats(@PathVariable Integer teacherId) {
        InteractStats stats = teacherService.getInteractStats(teacherId);
        return Result.success(stats);
    }
}
