package com.example.foodhelper.authenticated_user;


import com.example.foodhelper.user_details.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthentication().getAuthorities();
    }

    @Override
    public UserDetailsImpl getPrincipal() {
        return (UserDetailsImpl) getAuthentication().getPrincipal();
    }

    @Override
    public Object getName() {
        return getAuthentication().getName();
    }
}