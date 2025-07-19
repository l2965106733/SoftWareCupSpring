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
        System.out.println(rawText);
        List<Question> questions = parseQuestions(rawText, tquestionQueryParam.getType());
        for (Question question : questions) {
            question.setKnowledge(response.getKnowledge());
        }
        System.out.println(questions);
        return Result.success(questions);
    }

    private static final Pattern QUESTION_BLOCK = Pattern.compile(
            "【题号(?:\\d*)】\\s*(\\d*)\\s*" +                           // group(1): 题号
                    "【题干】([\\s\\S]*?)(?=\\s+(?:java|c|cpp|python|sql)\\b|【正确答案】|【解析】|【题号(?:\\d*)】|\\z)" + // group(2): 题干，止于 java 或结构标记
                    "(?:【正确答案】([\\s\\S]*?))?" +                            // group(3): 正确答案
                    "【解析】([\\s\\S]*?)(?=【题号(?:\\d*)】|\\z)"               // group(4): 解析
    );

    private static final Pattern CODE_BLOCK = Pattern.compile(
            "(?:```[^\\n]*\\R([\\s\\S]*?)```)" +
                    "|(?:\\b(java|c|cpp|python|sql)\\b\\s*\\R([\\s\\S]*?))(?=\\R\\s*【|\\z)",
            Pattern.CASE_INSENSITIVE);

    private List<Question> parseQuestions(String rawText, String type) {
        List<Question> list = new ArrayList<>();
        if (rawText == null || rawText.isBlank()) return list;

        Matcher m = QUESTION_BLOCK.matcher(rawText);
        while (m.find()) {
            String qNum    = m.group(1) != null ? m.group(1).trim() : "";
            String stem    = m.group(2) != null ? m.group(2).trim() : "";
            String answer  = m.group(3) != null ? m.group(3).trim() : null;
            String explain = m.group(4) != null ? m.group(4).trim() : "";
            if (answer == null || answer.trim().isEmpty()) {     // 自动抓代码
                Matcher code = CODE_BLOCK.matcher(stem);
                if (code.find()) {
                    String codeBlock = code.group(1) != null ? code.group(1) : code.group(3);
                    answer = codeBlock != null ? codeBlock.trim()
                            : "【未检测到代码块，无法自动提取正确答案】";
                } else {
                    answer = "【未检测到代码块，无法自动提取正确答案】";
                }
            } else {
                answer = answer.trim();
            }

            Question q = new Question();
            q.setContent(stem);
            q.setAnswer(answer);
            q.setExplain(explain);
            q.setType(type);
            q.setScore(null);
            list.add(q);
        }
        return list;
    }

}
