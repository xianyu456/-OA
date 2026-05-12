package com.mwh.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mwh.config.MyUserDetails;
import com.mwh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        User one = userService.getOne(userLambdaQueryWrapper);
        if (one == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new MyUserDetails(one);
    }
}
