package com.zerobase.oriticket.global.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<Void> handleCustomException(AbstractException e) {

        return ResponseEntity.status(e.getErrorCode()).build();
    }

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Void> handleRuntimeException(TypeMismatchException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Void> handleRuntimeException(RuntimeException e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
