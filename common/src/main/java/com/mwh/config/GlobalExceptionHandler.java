package com.mwh.config;

import com.mwh.result.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理，确保Spring Security异常也返回统一的Result格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDenied(AccessDeniedException e) {
        return Result.error(403, "权限不足，无法访问该资源");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<?> handleAuthentication(AuthenticationException e) {
        return Result.error(401, "认证失败: " + e.getMessage());
    }
}
