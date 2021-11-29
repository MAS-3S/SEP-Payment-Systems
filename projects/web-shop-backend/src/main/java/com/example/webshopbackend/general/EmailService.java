package com.example.webshopbackend.general;

import com.example.webshopbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "webshop.bachelor@gmail.com";
        String senderName = "WebShop SEP";
        String subject = "Verifikacija naloga prilikom registracije";
        String content = "Poštovani [[name]],<br>"
                + "Kliknite na link ispod, kako biste potrvrdili Vašu registraciju:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Verifikuj</a></h3>"
                + "Svako dobro,<br>"
                + "Vaš webshop.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteUrl + "/api/users/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        mailSender.send(message);

    }


}
