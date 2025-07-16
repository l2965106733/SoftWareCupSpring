package com.dream.softwarecupspring.Controller.TeacherController;

import com.dream.softwarecupspring.Service.TeacherService.TeacherResourceService;
import com.dream.softwarecupspring.pojo.AI.AiResponse;
import com.dream.softwarecupspring.pojo.AI.TeachingPlanQueryParam;
import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.pojo.Resource.Resource;
import com.dream.softwarecupspring.utils.AIUtils;
import com.dream.softwarecupspring.utils.AliyunOSSOperator;
import com.dream.softwarecupspring.utils.CurrentHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/teacher/resource")
public class TeacherResourceController {
    @Autowired
    private AIUtils aiUtils;

    @Autowired
    private TeacherResourceService teacherResourceService;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result uploadResource(@RequestBody Resource resource) {
        Long id = teacherResourceService.uploadResource(resource);
        return Result.success(id);
    }

    @PutMapping("/update")
    public Result updateResource(@RequestBody Resource resource) {
        teacherResourceService.updateResource(resource);
        return Result.success();
    }

    @DeleteMapping("/{resourceId}")
    public Result deleteResource(@PathVariable Long resourceId) {
        teacherResourceService.deleteResource(resourceId);
        return Result.success();
    }

    @GetMapping("/{teacherId}")
    public Result getResourceList(@PathVariable Long teacherId) {
        return Result.success(teacherResourceService.getResourceList(teacherId));
    }

    @PostMapping("/aiTeachingPlan")
    public Result generateTeachingPlan(@RequestBody TeachingPlanQueryParam teachingPlanQueryParam) throws Exception {
        AiResponse response = aiUtils.callAI("generateTeachingPlan", teachingPlanQueryParam, "/ai");
        String docxUrl = response.getData();
        URL url = new URL(docxUrl);
        File tempFile = File.createTempFile("teaching_plan_", ".docx");
        try (InputStream in = url.openStream()) {
            Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        String fileName = "teaching_plan_" + System.currentTimeMillis() + ".docx";
        byte[] fileBytes = Files.readAllBytes(tempFile.toPath());
        String ossUrl = aliyunOSSOperator.upload(fileBytes, fileName);

//        Long teacherId = Long.valueOf(CurrentHolder.getCurrentId());
        Long teacherId = 56L;
        Resource resource = new Resource();
        resource.setTeacherId(teacherId);
        resource.setResourceName("教学计划.docx");
        resource.setResourceUrl(ossUrl);
        resource.setResourceType("docx");
        resource.setDescription("由 AI 自动生成的教学计划");
        resource.setFileSize((long) fileBytes.length);

        teacherResourceService.uploadResource(resource);
        Map<String, Object> result = new HashMap<>();
        result.put("docxUrl", ossUrl);
        return Result.success(result);
    }

}
