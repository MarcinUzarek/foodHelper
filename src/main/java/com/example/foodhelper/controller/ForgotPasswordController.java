package com.example.foodhelper.controller;

import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public String sendResetLink(@RequestParam String email, HttpSession session) {
        var token = mailFacade.sendLinkToResetPass(email);
        session.setAttribute("token", token);
        return "forgot-pass";
    }

    @PostMapping("/new-pass")
    public String NewPasswordForm() {
        return "new-pass";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String password,
                                 @RequestParam String passwordRepeat, HttpSession session) {
        if (!password.equals(passwordRepeat)) {
            throw new IllegalArgumentException("Hasla nie sa takie same");
        }
        var token = (Token) session.getAttribute("token");
       userService.changePasswordWithToken(password, token);
        return "Pass-changed-success";
    }
}
