package com.example.foodhelper.mail.html_content_template;

public class MailHtmlTemplate {

    private String htmlContent;

    public String getHtmlActivateTemplate(String link, String token) {

        htmlContent = "<h2>Welcome</h2>";
        htmlContent += "<h3>HI</h3>";
        htmlContent += "<form action='" + link + "' method='post'>";
        htmlContent += "<input type='hidden' name='value' value='" + token + "'>";
        htmlContent += "<button type='submit'>Go</button>";
        htmlContent += "</form>";
        return htmlContent;
    }

    public String getHtmlPassReset(String link, String token) {

        htmlContent = "<h2>Working great</h2>";
        htmlContent += "<h3>HI</h3>";
        htmlContent += "<form action='" + link + "' method='post'>";
        htmlContent += "<input type='hidden' name='value' value='" + token + "'>";
        htmlContent += "<button type='submit'>Change password</button>";
        htmlContent += "</form>";
        return htmlContent;
    }
}
