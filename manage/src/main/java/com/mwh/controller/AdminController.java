package com.mwh.controller;

import com.mwh.config.MyUserDetails;
import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.service.UserService;
import com.mwh.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 管理员接口：HR和BOSS可访问
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 仅HR和BOSS查看所有员工
     */
    @GetMapping("/allPeople")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<PageResult> selectAllPeople(UserPageDTO userPageDTO) {
        return Result.success(userService.selectAllPeople(userPageDTO));
    }

    /**
     * 仅BOSS和HR新增员工
     */
    @PostMapping("/settings")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> addUser(@RequestBody User user) {
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        String pythonUrl = "http://localhost:5000/reload";
        restTemplate.postForObject(pythonUrl, null, String.class);
        boolean save = userService.save(user);
        if (!save) {
            return Result.error("新增员工失败");
        }
        return Result.success("新增成功");
    }

    /**
     * 仅HR和BOSS删除员工
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<String> deleteUser(@PathVariable Long id) {  // ✅ 接收ID参数
        try {
            userService.deleteUser(id);  // ✅ 调用Service真正执行删除（包含BOSS保护）
            return Result.success("删除员工成功");
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());  // 捕获"不能删除BOSS"等错误
        } catch (Exception e) {
            return Result.error(500, "删除失败: " + e.getMessage());
        }
    }
}
