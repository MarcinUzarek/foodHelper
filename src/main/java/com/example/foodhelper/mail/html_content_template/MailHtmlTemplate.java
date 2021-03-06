package com.example.foodhelper.mail.html_content_template;

public class MailHtmlTemplate {

    public String getHtmlActivateTemplate(String link, String token) {

        return
                "<h2>Welcome</h2>" +
                        "<h3>Thanks for signing up! Have fun using app.</h3>" +
                        "<form action='" + link + "' method='post'>" +
                        "<input type='hidden' name='value' value='" + token + "'>" +
                        "<button type='submit'>Activate account</button>" +
                        "</form>";

    }

    public String getHtmlPassReset(String link, String token) {

        return
                "<h2>Change your password</h2>" +
                        "<h3>HI</h3>" +
                        "<form action='" + link + "' method='get'>" +
                        "<input type='hidden' name='token' value='" + token + "'>" +
                        "<button type='submit'>Change password</button>" +
                        "</form>";
    }
}
