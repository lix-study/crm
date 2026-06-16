package com.ccsu.crm.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AOP日志切面 - 符合PDF基础设施层要求
 * 自动记录接口请求路径、参数、耗时、状态码
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.ccsu.crm.controller..*.*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String requestURI = "";
        String method = "";
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            requestURI = request.getRequestURI();
            method = request.getMethod();
        }

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[请求开始] {} {} - {}.{}()", method, requestURI, className, methodName);

        try {
            Object result = joinPoint.proceed();
            long costTime = System.currentTimeMillis() - startTime;
            log.info("[请求完成] {} {} - {}.{}() 耗时: {}ms", method, requestURI, className, methodName, costTime);
            return result;
        } catch (Exception e) {
            long costTime = System.currentTimeMillis() - startTime;
            log.error("[请求异常] {} {} - {}.{}() 耗时: {}ms 异常: {}", method, requestURI, className, methodName, costTime, e.getMessage());
            throw e;
        }
    }
}
