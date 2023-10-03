package com.codemarathon.user.controllers;

import com.codemarathon.user.config.jwtConfig.JwtService;
import com.codemarathon.user.constants.Role;
import com.codemarathon.user.dto.AuthenticationResponse;
import com.codemarathon.user.dto.AuthRequest;
import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.event.ApplicationUrl;
import com.codemarathon.user.event.RegistrationCompleteEvent;
import com.codemarathon.user.exceptions.UsersNotFoundException;
import com.codemarathon.user.model.User;
import com.codemarathon.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class RegisterController {
    private final UserService userService;


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


}
