package com.dream.softwarecupspring.Controller.StudentController;

import com.dream.softwarecupspring.Service.StudentService.StudentStudyService;
import com.dream.softwarecupspring.pojo.AI.AiQuestion;
import com.dream.softwarecupspring.pojo.AI.AiResponse;
import com.dream.softwarecupspring.pojo.AI.ChatQueryParam;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Homework.Question;
import com.dream.softwarecupspring.pojo.Homework.TquestionQueryParam;
import com.dream.softwarecupspring.pojo.Overall.StudyRecordDTO;
import com.dream.softwarecupspring.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/student/study")
public class StudentStudyController {

    @Autowired
    private StudentStudyService studentStudyService;

    @Autowired
    private AIUtils aiUtils;

    @GetMapping("/teacherId/{studentId}")
    public Result getTeacherId(@PathVariable Integer studentId) {
        Integer teacherId = studentStudyService.getTeacherId(studentId);
        return Result.success(teacherId);
    }

    @PostMapping("/record")
    public Result reportStudyRecord(@RequestBody StudyRecordDTO dto) {
        boolean success = studentStudyService.reportStudyRecord(dto);
        return Result.success(success ? "记录成功" : "无需更新");
    }

    @GetMapping("/records/{studentId}")
    public Result getStudyRecords(@PathVariable Long studentId, @RequestParam String type) {
        return Result.success(studentStudyService.getStudyRecords(studentId, type));
    }

    @GetMapping("/timeTrend/{studentId}")
    public Result getStudyTimeTrend(@PathVariable Long studentId, @RequestParam int days) {
        return Result.success(studentStudyService.getStudyTimeTrend(studentId, days));
    }

    @PostMapping("/chat")
    public Result getChat(@RequestBody ChatQueryParam chatQueryParam) {
        AiResponse response = aiUtils.callAI("generateStudentChat", chatQueryParam, "/ai");

        return Result.success(response.getData());
    }

    @GetMapping("/aiQuestions/{studentId}")
    public Result getAiQuestions(@PathVariable Long studentId, @RequestParam(defaultValue = "50") int limit) {
        return Result.success(studentStudyService.getAiQuestions(studentId, limit));
    }

    @PostMapping("/aiQuestion")
    public Result recordAiQuestion(@RequestBody AiQuestion aiQuestion) {
        System.out.println(111);
        studentStudyService.recordAiQuestion(aiQuestion);
        return Result.success("AI提问记录成功");
    }

    @GetMapping("/courseware/{studentId}")
    public Result getCoursewareList(@PathVariable Integer studentId) {
        List<Map<String, Object>> coursewareList = studentStudyService.getCoursewareList(studentId);
        return Result.success(coursewareList);
    }

    @GetMapping("/stats/{studentId}")
    public Result getStudyStats(@PathVariable Integer studentId) {
        Map<String, Object> stats = studentStudyService.getStudyStats(studentId);
        return Result.success(stats);
    }

    @PostMapping("/generateAIQuestion")
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
