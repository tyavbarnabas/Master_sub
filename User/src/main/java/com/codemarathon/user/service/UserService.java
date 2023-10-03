package com.codemarathon.user.service;


import com.codemarathon.user.constants.Role;
import com.codemarathon.user.dto.AuthRequest;
import com.codemarathon.user.dto.AuthenticationResponse;
import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.dto.UserResponse;
import com.codemarathon.user.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    AuthenticationResponse registerUser(RegisterRequest registerRequest,Role role,HttpServletRequest request);

    List<RegisterRequest> getAllUsers();

    User updateById(Long userId, RegisterRequest registerRequest);

    void deleteUser(RegisterRequest request);

   // User findUserBySubscriptionCode(UserRequest userRequest);


    void saveUserVerificationToken(String verificationToken,User registeredUser);

    AuthenticationResponse authenticate(AuthRequest request);

    public String verifyEmail(String token);

    String validateToken(String verifiedToken);
}
