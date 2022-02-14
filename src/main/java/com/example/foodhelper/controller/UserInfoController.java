package com.example.foodhelper.controller;

import com.example.foodhelper.authenticated_user.AuthenticationFacade;
import com.example.foodhelper.service.IntoleranceService;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-info")
public class UserInfoController {

    private final AuthenticationFacade authenticationFacade;
    private final IntoleranceService intoleranceService;
    private final UserService userService;

    public UserInfoController(AuthenticationFacade authenticationFacade, IntoleranceService intoleranceService, UserService userService) {
        this.authenticationFacade = authenticationFacade;
        this.intoleranceService = intoleranceService;
        this.userService = userService;
    }

    @GetMapping
    public String getInfo(Model model) {
        var user = authenticationFacade.getPrincipal().getUser();
        model.addAttribute("userinfo", user);
        return "user-info";
    }

    @PostMapping("/addintolerance")
    public String addIntolerance(@RequestParam String product) {
        var user = authenticationFacade.getPrincipal().getUser();
        user.getIntolerances().add(intoleranceService.getProduct(product));
        userService.saveUpdates(user);

        return "redirect:/user-info";
    }
}

