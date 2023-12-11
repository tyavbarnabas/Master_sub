package com.codemarathon.user.service;


import com.codemarathon.clients.allClient.GetUserByIdDto;
import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.clients.allClient.UserResponse;
import com.codemarathon.user.constants.Role;
import com.codemarathon.user.dto.*;
import com.codemarathon.user.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthenticationResponse registerUser(RegisterRequest registerRequest,Role role,HttpServletRequest request);

    List<RegisterRequest> getAllUsers();

    User updateById(Long userId, RegisterRequest registerRequest);

    void deleteUser(RegisterRequest request);

   // User findUserBySubscriptionCode(UserRequest userRequest);

    GetUserByIdDto getUserById(Long id);

    Optional<User> findUserEmail(String email);


    void saveUserVerificationToken(String verificationToken,User registeredUser);

    AuthenticationResponse authenticate(AuthRequest request);

    public String verifyEmail(String token);

    //String validateToken(String verifiedToken);

    ProductResponse getAllProduct();

    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);
}
