package com.mwh.util;

import com.mwh.config.MyUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * @Author admin
 * @Description 取出用户信息
 * @Date 2026/05/12/12:50
 * @Version 1.0
 */
public class SecurityUtils {
    public static MyUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof MyUserDetails) {
            return (MyUserDetails) auth.getPrincipal();
        }
        throw new AuthenticationCredentialsNotFoundException("未登录");
    }

    /**
     * 获取当前用户ID
     * @return
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}
