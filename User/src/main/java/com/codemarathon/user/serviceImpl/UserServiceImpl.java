package com.codemarathon.user.serviceImpl;

import com.codemarathon.clients.allClient.GetUserByIdDto;
import com.codemarathon.clients.allClient.NotificationClient;
import com.codemarathon.clients.allClient.ProductClient;
import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.notification.dto.NotificationRequest;
import com.codemarathon.notification.dto.NotificationResponse;
import com.codemarathon.user.config.jwtConfig.JwtService;
import com.codemarathon.user.constants.Role;
import com.codemarathon.user.dto.AuthRequest;
import com.codemarathon.user.dto.AuthenticationResponse;
import com.codemarathon.user.dto.RegisterRequest;
import com.codemarathon.user.event.ApplicationUrl;
import com.codemarathon.user.event.RegistrationCompleteEvent;
import com.codemarathon.user.event.VerificationToken;
import com.codemarathon.user.exceptions.*;
import com.codemarathon.user.model.User;
import com.codemarathon.user.password.PasswordResetService;
import com.codemarathon.user.repository.UserRepository;
import com.codemarathon.user.repository.VerificationTokenRepository;
import com.codemarathon.user.service.UserService;
import com.codemarathon.user.token.Token;
import com.codemarathon.user.token.TokenRepository;
import com.codemarathon.user.token.TokenType;
import com.codemarathon.user.utils.ApplicationUtils;
import com.codemarathon.user.utils.ValidateToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUtils applicationUtils;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final ApplicationUrl applicationUrl;
    private final ApplicationEventPublisher publisher;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;

    private  final ValidateToken validateTokenClass;

    private final PasswordResetService passwordResetService;
   // private final WebClient webClient;
//   @Value("${master.sub.Product_Url}")
//    private String get_Product_Url;

   private final ProductClient productClient;

   private final NotificationClient notificationClient;


    public AuthenticationResponse registerUser(RegisterRequest registerRequest,Role role,final HttpServletRequest request) {

         Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
         log.info("existing user in database: {}",existingUser);

         if(existingUser.isPresent()){
             throw  new UsersNotFoundException("user with " + registerRequest.getEmail() + " already exists");
         }

         User registeredUser = new User();

         String subscriptionCode = applicationUtils.generateSubscriptionCode();
         String registrationTime = applicationUtils.registeredTime();
         String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

         modelMapper.map(registerRequest,registeredUser);

         registeredUser.setSubscriptionCode(subscriptionCode);
         registeredUser.setRegisteredTime(registrationTime);
         registeredUser.setPassword(encryptedPassword);
         registeredUser.setRole(role);

         User savedUser = userRepository.save(registeredUser);
         log.info("User successfully saved to database: {} ",savedUser);


        publisher.publishEvent(new RegistrationCompleteEvent(registeredUser, applicationUrl.messageUrl(request)));

        var jwtToken = jwtService.generateToken(registeredUser);
        log.info("generated jwt token for user: {}", jwtToken);

        saveToken(savedUser, jwtToken);
        log.info("token saved to token db....");


        return AuthenticationResponse.builder()
                .message("click here to complete your registration")
                .token(jwtToken)
                .build();
    }





    @Override
    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()
        ));

        var user = userRepository.findByEmail(request.getUsername()).orElseThrow(()-> new UsersNotFoundException("User not found"));

        checkUserStatus(user);

        var jwtToken = jwtService.generateToken(user);
        log.info("generated jwt token for user: {}", jwtToken);

        revokeAllUserToken(user);
        saveToken(user, jwtToken);
        log.info("token saved to token db....");

        NotificationResponse response = notificationClient.sendNotification(
                            new NotificationRequest(
                                    Math.toIntExact(user.getId()),
                                    user.getEmail(),
                                    String.format("Hi %s, welcome to Master-sub...",user.getFirstName())
                            )
        );

        String responseCode = response.getResponseCode();
        log.info("notification response code: {}",responseCode);

       if(!responseCode.equals("000")){
           return AuthenticationResponse.builder()
                   .message("Notification not sent to user")
                   .build();
       }


        return AuthenticationResponse.builder()
                .message("user successfully authenticated")
                .token(jwtToken)
                .build();
    }

    private void checkUserStatus(User user) {
        if (!user.isEnabled()) {
            throw new UserNotEnabledException("User is not enabled or not verified");
        }
    }


    private void saveToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .expired(false)
                .build();

        tokenRepository.save(token);

    }

    public void revokeAllUserToken(User user){
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());

        if(validUserToken.isEmpty()){
            throw new TokenNotFoundException("token not found");
        }

        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
    }


    public List<RegisterRequest> getAllUsers() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UsersNotFoundException("No users found in the database");
        }

        return users.stream()
                .map(this::mapUserToDto)
                .collect(Collectors.toList());

    }

    @Override
    public GetUserByIdDto getUserById(Long id){

        Optional<User> user = userRepository.findById(id);
        log.info("user : {}",user);

        if(user.isEmpty()){

            throw new UsersNotFoundException("user not found");
        }

        return GetUserByIdDto.builder()
                .id(user.get().getId())
                .subscriptionCode(user.get().getSubscriptionCode())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .address(user.get().getAddress())
                .email(user.get().getEmail())
                .mobile(user.get().getMobile())
                .build();

    }

    @Override
    public Optional<User> findUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private RegisterRequest mapUserToDto(User user) {
        return RegisterRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .mobile(user.getMobile())
                .build();
    }


    public User updateById(Long userId, RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findById(userId);
        log.info("Existing user: {}",existingUser);

        if(existingUser.isEmpty()){
            throw new UsersNotFoundException("User with with id " + userId + "was not found");
        }
        User newUser = new User();

        String subscriptionCode = applicationUtils.generateSubscriptionCode();
        String updatedTime = applicationUtils.registeredTime();
        String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setPassword(encryptedPassword);
        newUser.setSubscriptionCode(subscriptionCode);
        newUser.setEmail(registerRequest.getEmail());
        newUser.setAddress(registerRequest.getAddress());
        newUser.setMobile(registerRequest.getMobile());
        newUser.setRegisteredTime(updatedTime);

        User savedUser = userRepository.save(newUser);
        log.info("saved saved successfully to db: {}",savedUser);

        return savedUser;

    }



    public void deleteUser(RegisterRequest registerRequest) {
        Optional<User> userToDelete = userRepository.findByEmail(registerRequest.getEmail());
        log.info("User to be deleted: {}", userToDelete);

        if (userToDelete.isEmpty()) {
            throw new UsersNotFoundException("No User with Email: " + registerRequest.getEmail());
        }
        User userToBeDeleted = userToDelete.get();
        log.info("user to be deleted: {}", userToBeDeleted);
        userRepository.delete(userToBeDeleted);
    }


//    @Override
//    public User findUserBySubscriptionCode(UserRequest userRequest) {
//        User userWithSubCode = userRepository.findBySubscriptionCode(userRequest.getSubscriptionCode());
//        if (userWithSubCode == null){
//            throw new UsersNotFoundException("user with subscription code " + userRequest.getSubscriptionCode()
//            + "does not exist");
//        }
//        return userWithSubCode;
//    }

    @Override
    public void saveUserVerificationToken(String verificationToken,User registeredUser) {
        VerificationToken userToken = new VerificationToken(verificationToken,registeredUser);
        VerificationToken savedToken = verificationTokenRepository.save(userToken);
        log.info("token successfully saved to db : {}",savedToken);
    }



    public String verifyEmail(String token) {

        VerificationToken verifiedToken = verificationTokenRepository.findByToken(token);
        log.info("Verified token: {}", verifiedToken);

        if (verifiedToken == null) {
            return "Invalid Verification token";
        }


        if (verifiedToken.getUser().isEnabled()) {
            return "The account has already been verified, please login";
        }

        return validateTokenClass.validateToken(token);
    }



    @Override
    public ProductResponse getAllProduct() {

        ProductResponse allProduct = productClient.getAllProduct();
        log.info("product response: {}", allProduct);

        if (allProduct == null) {

            throw new NoProductFoundException("No products found.");
        }

        String responseCode = allProduct.getResponseCode();
        log.info("product response code: {}", responseCode);

        String responseMessage = allProduct.getMessage();
        log.info("product response message: {}", responseMessage);

        if ("000".equals(responseCode) && "Process Completed successfully".equals(responseMessage)) {
            return allProduct;
        } else {

            throw new ProductResponseException("Error while fetching products: " + responseMessage);
        }
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        passwordResetService.createPasswordResetTokenForUser(passwordToken,user);
    }
    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        return passwordResetService.validatePasswordResetToken(passwordResetToken);
    }

    @Override
    public User findUserByPasswordToken(String passwordResetToken) {
        return passwordResetService.findUserByPasswordToken(passwordResetToken).get();
    }



    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


}
