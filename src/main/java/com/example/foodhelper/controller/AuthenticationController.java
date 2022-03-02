package com.example.foodhelper.controller;

import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        model.addAttribute("registration", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("register")
    public String signUp(@Valid @ModelAttribute("registration") UserRegisterDTO userDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.createUser(userDto);
        return "register";
    }

    @PostMapping("token")
    public String verifyToken(@RequestParam String value) {
        userService.activateUserWithToken(value);
        return "hello";
    }

}
