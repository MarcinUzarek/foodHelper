package com.example.foodhelper.service;

import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MailFacade mailFacade;


    public UserService(UserRepository userRepository, TokenService tokenService, RoleService roleService, PasswordEncoder passwordEncoder, MailFacade mailFacade) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailFacade = mailFacade;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.setUserRole());
        userRepository.save(user);
        mailFacade.sendActivationEmail(user);
    }

    public void activateUserWithToken(String tokenValue) {
        var token = tokenService.findToken(tokenValue);
        User user = token.getUser();
        ActivateUser(user);
    }

    public void saveUpdates(User user) {
        userRepository.save(user);
    }

    public void changePasswordWithToken(String password, Token token) {
        var user = token.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private void ActivateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }


}