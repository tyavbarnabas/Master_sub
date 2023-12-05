package com.codemarathon.user.password;

import com.codemarathon.user.event.ApplicationUrl;
import com.codemarathon.user.event.RegistrationCompleteEventListener;
import com.codemarathon.user.exceptions.InvalidPasswordException;
import com.codemarathon.user.exceptions.PasswordResetTokenNotFoundException;
import com.codemarathon.user.exceptions.TokenExpiredException;
import com.codemarathon.user.exceptions.UsersNotFoundException;
import com.codemarathon.user.model.User;
import com.codemarathon.user.repository.UserRepository;
import com.codemarathon.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordResetService{





    private final PasswordResetTokenRepository passwordResetTokenRepository;



    public void createPasswordResetTokenForUser(String passwordToken,User user){

        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken,user);

        PasswordResetToken saveToken = passwordResetTokenRepository.save(passwordResetToken);
        log.info("saved token: {}", saveToken);

    }


    public String validatePasswordResetToken(String passwordToken) {

        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(passwordToken);

        if (passwordResetToken == null) {

            throw new PasswordResetTokenNotFoundException("invalid token");
        }

        User userGotten = passwordResetToken.getUser();
        log.info("User: {}", userGotten);

        Calendar calendar = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);

            throw new TokenExpiredException("Link Expired, please Resend link");
        }

        return "valid";
    }


    public Optional<User> findUserByPasswordToken(String passwordToken){
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordToken).getUser());
    }








}
