package com.codemarathon.user.event;

import com.codemarathon.user.model.User;
import com.codemarathon.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private User registeredUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        registeredUser = event.getUser();
        log.info("registered User gotten from registration complete Event: {}",registeredUser);

        String verificationToken = UUID.randomUUID().toString();
        log.info("created random verification token for user: {}",verificationToken);

        userService.saveUserVerificationToken(verificationToken,registeredUser);
        log.info("saved verification token");

        String url = event.getMessageUrl()+"/api/v1/users/verifyEmail?token="+verificationToken;
        log.info("click the link to verify your registration: {}",url);
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "MasterSub Email Verification";
        String senderName = "MasterSubs";
        String mailContent = " Hi," + registeredUser.getFirstName()+ " " +
                "Thank you for your registration \n" +
                "please,follow the link below to complete your registration.\n" +
                "Verify Email to activate your account : \n "+
                url + "\n"+
                "Thank you masterSubs";

        MimeMessage message = javaMailSender.createMimeMessage();
        MessageSender(subject, senderName, mailContent, message);
    }


    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "ForgotPassword";
        String senderName = "MasterSubs";
        String mailContent = " Hi," + registeredUser.getFirstName()+ " " +
                "Thank you recently request To change you password \n" +
                "please,follow the link below to reset your password.\n" +
                "Reset Password : \n "+
                url + "\n"+
                "Thank you masterSubs";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        log.info("Message: {}",mimeMessage);
        MessageSender(subject, senderName, mailContent, mimeMessage);
    }

    private void MessageSender(String subject, String senderName, String mailContent, MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
        var messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("codemarathon2@gmail.com",senderName);
        messageHelper.setTo(registeredUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent);
        javaMailSender.send(mimeMessage);

        log.info("mail sent successfully: {}",mimeMessage);
    }
}
