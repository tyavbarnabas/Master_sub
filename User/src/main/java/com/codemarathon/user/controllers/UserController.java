package com.codemarathon.user.controllers;

import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth-users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all-users")
    public List<RegisterRequest> GetAllUsers(){
        log.info("entering the authenticate controller");
        return userService.getAllUsers();

    }



}
