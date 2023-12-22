package com.codemarathon.user.controllers;

import com.codemarathon.user.constants.Role;
import com.codemarathon.user.dto.AuthenticationResponse;
import com.codemarathon.user.dto.AuthRequest;
import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.password.PasswordResetRequest;
import com.codemarathon.user.password.ProcessPasswordResetService;
import com.codemarathon.user.service.UserService;
import com.codemarathon.user.token.TokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class RegisterController {
    private final UserService userService;
    private final ProcessPasswordResetService processPasswordResetService;

    private final TokenService tokenService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest,final HttpServletRequest request) {
        log.info("from register user controller");
        return ResponseEntity.ok(userService.registerUser(registerRequest, Role.USER,request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthRequest request){
        log.info("entering the authenticate controller{}",request);
        return ResponseEntity.ok(userService.authenticate(request));

    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        String verificationResult = userService.verifyEmail(token);
        return ResponseEntity.ok(verificationResult);
    }


    @PostMapping("/password-reset-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest,
                                                       HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        String passwordResetUrl = processPasswordResetService.resetPasswordRequest(passwordResetRequest, request);
        return ResponseEntity.ok(passwordResetUrl);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                                @RequestParam("token") String passwordResetToken) {
        String result = processPasswordResetService.processPasswordReset(passwordResetRequest, passwordResetToken);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/generateToken/{id}")
    public ResponseEntity<String> generateToken(@PathVariable("id") Long userId) {
        String jwtToken = tokenService.getValidTokenForUser(userId);
        return ResponseEntity.ok(jwtToken);
    }



}
