package com.dream.softwarecupspring.Controller.StudentController;

import com.dream.softwarecupspring.Service.StudentService.StudentInteractService;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Interact.StudentQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student/interact")
public class StudentInteractController {

    @Autowired
    private StudentInteractService studentInteractService;


    @PostMapping("/questionSubmit")
    public Result submitQuestion(@RequestBody StudentQuestion studentQuestion) {
        studentInteractService.submitQuestion(studentQuestion);
        return Result.success("问题提交成功");
    }

    @GetMapping("/questions/{studentId}")
    public Result getMyQuestions(@PathVariable Integer studentId) {
        List<StudentQuestion> questions = studentInteractService.getMyQuestions(studentId);
        return Result.success(questions);
    }

    @GetMapping("/questionDetail/{questionId}")
    public Result getQuestionDetail(@PathVariable Integer questionId) {
        StudentQuestion question = studentInteractService.getQuestionDetail(questionId);
        return Result.success(question);
    }

    @PostMapping("/questionRate")
    public Result rateAnswer(@RequestBody Map<String, Object> rateData) {
        Integer questionId = (Integer) rateData.get("questionId");
        Integer rating = (Integer) rateData.get("rating");
        studentInteractService.rateAnswer(questionId, rating);
        return Result.success("评价成功");
    }

    @PostMapping("/rating/submit")
    public Result submitRating(@RequestBody Map<String, Object> ratingData) {
        Integer questionId = (Integer) ratingData.get("questionId");
        Integer rating = (Integer) ratingData.get("rating");
        studentInteractService.submitRating(questionId, rating);
        return Result.success("评分提交成功");
    }

    @GetMapping("/rating/{questionId}")
    public Result getRating(@PathVariable Integer questionId) {
        Integer rating = studentInteractService.getRating(questionId);
        return Result.success(rating);
    }

    @GetMapping("/rating/history/{studentId}")
    public Result getRatingHistory(@PathVariable Integer studentId) {
        List<StudentQuestion> ratingHistory = studentInteractService.getRatingHistory(studentId);
        return Result.success(ratingHistory);
    }

    @GetMapping("/rating/stats/{studentId}")
    public Result getRatingStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentInteractService.getRatingStats(studentId);
        return Result.success(stats);
    }

    @GetMapping("/stats/{studentId}")
    public Result getInteractStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentInteractService.getInteractStats(studentId);
        return Result.success(stats);
    }

    @GetMapping("/homeStats/{studentId}")
    public Result getHomeStats(@PathVariable Long studentId) {
        return Result.success(studentInteractService.getHomeStats(studentId));
    }

    @GetMapping("/recentActivities/{studentId}")
    public Result getRecentActivities(@PathVariable Long studentId) {
        return Result.success(studentInteractService.getRecentActivities(studentId));
    }

    @GetMapping("/teacherId/{studentId}")
    public Result getTeacherId(@PathVariable Long studentId) {
        return Result.success(studentInteractService.getTeacherId(studentId));
    }

}