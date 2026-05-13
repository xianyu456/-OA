package com.mwh.controller;

import com.mwh.config.MyUserDetails;
import com.mwh.result.Result;
import com.mwh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 演示角色权限控制：不同角色访问不同接口
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /** 所有已认证用户均可访问（EMPLOYEE/HR/BOSS） */
    @GetMapping("/profile")
    public Result<Map<String, Object>> getProfile(@AuthenticationPrincipal MyUserDetails principal) {
        Map<String, Object> data = Map.of(
                "userId", principal.getUserId(),
                "username", principal.getUsername(),
                "realName", principal.getRealName(),
                "role", principal.getRole()
        );
        return Result.success(data);
    }

    /** 仅HR和BOSS可访问 */
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<String> listAllUsers() {
        return Result.success("所有用户列表（仅HR/老板可见）");
    }

    /** 仅员工本人可访问 */
    @GetMapping("/my-attendance")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Result<String> myAttendance(@AuthenticationPrincipal MyUserDetails principal) {
        return Result.success(principal.getRealName() + " 的考勤记录");
    }

    /** 仅HR和BOSS可访问 */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<String> updateUser(@AuthenticationPrincipal MyUserDetails principal) {
        return Result.success("更新用户信息（仅HR/老板可见）");
    }

}
