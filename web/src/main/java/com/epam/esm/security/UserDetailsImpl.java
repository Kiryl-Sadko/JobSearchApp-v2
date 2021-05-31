package com.epam.esm.security;

import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {
    
    private final String userName;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    public UserDetailsImpl(String userName, String password, List<SimpleGrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails fromUser(User user) {
        Set<SimpleGrantedAuthority> authorities = SecurityRole.getAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
