package com.example.foodhelper.authenticated_user;

import com.example.foodhelper.user_details.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticationFacade {
    
    Authentication getAuthentication();
    Collection<? extends GrantedAuthority> getAuthorities();
    UserDetailsImpl getPrincipal();
    Object getName();
}
