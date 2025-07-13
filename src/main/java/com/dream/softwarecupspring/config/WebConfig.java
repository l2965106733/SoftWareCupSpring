package com.dream.softwarecupspring.config;

import com.dream.softwarecupspring.interceptor.SystemLogInterceptor;
import com.dream.softwarecupspring.interceptor.UserActivityInterceptor;
import com.dream.softwarecupspring.interceptor.ResourceAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserActivityInterceptor userActivityInterceptor;
    @Autowired
    private ResourceAccessInterceptor resourceAccessInterceptor;

    @Autowired
    private SystemLogInterceptor systemLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userActivityInterceptor).addPathPatterns("/**"); // 拦截所有请求
        registry.addInterceptor(resourceAccessInterceptor).addPathPatterns("/resource/**"); // 只拦截资源相关请求
        registry.addInterceptor(systemLogInterceptor).addPathPatterns("/**");  // 拦截所有请求
    }
}
