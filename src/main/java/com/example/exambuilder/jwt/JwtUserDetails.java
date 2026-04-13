package com.example.exambuilder.jwt;

import com.example.exambuilder.entity.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUserDetails implements UserDetails {

    private com.example.exambuilder.entity.Teacher teacher;

    public JwtUserDetails(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getId() {
        return teacher.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // SEM ROLES
    }

    @Override
    public String getPassword() {
        return teacher.getPassword();
    }

    @Override
    public String getUsername() {
        return teacher.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
