package com.mit.exception;

import com.mit.dto.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResportException.class)
    public ResponseEntity<RestResponse<String>> handleListEmptyException(ResportException ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.NOT_FOUND);
    }
}
