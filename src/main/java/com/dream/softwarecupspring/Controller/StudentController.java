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
    private  StudentService studentService;
    @Autowired
    private AIUtils aiUtils;

    @GetMapping("/teacherId/{studentId}")
    public Result getTeacherId(@PathVariable Integer studentId) {
        Integer teacherId = studentService.getTeacherId(studentId);
        return Result.success(teacherId);
    }

    @PostMapping("/chat")
    public Result getChat(@RequestBody ChatQueryParam chatQueryParam) {
        return aiUtils.callAI("generateStudentChat", chatQueryParam, "/chat");
    }

    @GetMapping("/courseware/{studentId}")
    public Result getCoursewareList(@PathVariable Integer studentId) {
        List<Map<String, Object>> coursewareList = studentService.getCoursewareList(studentId);
        return Result.success(coursewareList);
    }

    @GetMapping("/stats/{studentId}")
    public Result getStudyStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getStudyStats(studentId);
        return Result.success(stats);
    }

    @PostMapping("/studyRecord")
    public Result recordStudyBehavior(@RequestBody StudyRecord studyRecord) {
        studentService.recordStudyBehavior(studyRecord);
        return Result.success("学习记录保存成功");
    }

    @PostMapping("/aiQuestion")
    public Result recordAiQuestion(@RequestBody AiQuestion aiQuestion) {
        studentService.recordAiQuestion(aiQuestion);
        return Result.success("AI提问记录成功");
    }

    @GetMapping("/homework/{studentId}")
    public Result getHomeworkList(@PathVariable Integer studentId) {
        List<Map<String, Object>> homeworkList = studentService.getHomeworkList(studentId);
        return Result.success(homeworkList);
    }

    @GetMapping("/homeworkDetail/{homeworkId}")
    public Result getHomeworkDetail(@PathVariable Integer homeworkId) {
        Map<String, Object> homeworkDetail = studentService.getHomeworkDetail(homeworkId);
        return Result.success(homeworkDetail);
    }

    @PostMapping("/homeworkDraft")
    public Result saveHomeworkDraft(@RequestBody StudentHomework studentHomework) {
        studentService.saveHomeworkDraft(studentHomework);
        return Result.success("草稿保存成功");
    }

    @PostMapping("/homeworkSubmit")
    public Result submitHomework(@RequestBody StudentHomework studentHomework) {
        studentService.submitHomework(studentHomework);
        return Result.success("作业提交成功");
    }

    @GetMapping("/homeworkStats/{studentId}")
    public Result getHomeworkStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getHomeworkStats(studentId);
        return Result.success(stats);
    }

    @PostMapping("/questionSubmit")
    public Result submitQuestion(@RequestBody StudentQuestion studentQuestion) {
        studentService.submitQuestion(studentQuestion);
        return Result.success("问题提交成功");
    }

    @GetMapping("/questions/{studentId}")
    public Result getMyQuestions(@PathVariable Integer studentId) {
        List<StudentQuestion> questions = studentService.getMyQuestions(studentId);
        return Result.success(questions);
    }

    @GetMapping("/questionDetail/{questionId}")
    public Result getQuestionDetail(@PathVariable Integer questionId) {
        StudentQuestion question = studentService.getQuestionDetail(questionId);
        return Result.success(question);
    }

    @PostMapping("/questionRate")
    public Result rateAnswer(@RequestBody Map<String, Object> rateData) {
        Integer questionId = (Integer) rateData.get("questionId");
        Integer rating = (Integer) rateData.get("rating");
        studentService.rateAnswer(questionId, rating);
        return Result.success("评价成功");
    }

    @GetMapping("/interactStats/{studentId}")
    public Result getInteractStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentService.getInteractStats(studentId);
        return Result.success(stats);
    }
}
