package com.escamilla.flow_tree.exception;

import com.escamilla.flow_tree.payload.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistException(AlreadyExistException exception) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(exception.getMessage())
                        .object(null)
                        .build(),
                HttpStatus.NOT_ACCEPTABLE
        );
    }
}
