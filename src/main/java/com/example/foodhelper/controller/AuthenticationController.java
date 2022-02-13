package com.example.foodhelper.controller;

import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import com.example.foodhelper.service.TokenService;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationController(UserService userService, TokenService tokenService, UserRepository userRepository) {
        this.userService = userService;
        this.tokenService = tokenService;
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
        userService.createUser(user);
        return "sign-up";
    }

    @GetMapping("/token")
    public String verifyToken(@RequestParam String value) {

        var token = tokenService.findToken(value);
        User user = token.getUser();
        userService.ActivateUser(user);
        return "hello";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }
}
