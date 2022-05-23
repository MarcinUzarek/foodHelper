package com.example.foodhelper.controller;

import com.example.foodhelper.exception.custom.ItemDuplicateException;
import com.example.foodhelper.model.dto.IntoleranceDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAuthority('USER')")
public class MyAccountController {

    private final UserService userService;

    public MyAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("my-account")
    public String getInfo(Model model) {

        var userShowDto = userService.getLoggedUserAsDto();
        model.addAttribute("userinfo", userShowDto);
        return "user-profile";
    }

    @PostMapping("add-intolerance")
    public String addIntolerance(IntoleranceDTO intolerance) {
        userService.addIntolerance(intolerance);
        return "redirect:/my-account";
    }

    @GetMapping("remove-intolerance/{id}")
    public String removeIntolerance(@PathVariable Long id) {
        userService.removeIntoleranceById(id);
        return "redirect:/my-account";
    }

    @ExceptionHandler(ItemDuplicateException.class)
    public String handleItemDuplicateExceptionLocally() {
        return "redirect:/my-account";
    }
}

