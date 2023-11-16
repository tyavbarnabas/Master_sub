package com.codemarathon.user.controllers;

import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.dto.UserResponse;
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

    @GetMapping("/all-product")
    public ProductResponse getAllProduct(){
        log.info("entering the get all product controller...");
        return userService.getAllProduct();

    }

    @GetMapping("/user/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id){
        log.info("entering the get user controller...");
        return userService.getUserById(id);
    }



}
