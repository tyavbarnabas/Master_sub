package com.codemarathon.user.exceptions;

import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.clients.allClient.UserResponse;
import com.codemarathon.user.constants.GeneralResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProductResponse> handlePayloadException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ProductResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getAllErrors().get(0).getDefaultMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductResponseException.class)
    public ResponseEntity<ProductResponse> handleCustomerNotFoundException(ProductResponseException ex) {
        return new ResponseEntity<>(new ProductResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoProductFoundException.class)
    public ResponseEntity<ProductResponse> handleNoCustomerFoundException(NoProductFoundException ex) {
        return new ResponseEntity<>(new ProductResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<UserResponse> handleUsersNotFoundException(UsersNotFoundException ex) {
        return new ResponseEntity<>(new UserResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<UserResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
        return new ResponseEntity<>(new UserResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<UserResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        return new ResponseEntity<>(new UserResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PasswordResetTokenNotFoundException.class)
    public ResponseEntity<UserResponse> handlePasswordResetTokenNotFoundException(PasswordResetTokenNotFoundException ex) {
        return new ResponseEntity<>(new UserResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<UserResponse> handleTokenExpiredException(TokenExpiredException ex) {
        return new ResponseEntity<>(new UserResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotEnabledException.class)
    public ResponseEntity<UserResponse> handleUserNotEnabledException(UserNotEnabledException ex) {
        return new ResponseEntity<>(new UserResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }
}
