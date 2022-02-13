package com.example.foodhelper.controller;

import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.TokenRepository;
import com.example.foodhelper.repository.UserRepository;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AuthenticationController {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public AuthenticationController(UserService userService, TokenRepository tokenRepository, UserRepository userRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String logInForm() {
        return "login";
    }

    @GetMapping("/register")
    public String SignUpForm(Model model) {
        model.addAttribute("registration", new User());
        return "sign-up";
    }

    @PostMapping("/register")
    public String signUp(User user) {
         userService.addUser(user);
        return "sign-up";
    }

    @GetMapping("/token")
    public String verifyToken(@RequestParam String value) {
        Token token = tokenRepository.findByValue(value);
        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "hello";
    }
}
