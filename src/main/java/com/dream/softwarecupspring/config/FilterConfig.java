package com.dream.softwarecupspring.config;

import com.dream.softwarecupspring.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {

//    @Autowired
//    private TokenFilter tokenFilter;

//    @Bean
//    public FilterRegistrationBean<TokenFilter> loggingFilter() {
//        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(tokenFilter);  // 让 Spring 自动管理 TokenFilter
//        registrationBean.addUrlPatterns("/*");   // 拦截所有路径
//        registrationBean.setOrder(1);            // 优先级
//        return registrationBean;
//    }
}
