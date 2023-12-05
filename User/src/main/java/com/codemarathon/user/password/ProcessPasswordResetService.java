package com.codemarathon.user.password;

import com.codemarathon.user.event.ApplicationUrl;
import com.codemarathon.user.event.RegistrationCompleteEventListener;
import com.codemarathon.user.exceptions.InvalidPasswordException;
import com.codemarathon.user.exceptions.UsersNotFoundException;
import com.codemarathon.user.model.User;
import com.codemarathon.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessPasswordResetService {
    private final UserService userService;
    private final RegistrationCompleteEventListener eventListener;
    private final ApplicationUrl applicationUrl;


    public String resetPasswordRequest(PasswordResetRequest passwordResetRequest, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        log.info("Processing password reset request...");

        Optional<User> user = userService.findUserEmail(passwordResetRequest.getEmail());

        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            return passwordResetEmailLink(user.get(), applicationUrl.messageUrl(request), passwordResetToken);
        }

        throw new UsersNotFoundException("User not found for email: " + passwordResetRequest.getEmail());
    }



    public String processPasswordReset(PasswordResetRequest passwordResetRequest, String passwordResetToken) {
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);

        if (!tokenValidationResult.equalsIgnoreCase("valid")) {
            throw new InvalidPasswordException("Invalid password reset token");
        }

        User user = userService.findUserByPasswordToken(passwordResetToken);
        if (user != null) {
            userService.resetUserPassword(user, passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }

        return "Invalid password reset token";
    }


    public String passwordResetEmailLink(User user, String messageUrl, String passwordResetToken) throws MessagingException, UnsupportedEncodingException {
        String url = messageUrl +"/api/v1/users/reset-password?token="+ passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("click the link to reset your password: {}",url);

        return url;
    }

}
