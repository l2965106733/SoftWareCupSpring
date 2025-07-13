package com.dream.softwarecupspring.utils;

import com.dream.softwarecupspring.pojo.AI.AIRequest;
import com.dream.softwarecupspring.pojo.AI.AiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AIUtils {

    @Value("${ai.flaskUrl}")
    private String flaskUrl;

    @Autowired
    private RestTemplate restTemplate;

    public AiResponse callAI(String task, Object data, String endpoint) {
        try {
            String url = flaskUrl + endpoint;
            AIRequest request = new AIRequest(task, data);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AIRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // 使用 Jackson 转为对象
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.getBody(), AiResponse.class);
            } else {
                throw new RuntimeException("AI 服务响应失败：" + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("请求 AI 服务出错：" + e.getMessage(), e);
        }
    }
}
