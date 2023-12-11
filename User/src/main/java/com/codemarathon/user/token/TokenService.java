package com.codemarathon.user.token;

import com.codemarathon.user.config.jwtConfig.JwtService;
import com.codemarathon.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public String getValidTokenForUser(Long userId) {

        List<Token> validTokens = tokenRepository.findAllValidTokenByUser(userId);
        log.info("valid token for User : {}",validTokens);

        // Example: Choose the first non-expired and non-revoked token
        Optional<Token> firstValidToken = validTokens.stream().findFirst();
        log.info("first non expired token: {}", firstValidToken);

        // If a valid token is found, return it. Otherwise, generate a new one.
        return firstValidToken.map(Token::getToken)
                .orElseGet(() -> generateAndSaveNewToken(userId));
    }

    private String generateAndSaveNewToken(Long userId) {
        // Generate a new token
        String newToken = jwtService.generateTokenForUser(userId);
        log.info("newly generated token: {}", newToken);

        // Save the new token to the database
        Token tokenEntity = Token.builder()
                .token(newToken)
                .tokenType(TokenType.BEARER)  // Set the appropriate token type
                .expired(false)
                .revoked(false)
                .user(User.builder().id(userId).build())  // Assuming you have a User entity
                .build();
        tokenRepository.save(tokenEntity);

        return newToken;
    }
}
