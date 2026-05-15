package com.mwh.controller;

import com.mwh.config.MyUserDetails;
import com.mwh.dto.LoginDTO;
import com.mwh.result.Result;
import com.mwh.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/**
 * 登录控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /**
     * 登录接口
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(principal.getUsername(), principal.getRole());
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("username", principal.getUsername());
            data.put("role", principal.getRole());
            data.put("userid", String.valueOf(principal.getUserId()));
            return Result.success("登录成功", data);
        } catch (BadCredentialsException e) {
            return Result.error(401, "用户名或密码错误");
        } catch (DisabledException e) {
            return Result.error(401, "账号已被禁用");
        } catch (Exception e) {
            return Result.error(500, "登录失败: " + e.getMessage());
        }
    }
}
