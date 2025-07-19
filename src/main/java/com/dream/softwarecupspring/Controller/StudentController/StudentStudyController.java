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
        System.out.println(response);
        String rawText = response.getData();
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
