package com.example.foodhelper.mail;

import com.example.foodhelper.mail.html_content_template.MailHtmlTemplate;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.service.TokenService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class MailFacade {

    private final static String VERIFICATION_URL = "http://localhost:8080/token";
    private final static String FORGOT_PASS_URL = "http://localhost:8080/new-pass";
    private final MailService mailService;
    private final TokenService tokenService;
    private final MailHtmlTemplate template = new MailHtmlTemplate();


    public MailFacade(MailService mailService, TokenService tokenService) {
        this.mailService = mailService;
        this.tokenService = tokenService;
    }

    public void sendActivationEmail(User user) {
        var tokenValue = tokenService
                .findTokenByEmail(user.getEmail()).getValue();
        try {
            mailService.sendMail(user.getEmail(), "Activate account!", "Welcome",
                    template.getHtmlActivateTemplate(VERIFICATION_URL, tokenValue));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendLinkToResetPass(String email) {
        Token token = tokenService.findTokenByEmail(email);
        try {
            mailService.sendMail(email, "Change Password", "",
                    template.getHtmlPassReset(FORGOT_PASS_URL, token.getValue()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
