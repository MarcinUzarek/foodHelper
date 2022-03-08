package com.example.foodhelper.controller;

import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.model.dto.ResetPasswordDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ForgotPasswordController {

    private final MailFacade mailFacade;
    private final UserService userService;

    public ForgotPasswordController(MailFacade mailFacade, UserService userService) {
        this.mailFacade = mailFacade;
        this.userService = userService;
    }

    @GetMapping("forgot-password")
    public String forgotPassForm() {
        return "forgot-pass";
    }

    @PostMapping("forgot-password")
    public String sendResetLink(@RequestParam String email) {
        mailFacade.sendLinkToResetPass(email);
        return "forgot-pass";
    }

    @GetMapping("new-pass")
    public String NewPasswordForm(@RequestParam String token, Model model) {
        var data = new ResetPasswordDTO();
        data.setToken(token);
        model.addAttribute("resetPasswordDto", data);
        return "new-pass";
    }

    @PostMapping("change-password")
    public String changePassword(ResetPasswordDTO passwordDto) {
        userService.changePassword(passwordDto);
        return "Pass-changed-success";
    }
}
