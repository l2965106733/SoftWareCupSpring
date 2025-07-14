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
public class TokenFilter implements Filter {

    private final UserActivityTracker userActivityTracker;

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

        // ✅ 登录/注册请求不需要 Token 校验，直接放行
        if (requestURI.contains("/login") || requestURI.contains("/register")) {
            log.info("🔓 登录/注册请求，放行: {}", requestURI);
            filterChain.doFilter(request, response); // 放行，不进行 token 校验
            return;  // 直接返回，跳过 Token 校验
        }

        // 获取 token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.warn("❌ 令牌为空，请求被拒绝: {}", requestURI);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // 令牌为空时直接返回 401 错误
        }

        // 令牌校验逻辑
        try {
            Claims claims = JwtUtils.parseToken(token);  // 解析 token
            Integer userId = Integer.valueOf(claims.get("id").toString());  // 提取用户 ID
            CurrentHolder.setCurrentId(userId);  // 设置当前用户 ID
            userActivityTracker.recordLogin(Long.valueOf(userId));  // 记录用户活动日志
            log.info("✅ 令牌校验成功，用户ID: {}", userId);

            // 放行请求
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.error("❌ Token 过期：{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // 如果 Token 过期，直接返回 401 错误
        } catch (SignatureException e) {
            log.error("❌ Token 签名无效：{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // 如果签名无效，直接返回 401 错误
        } catch (Exception e) {
            log.error("❌ Token 无效：{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // 其他 Token 无效情况，返回 401 错误
        } finally {
            // 清除当前线程上下文用户信息
            CurrentHolder.remove();
            log.info("🧹 当前用户信息已清除");
        }
    }

}
