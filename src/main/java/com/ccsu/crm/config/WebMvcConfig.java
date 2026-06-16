package com.ccsu.crm.config;

import com.ccsu.crm.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置 - 注册拦截器
 * 符合PDF规范：AuthInterceptor统一校验Token有效性
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/login",       // 登录接口放行
                        "/api/auth/register",     // 注册接口放行
                        "/api/auth/refresh",      // Token刷新放行
                        "/api/auth/captcha",      // 验证码放行
                        "/test",                  // 测试接口放行
                        "/doc.html",              // Knife4j文档放行
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**"
                );
    }
}
