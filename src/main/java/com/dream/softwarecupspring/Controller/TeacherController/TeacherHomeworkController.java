package com.dream.softwarecupspring.Controller.TeacherController;

import com.dream.softwarecupspring.Service.TeacherService.TeacherHomeworkService;
import com.dream.softwarecupspring.pojo.AI.AiResponse;
import com.dream.softwarecupspring.pojo.Homework.Homework;
import com.dream.softwarecupspring.pojo.Homework.Question;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Homework.StudentHomework;
import com.dream.softwarecupspring.pojo.Homework.TquestionQueryParam;
import com.dream.softwarecupspring.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/teacher/homework")
public class TeacherHomeworkController {
    @Autowired
    private AIUtils aiUtils;

    @Autowired
    private TeacherHomeworkService teacherHomeworkService;

    @PostMapping("/publish")
    public Result publishHomework(@RequestBody Homework homework) {
        Integer studentCount = teacherHomeworkService.publishHomework(homework);
        return Result.success(studentCount);
    }

    @PostMapping("/grade")
    public Result gradeHomework(@RequestBody StudentHomework studentHomework) {
        String resMsg = teacherHomeworkService.gradeHomework(studentHomework);
        return Result.success(resMsg);
    }

    @GetMapping("/{teacherId}")
    public Result getHomeworkList(@PathVariable Integer teacherId) {
        return Result.success(teacherHomeworkService.getHomeworkList(teacherId));
    }

    @GetMapping("/detail/{homeworkId}")
    public Result getHomeworkDetail(@PathVariable Integer homeworkId) {
        return Result.success(teacherHomeworkService.getHomeworkDetail(homeworkId));
    }

    @GetMapping("/detailWithAnswer")
    public Result getHomeworkDetailWithAnswer(
            @RequestParam Long homeworkId,
            @RequestParam Long studentId) {
        List<Map<String, Object>> data = teacherHomeworkService.getHomeworkDetailWithAnswer(homeworkId, studentId);
        return Result.success(data);
    }

    @GetMapping("/submissions/{homeworkId}")
    public Result getStudentSubmissions(@PathVariable Integer homeworkId) {
        return Result.success(teacherHomeworkService.getStudentSubmissions(homeworkId));
    }

    @PostMapping("/questions")
    public Result saveHomework(@RequestBody List<Question> questions) {
        return Result.success(teacherHomeworkService.saveQuestion(questions));
    }

    @PostMapping("/aiQuestion")
    public Result generateAIQuestion(@RequestBody TquestionQueryParam tquestionQueryParam) {
        AiResponse response = aiUtils.callAI("generateQuestion", tquestionQueryParam, "/ai");
        String rawText = response.getData();
        List<Question> questions = parseQuestions(rawText, tquestionQueryParam.getType());
        for (Question question : questions) {
            question.setKnowledge(response.getKnowledge());
        }
        return Result.success(questions);
    }

    private List<Question> parseQuestions(String rawText, String type) {
        List<Question> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "【\\d+】([\\s\\S]*?)【正确答案】([\\s\\S]*?)【解析】([\\s\\S]*?)(?=【\\d+】|$)"
        );
        Matcher matcher = pattern.matcher(rawText);
        while (matcher.find()) {
            Question q = new Question();
            q.setContent(matcher.group(1).trim());
            q.setAnswer(matcher.group(2).trim());
            q.setExplain(matcher.group(3).trim());
            q.setType(type);
            q.setScore(null);
            list.add(q);
        }
        return list;
    }

}
