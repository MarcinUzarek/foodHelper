package com.example.foodhelper.controller;

import com.example.foodhelper.authenticated_user.AuthenticationFacade;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.User;
import com.example.foodhelper.service.IntoleranceService;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserInfoController {

    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("my-account")
    public String getInfo(Model model) {

        var user = userService.getLoggedUser();
        model.addAttribute("userinfo", user);
        return "user-profile";
    }

    @PostMapping("/add-intolerance")
    @Transactional
    public String addIntolerance(@RequestParam String product) {

        userService.addIntolerance(product);
        return "redirect:/my-account";
    }

    @GetMapping("/remove-intolerance/{id}")
    public String removeIntolerance(@PathVariable Long id){

        userService.removeIntolerance(id);
        return "redirect:/my-account";
    }
}

