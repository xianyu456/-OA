package com.mwh.controller;

import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.service.UserService;
import com.mwh.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员接口：HR和BOSS可访问
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    /** 仅HR和BOSS
     * 查看所有用户
     * */
    @GetMapping("/allPeople")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<PageResult> selectAllPeople(UserPageDTO userPageDTO) {
        return Result.success(userService.selectAllPeople(userPageDTO));
    }

    /** 仅BOSS */
    @GetMapping("/settings")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> systemSettings() {
        return Result.success("系统设置（仅老板可访问）");
    }
}
