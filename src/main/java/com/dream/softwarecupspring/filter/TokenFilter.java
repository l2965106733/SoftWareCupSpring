package com.dream.softwarecupspring.filter;

import com.dream.softwarecupspring.utils.CurrentHolder;
import com.dream.softwarecupspring.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
@Slf4j
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("令牌为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            Claims claims = JwtUtils.parseToken(token);
//            Integer userId =  Integer.valueOf(claims.get("id").toString());
//            CurrentHolder.setCurrentId(userId);
        }
        catch (Exception e) {
            log.info("令牌非法");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        log.info("令牌合法，放行");
        filterChain.doFilter(request, response);
//        CurrentHolder.remove();
    }
}
