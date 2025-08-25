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
import com.dream.softwarecupspring.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    @GetMapping("/getChatDetailById/{id}")
    public Result getChatDetailById(@PathVariable Long id) {
        return Result.success(studentStudyService.getChatDetailById(id));
    }

    @GetMapping("/mistakes/{studentId}")
    public Result getMistakes(@PathVariable Long studentId) {
        List<Question> res = studentStudyService.getMistakes(studentId);
        return Result.success(res);
    }




    @GetMapping("getChatList/{userId}")
    public Result getChatList(@PathVariable Long userId) {
        List<AiQuestion> chatList =  studentStudyService.getChatList(userId);
        return Result.success(chatList);
    }

    @GetMapping("/aiQuestions/{studentId}")
    public Result getAiQuestions(@PathVariable Long studentId, @RequestParam(defaultValue = "50") int limit) {
        return Result.success(studentStudyService.getAiQuestions(studentId, limit));
    }

    @PostMapping("/aiQuestion")
    public Result recordAiQuestion(@RequestBody AiQuestion aiQuestion) {

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

    @GetMapping("/getChatName/{chatId}")
    public Result getChatName(@PathVariable Integer chatId) {
        String name = studentStudyService.getChatName(chatId);
        return Result.success(name);
    }

    @PostMapping("/setChatName/{chatId}")
    public Result setChatName(@PathVariable Integer chatId,
                              @RequestBody Map<String, String> body) {
        String name = body.get("name");          // ← 从 JSON 里取 name
        studentStudyService.setChatName(chatId, name);
        return Result.success();
    }

    @DeleteMapping("/deleteChat/{chatId}")
    public Result deleteChat(@PathVariable Integer chatId) {
        studentStudyService.deleteChat(chatId);
        return Result.success();
    }

    @PostMapping("/generateAIAdvice")
    public Result generateAIAdvice() {
        Long studentId = Long.valueOf(CurrentHolder.getCurrentId());
        List<Question> tempQuestions = studentStudyService.getMistakes(studentId);
        Map<String,List<Question>> questions = new HashMap<>();
        questions.put("questions",tempQuestions);
        AiResponse response = aiUtils.callAI("generateAIAdvice", questions, "/ai");

        String rawText = response.getData();
        System.out.println(rawText);

        return Result.success(rawText);
    }

    @PostMapping("/generateAIQuestion")
    public Result generateAIQuestion(@RequestBody TquestionQueryParam tquestionQueryParam) {
        tquestionQueryParam.setKnowledge(tquestionQueryParam.getKnowledge()+ ',' +tquestionQueryParam.getRemark());
        tquestionQueryParam.setRemark(null);
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


    // 匹配代码块（markdown 或语言标记）
    private static final Pattern QUESTION_BLOCK = Pattern.compile(
            "\\s*(?:【题号】\\s*(\\d+)|【\\s*(\\d+)】)\\s*" +          // group(1)/group(2): 题号
                    "(?:【题型】\\s*([\\s\\S]*?))?" +                        // group(3): 题型（可选）
                    "\\s*【题干】\\s*([\\s\\S]*?)" +                         // group(4): 题干（懒惰直到答案/解析）
                    "(?:\\s*【选项】\\s*([\\s\\S]*?))?" +                    // group(5): 选项（可选）
                    "\\s*【正确答案】\\s*([\\s\\S]*?)" +                     // group(6): 正确答案
                    "\\s*【解析】\\s*([\\s\\S]*?)" +                         // group(7): 解析
                    "(?=\\s*(?:【题号】\\s*\\d+|【\\s*\\d+】)|\\z)"           // 直到下一个题号（允许空白）或结尾
    );

    private List<Question> parseQuestions(String rawText, String type) {
        List<Question> list = new ArrayList<>();
        if (rawText == null || rawText.isBlank()) return list;

        Matcher m = QUESTION_BLOCK.matcher(rawText);
        while (m.find()) {
            // 题号可能在 group(1) 或 group(2)
            String qNum    = m.group(1) != null ? m.group(1).trim() :
                    (m.group(2) != null ? m.group(2).trim() : "");

            String qType   = m.group(3) != null ? m.group(3).trim() : type;
            String stem    = m.group(4) != null ? m.group(4).trim() : "";
            String options = m.group(5) != null ? m.group(5).trim() : "";
            String answer  = m.group(6) != null ? m.group(6).trim() : "";
            String explain = m.group(7) != null ? m.group(7).trim() : "";

            // 如果没有【选项】，但题干里包含了 A. B. C. D.，也拼接进去
            if (options.isEmpty() && stem.matches("(?s).*\\n?A\\..*")) {
                // 题干里已经包含选项，不需要额外拼接
            } else if (!options.isEmpty()) {
                stem = stem + "\n" + options;
            }

            Question q = new Question();
            q.setId(qNum.isEmpty() ? null : Integer.valueOf(qNum));
            q.setContent(stem);
            q.setAnswer(answer);
            q.setExplain(explain);
            q.setType(qType);
            q.setScore(null);
            q.setCreatedTime(LocalDateTime.now());
            q.setUpdatedTime(LocalDateTime.now());
            list.add(q);
        }
        return list;
    }

}
