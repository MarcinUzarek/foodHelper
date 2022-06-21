package com.example.foodhelper.mail;

import com.example.foodhelper.mail.html_content_template.MailHtmlTemplate;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class MailFacade {

    private final String host;
    private final MailService mailService;
    private final TokenService tokenService;
    private final MailHtmlTemplate template = new MailHtmlTemplate();


    public MailFacade(@Value("${food-helper.host}") String host, MailService mailService, TokenService tokenService) {
        this.host = host;
        this.mailService = mailService;
        this.tokenService = tokenService;
    }

    public void sendActivationEmail(User user) {
        var VERIFICATION_URL = host + "/token";

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
        var FORGOT_PASS_URL = host + "/new-pass";

        Token token = tokenService.findTokenByEmail(email);
        try {
            mailService.sendMail(email, "Change Password", "",
                    template.getHtmlPassReset(FORGOT_PASS_URL, token.getValue()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
