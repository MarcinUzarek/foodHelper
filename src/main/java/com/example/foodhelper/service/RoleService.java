package com.example.foodhelper.service;

import com.example.foodhelper.exception.custom.RoleNotExistsException;
import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.RoleTypes;
import com.example.foodhelper.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addUserRole() {
        return addRole(String.valueOf(RoleTypes.USER));
    }

    public Role addAdminRole() {
        return addRole(String.valueOf(RoleTypes.ADMIN));
    }

    public Role addModeratorRole() {
        return addRole(String.valueOf(RoleTypes.MODERATOR));
    }

    private Role addRole(String role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RoleNotExistsException(role));
    }
}
