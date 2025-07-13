package com.dream.softwarecupspring.filter;

import com.dream.softwarecupspring.pojo.User.UserActivityTracker;
import com.dream.softwarecupspring.utils.CurrentHolder;
import com.dream.softwarecupspring.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component  // 确保 TokenFilter 被 Spring 管理
public class TokenFilter implements Filter {

    private final UserActivityTracker userActivityTracker;

    // 使用构造器注入
    @Autowired
    public TokenFilter(UserActivityTracker userActivityTracker) {
        this.userActivityTracker = userActivityTracker;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("✅ TokenFilter 被执行了！");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        // 登录请求不需要验证 Token，直接放行
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            filterChain.doFilter(request, response);
            return;
        }

        // 获取 token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("令牌为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Claims claims = JwtUtils.parseToken(token);
            Integer userId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(userId);
            userActivityTracker.recordLogin(Long.valueOf(userId));  // 自动记录登录

        } catch (ExpiredJwtException e) {
            log.error("Token 过期：{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (SignatureException e) {
            log.error("Token 签名无效：{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            log.error("Token 无效：{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        log.info("令牌合法，放行");
        filterChain.doFilter(request, response);

        // 清除当前用户 ID，避免内存泄漏
        CurrentHolder.remove();
    }
}
