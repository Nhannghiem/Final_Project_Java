package com.example.demo.service.impl;

import com.example.demo.entity.UsersEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class UsersDetails implements UserDetails {


    UsersEntity systemManagementEntity;

    public UsersDetails(UsersEntity userEntity){
        this.systemManagementEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(systemManagementEntity.getRole());

        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return systemManagementEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return systemManagementEntity.getEmail();
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
