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
@Component
public class TokenFilter{ // implements Filter {
//
//    private final UserActivityTracker userActivityTracker;
//
//    @Autowired
//    public TokenFilter(UserActivityTracker userActivityTracker) {
//        this.userActivityTracker = userActivityTracker;
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        log.info("✅ TokenFilter 被执行了！");
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String requestURI = request.getRequestURI();
//
//        // ✅ 登录/注册请求不需要 Token 校验，直接放行
//        if (requestURI.contains("/login")) {
//            log.info("🔓 登录/注册请求，放行: {}", requestURI);
//            filterChain.doFilter(request, response); // 放行，不进行 token 校验
//            return;  // 直接返回，跳过 Token 校验
//        }
//
//        // 获取 token
//        String token = request.getHeader("token");
//        log.info("提取的令牌: {}", token);  // 打印令牌内容
//        if (token == null || token.isEmpty()) {
//            log.warn("❌ 令牌为空，请求被拒绝: {}", requestURI);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return; // 令牌为空时直接返回 401 错误
//        }
//        // 令牌校验逻辑
//        try {
//            Claims claims = JwtUtils.parseToken(token);
//            Integer userId = Integer.valueOf(claims.get("id").toString());
//            CurrentHolder.setCurrentId(userId);
//            userActivityTracker.recordLogin(Long.valueOf(userId));
//            log.info("✅ 令牌校验成功，用户ID: {}", userId);
//
//            filterChain.doFilter(request, response);
//
//        } catch (ExpiredJwtException | SignatureException e) {
//            // Token 相关异常
//            log.error("❌ Token 错误：{}", e.getMessage());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//
//        } catch (Exception e) {
//            // 非 token 异常，不处理，让后面的异常处理器来兜底
//            log.error("⚠ 业务异常：{}", e.getMessage());
//            throw e;  // 👈 交给全局异常处理器处理
//        }
//    }

}