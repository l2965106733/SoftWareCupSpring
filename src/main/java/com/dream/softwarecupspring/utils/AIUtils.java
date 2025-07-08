package com.dream.softwarecupspring.utils;

import com.dream.softwarecupspring.pojo.AIRequest;
import com.dream.softwarecupspring.pojo.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AIUtils {

    @Value("${ai.flaskUrl}")
    private String flaskUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Result callAI(String task, Object data, String endpoint) {
        try {
            String url = flaskUrl + endpoint;
            AIRequest request = new AIRequest(task, data);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AIRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // 处理接受后逻辑
                //1
                //System.out.println(response.getBody());
                //2
                String json = response.getBody();  // 类型是 String

                // 2. 使用 Jackson 解析 JSON
                ObjectMapper mapper = new ObjectMapper();
                // 创建一个 Map 接收解析后的数据
                Map<String, String> result = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
                // 3. 取出 "data" 字段并打印
                String re = result.get("data");
                System.out.println(re);  // 会显示为正常中文
                return Result.success(response.getBody());
            } else {
                return Result.error("AI 服务响应失败：" + response.getStatusCode());
            }
        } catch (Exception e) {
            return Result.error("请求 AI 服务出错：" + e.getMessage());
        }
    }
}
