package com.example.foodhelper.authentication_info;


import com.example.foodhelper.exception.custom.UserNotLoggedException;
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new UserNotLoggedException();
        }
        return authentication;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthentication().getAuthorities();
    }

    @Override
    public UserDetailsImpl getPrincipal() {
        var principal = getAuthentication().getPrincipal();
        return (UserDetailsImpl) principal;
    }

    @Override
    public Object getName() {
        return getAuthentication().getName();
    }
}