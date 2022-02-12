package com.example.foodhelper.controller;

import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import com.example.foodhelper.service.UserService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    public AuthenticationController(UserDetailsServiceImpl userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
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
}
