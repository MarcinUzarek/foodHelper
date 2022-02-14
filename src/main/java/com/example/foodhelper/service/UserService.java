package com.example.foodhelper.service;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.userRole());
        userRepository.save(user);
        tokenService.sendToken(user);
    }

    public void ActivateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveUpdates(User user) {
        userRepository.save(user);
    }


}