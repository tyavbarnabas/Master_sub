package com.codemarathon.user.utils;

import com.codemarathon.user.event.VerificationToken;
import com.codemarathon.user.exceptions.PasswordResetTokenNotFoundException;
import com.codemarathon.user.model.User;
import com.codemarathon.user.password.PasswordResetToken;
import com.codemarathon.user.password.PasswordResetTokenRepository;
import com.codemarathon.user.repository.UserRepository;
import com.codemarathon.user.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidateToken {

    private final VerificationTokenRepository verificationTokenRepository;

    public String validateToken(String token) {

        VerificationToken retrievedToken = verificationTokenRepository.findByToken(token);

        if (retrievedToken == null) {

            return "Invalid retrievedToken";
        }

        User user = retrievedToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if ((retrievedToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(retrievedToken);
            return "Token is expired";
        }

        return "Password Validation Was Successfully";
    }



}
