package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.Service.StudentService;
import com.dream.softwarecupspring.pojo.*;
import com.dream.softwarecupspring.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AIUtils aiUtils;

    // ========================= 【基础信息与聊天模块】 =========================

    /**
     * 获取学生对应的教师ID
     */
    @GetMapping("/teacherId/{studentId}")
    public Result getTeacherId(@PathVariable Integer studentId) {
        Integer teacherId = studentService.getTeacherId(studentId);
        return Result.success(teacherId);
    }

    @PostMapping("/studyRecord")
    public Result reportStudyRecord(@RequestBody StudyRecordDTO dto) {
        boolean success = studentService.reportStudyRecord(dto);
        return Result.success(success ? "记录成功" : "无需更新");
    }

    /**
     * 学生AI聊天接口
     */
    @PostMapping("/chat")
    public Result getChat(@RequestBody ChatQueryParam chatQueryParam) {
        return aiUtils.callAI("generateStudentChat", chatQueryParam, "/ai");
    }

    // ========================= 【教学资源模块】 =========================

    /**
     * 获取学生可查看的课件资源列表
     */
    @GetMapping("/courseware/{studentId}")
    public Result getCoursewareList(@PathVariable Integer studentId) {
        List<Map<String, Object>> coursewareList = studentService.getCoursewareList(studentId);
        return Result.success(coursewareList);
    }

    /**
     * 获取学生学习统计数据（图表）
     */
    @GetMapping("/stats/{studentId}")
    public Result getStudyStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getStudyStats(studentId);
        return Result.success(stats);
    }

    // ========================= 【作业模块】 =========================

    /**
     * 获取学生作业列表
     */
    @GetMapping("/homework/{studentId}")
    public Result getHomeworkList(@PathVariable Integer studentId) {
        List<Map<String, Object>> homeworkList = studentService.getHomeworkList(studentId);
        return Result.success(homeworkList);
    }

    /**
     * 获取作业详情
     */
    @GetMapping("/homeworkDetail/{homeworkId}")
    public Result getHomeworkDetail(@PathVariable Integer homeworkId) {
        List<Map<String, Object>> homeworkDetail = studentService.getHomeworkDetail(homeworkId);
        return Result.success(homeworkDetail);
    }

    /**
     * 保存作业草稿
     */
    @PostMapping("/homeworkDraft")
    public Result saveHomeworkDraft(@RequestBody StudentHomework studentHomework) {
        studentService.saveHomeworkDraft(studentHomework);
        return Result.success("草稿保存成功");
    }

    /**
     * 提交作业
     */
    @PostMapping("/homeworkSubmit")
    public Result submitHomework(@RequestBody StudentHomework studentHomework) {
        studentService.submitHomework(studentHomework);
        return Result.success("作业提交成功");
    }

    /**
     * 获取学生作业统计信息（图表）
     */
    @GetMapping("/homeworkStats/{studentId}")
    public Result getHomeworkStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getHomeworkStats(studentId);
        return Result.success(stats);
    }

    // ========================= 【提问与AI问答模块】 =========================

    /**
     * 提交AI问题记录
     */
    @PostMapping("/aiQuestion")
    public Result recordAiQuestion(@RequestBody AiQuestion aiQuestion) {
        studentService.recordAiQuestion(aiQuestion);
        return Result.success("AI提问记录成功");
    }

    /**
     * 提交普通问题
     */
    @PostMapping("/questionSubmit")
    public Result submitQuestion(@RequestBody StudentQuestion studentQuestion) {
        studentService.submitQuestion(studentQuestion);
        return Result.success("问题提交成功");
    }

    /**
     * 获取学生提交的问题列表
     */
    @GetMapping("/questions/{studentId}")
    public Result getMyQuestions(@PathVariable Integer studentId) {
        List<StudentQuestion> questions = studentService.getMyQuestions(studentId);
        return Result.success(questions);
    }

    /**
     * 获取单个问题详情
     */
    @GetMapping("/questionDetail/{questionId}")
    public Result getQuestionDetail(@PathVariable Integer questionId) {
        StudentQuestion question = studentService.getQuestionDetail(questionId);
        return Result.success(question);
    }

    // ========================= 【问题评分模块】 =========================

    /**
     * 学生对回答进行评分
     */
    @PostMapping("/questionRate")
    public Result rateAnswer(@RequestBody Map<String, Object> rateData) {
        Integer questionId = (Integer) rateData.get("questionId");
        Integer rating = (Integer) rateData.get("rating");
        studentService.rateAnswer(questionId, rating);
        return Result.success("评价成功");
    }

    /**
     * 提交评分（另一入口）
     */
    @PostMapping("/rating/submit")
    public Result submitRating(@RequestBody Map<String, Object> ratingData) {
        Integer questionId = (Integer) ratingData.get("questionId");
        Integer rating = (Integer) ratingData.get("rating");
        studentService.submitRating(questionId, rating);
        return Result.success("评分提交成功");
    }

    /**
     * 获取某个问题的评分
     */
    @GetMapping("/rating/{questionId}")
    public Result getRating(@PathVariable Integer questionId) {
        Integer rating = studentService.getRating(questionId);
        return Result.success(rating);
    }

    /**
     * 获取学生的评分历史
     */
    @GetMapping("/rating/history/{studentId}")
    public Result getRatingHistory(@PathVariable Integer studentId) {
        List<StudentQuestion> ratingHistory = studentService.getRatingHistory(studentId);
        return Result.success(ratingHistory);
    }

    /**
     * 获取学生评分统计信息（图表）
     */
    @GetMapping("/rating/stats/{studentId}")
    public Result getRatingStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getRatingStats(studentId);
        return Result.success(stats);
    }

    // ========================= 【互动统计模块】 =========================

    /**
     * 获取学生提问互动统计（提问数、评分、类型分布等）
     */
    @GetMapping("/interactStats/{studentId}")
    public Result getInteractStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getInteractStats(studentId);
        return Result.success(stats);
    }

}
