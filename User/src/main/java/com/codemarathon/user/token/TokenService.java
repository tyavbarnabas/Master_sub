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

        Optional<Token> firstValidToken = validTokens.stream().findFirst();
        log.info("first non expired token: {}", firstValidToken);


        return firstValidToken.map(Token::getToken)
                .orElseGet(() -> generateAndSaveNewToken(userId));
    }

    private String generateAndSaveNewToken(Long userId) {

        String newToken = jwtService.generateTokenForUser(userId);
        log.info("newly generated token: {}", newToken);


        Token tokenEntity = Token.builder()
                .token(newToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(User.builder().id(userId).build())
                .build();
        tokenRepository.save(tokenEntity);

        return newToken;
    }
}
