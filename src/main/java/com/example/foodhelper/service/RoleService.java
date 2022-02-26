package com.example.foodhelper.service;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String MODERATOR = "MODERATOR";

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role setUserRole() {
        return addRole(USER);
    }

    public Role setAdminRole() {
        return addRole(ADMIN);
    }

    public Role setModeratorRole() {
        return addRole(MODERATOR);
    }

    private Role addRole(String role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("No Such Role"));
    }
}
