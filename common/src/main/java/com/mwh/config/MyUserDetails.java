package com.mwh.config;

import com.mwh.Enum.UserEnum;
import com.mwh.pojo.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRoleName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == 1;
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getRealName() {
        return user.getRealName();
    }

    public String getRole() {
        return String.valueOf(user.getRole().getRole());
    }

    public UserEnum getRoleEnum() {
        return user.getRole();
    }
}
