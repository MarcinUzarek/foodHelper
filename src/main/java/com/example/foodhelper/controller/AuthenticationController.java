package com.example.foodhelper.controller;

import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.service.TokenService;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String logInForm() {
        return "login";
    }

    @GetMapping("register")
    public String SignUpForm(Model model) {
        model.addAttribute("registration", new User());
        return "register";
    }

    @PostMapping("register")
    public String signUp(User user) {
        userService.createUser(user);
        return "register";
    }

    @PostMapping("token")
    public String verifyToken(@RequestParam String value) {
        userService.activateUserWithToken(value);
        return "hello";
    }


    @GetMapping("menu")
    public String menu() {
        return "menu";
    }
}
