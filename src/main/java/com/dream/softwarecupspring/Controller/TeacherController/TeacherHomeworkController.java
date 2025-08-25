package com.dream.softwarecupspring.Controller.TeacherController;

import com.dream.softwarecupspring.Service.TeacherService.TeacherHomeworkService;
import com.dream.softwarecupspring.pojo.AI.AiJudge;
import com.dream.softwarecupspring.pojo.AI.AiResponse;
import com.dream.softwarecupspring.pojo.AI.JudgeQuestion;
import com.dream.softwarecupspring.pojo.Homework.*;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("generateQuestionJudge")
    public Result generateQuestionJudge(@RequestBody JudgeQuestion judgeQuestion) {
        System.out.println(judgeQuestion);

        AiResponse response = aiUtils.callAI("generateQuestionJudge", judgeQuestion, "/ai");
        String rawText = response.getData();
        System.out.println(rawText);

        JudgeResponse res = parseJudgeResponse(rawText);

        return Result.success(res);
    }

    // 解析AI返回的字符串
    private JudgeResponse parseJudgeResponse(String rawText) {
        JudgeResponse jr = new JudgeResponse();

        if (rawText == null || rawText.isBlank()) {
            jr.setScore(0);
            jr.setFeedback("未获取到批改结果");
            return jr;
        }

        // 正则匹配 【得分】 和 【反馈】
        Pattern pattern = Pattern.compile("【得分】(\\d+)\\s*【反馈】([\\s\\S]*)");
        Matcher matcher = pattern.matcher(rawText);

        if (matcher.find()) {
            jr.setScore(Integer.parseInt(matcher.group(1).trim()));
            jr.setFeedback(matcher.group(2).trim());
        } else {
            // 如果格式不对，直接兜底返回原文
            jr.setScore(0);
            jr.setFeedback(rawText.trim());
        }
        return jr;
    }

    @PostMapping("/aiQuestion")
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
            stem = removeQuotedText(stem);
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
    public static String removeQuotedText(String input) {
        // 查找第一个 """ 出现的位置
        int startIndex = input.indexOf("\"\"\"");

        // 如果找到了 """，则去掉其中的文本和 """ 包裹
        if (startIndex != -1) {
            // 查找第二个 """ 出现的位置
            int endIndex = input.indexOf("\"\"\"", startIndex + 3);

            // 如果找到了第二个 """，则去掉其中的部分
            if (endIndex != -1) {
                // 返回去掉包裹文本后的新字符串
                return input.substring(0, startIndex) + input.substring(endIndex + 3);
            }
        }

        // 如果没有找到 """，则返回原始字符串
        return input;
    }

}
