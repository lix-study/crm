package com.ccsu.crm.interceptor;

import com.ccsu.crm.common.UnauthorizedException;
import com.ccsu.crm.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 认证拦截器 - 符合PDF规范8.3
 * 统一校验Token有效性
 * 请求头格式：Authorization: Bearer {token}
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 放行OPTIONS预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException(401, "未登录或Token已过期，请重新登录");
        }

        String token = authHeader.substring(7);
        if (!JwtUtil.validateToken(token)) {
            throw new UnauthorizedException(401, "Token无效或已过期，请重新登录");
        }

        // 将用户信息存入request属性，供后续使用
        request.setAttribute("userId", JwtUtil.getUserId(token));
        request.setAttribute("username", JwtUtil.getUsername(token));

        return true;
    }
}
