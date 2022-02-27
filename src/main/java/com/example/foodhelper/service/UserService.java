package com.example.foodhelper.service;

import com.example.foodhelper.authenticated_user.AuthenticationFacade;
import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MailFacade mailFacade;
    private final AuthenticationFacade authenticationFacade;
    private final IntoleranceService intoleranceService;


    public UserService(UserRepository userRepository, TokenService tokenService, RoleService roleService, PasswordEncoder passwordEncoder, MailFacade mailFacade, AuthenticationFacade authenticationFacade, IntoleranceService intoleranceService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailFacade = mailFacade;
        this.authenticationFacade = authenticationFacade;
        this.intoleranceService = intoleranceService;
    }

    public User getLoggedUser() {
        var id = authenticationFacade.getPrincipal().getUser().getId();
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with such Id"));
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

    public void changePasswordWithToken(String password, Token token) {
        var user = token.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


    public void addIntolerance(String product) {
        Intolerance intolerance;

        var user = getLoggedUser();
        intolerance = getIntolerance(product);

        if (!user.getIntolerances().contains(intolerance)) {
            user.addIntolerance(intolerance);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("You already have it on your List");
        }
    }

    public void removeIntolerance(Long id) {
        var user = getLoggedUser();
        var intolerance = intoleranceService.findById(id);
        var intolerances = user.getIntolerances();
        intolerances.remove(intolerance);
        userRepository.save(user);
    }

    private Intolerance getIntolerance(String product) {
        Intolerance intolerance;
        if (intoleranceService.getIntoleranceByName(product).equals(Optional.empty())) {
            intolerance = intoleranceService.saveIntolerance(product);
        } else {
            intolerance = intoleranceService.getIntoleranceByName(product).get();
        }
        return intolerance;
    }

    private void ActivateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}