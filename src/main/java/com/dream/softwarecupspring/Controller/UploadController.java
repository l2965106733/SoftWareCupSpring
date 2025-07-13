package com.dream.softwarecupspring.Controller;

import com.dream.softwarecupspring.pojo.Common.Result;
import com.dream.softwarecupspring.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        String url = aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        return Result.success(url);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam String url) throws Exception {
        String objectName = aliyunOSSOperator.extractObjectName(url);
        aliyunOSSOperator.delete(objectName);
        return Result.success("删除成功");
    }

}
