package com.example.foodhelper.controller;

import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AuthenticationController {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(UserRepository userRepository, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String logInForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String logIn(User user) {
            userDetailsService.loadUserByUsername(user.getUsername());
        System.out.println(user.getUsername());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        return "menu";
    }

    @GetMapping("/register")
    public String SignUpForm(Model model) {
        model.addAttribute("registration", new User());
        return "sign-up";
    }

    @PostMapping("/register")
    public String signUp(User user) {
         userRepository.save(user);
        return "sign-up";
    }
}
