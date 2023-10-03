package com.codemarathon.user.controllers;

import com.codemarathon.user.constants.Role;
import com.codemarathon.user.dto.AuthenticationResponse;
import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.dto.UserResponse;
import com.codemarathon.user.event.ApplicationUrl;
import com.codemarathon.user.event.RegistrationCompleteEvent;
import com.codemarathon.user.model.User;
import com.codemarathon.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final ApplicationUrl applicationUrl;

    private final ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerAdmin(RegisterRequest registerRequest, HttpServletRequest request){
        log.info("from register user controller");
        return ResponseEntity.ok(userService.registerUser(registerRequest, Role.ADMIN,request));
    }

}
