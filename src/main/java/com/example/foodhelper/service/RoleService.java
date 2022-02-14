package com.example.foodhelper.service;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String MODERATOR = "MODERATOR";

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role userRole() {
        return addRole(USER);
    }

    public Role adminRole() {
        return addRole(ADMIN);
    }

    public Role moderatorRole() {
        return addRole(MODERATOR);
    }

    private Role addRole(String role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("No Such Role"));
    }
}
