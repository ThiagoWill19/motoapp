package com.will.motoapp.security;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.will.motoapp.models.entity.User;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Collection<? extends GrantedAuthority> authorities;
    private final String username; //Neste caso, o username é o email do usuário
    private final String password;
    private final UUID id;
    private final String name;
    private final String profile;
    private final boolean active;



    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.profile = user.getProfile().name();
        this.active = user.isActive();
        this.authorities = Collections
            .singletonList(new SimpleGrantedAuthority("ROLE_" + user.getProfile().name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
}
