package com.example.foodhelper.utils.authentication_info;

import com.example.foodhelper.model.user_details.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticationFacade {
    
    Authentication getAuthentication();
    Collection<? extends GrantedAuthority> getAuthorities();
    UserDetailsImpl getPrincipal();
    Object getName();
}
