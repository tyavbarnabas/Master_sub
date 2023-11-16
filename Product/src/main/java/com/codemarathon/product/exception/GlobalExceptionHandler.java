package com.codemarathon.product.exception;

import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.product.constants.GeneralResponseEnum;
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
    @ExceptionHandler(PackageNotFoundException.class)
    public ResponseEntity<ProductResponse> handlePackageNotFoundException(PackageNotFoundException ex) {
        return new ResponseEntity<>(new ProductResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ProductResponse> handleProductAlreadyExistException(ProductAlreadyExistException ex) {
        return new ResponseEntity<>(new ProductResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(new ProductResponse(GeneralResponseEnum.FAILED.getCode(),
                ex.getMessage(),
                GeneralResponseEnum.FAILED.getMessage()), HttpStatus.NOT_FOUND);
    }
}
