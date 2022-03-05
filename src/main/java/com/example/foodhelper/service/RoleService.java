package com.example.foodhelper.service;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.RoleTypes;
import com.example.foodhelper.repository.RoleRepository;
import org.springframework.stereotype.Service;

import static org.hibernate.cfg.AvailableSettings.USER;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role setUserRole() {
        return addRole(String.valueOf(RoleTypes.USER));
    }

    public Role setAdminRole() {
        return addRole(String.valueOf(RoleTypes.ADMIN));
    }

    public Role setModeratorRole() {
        return addRole(String.valueOf(RoleTypes.MODERATOR));
    }

    private Role addRole(String role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("No Such Role"));
    }
}
