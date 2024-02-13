package com.example.demo.common.aop;

import com.example.demo.common.ExceptionResponse;
import com.example.demo.common.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(Exception e) {
        return commonHandler(e, e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidTokenException.class, BadCredentialsException.class})
    public ResponseEntity<ExceptionResponse> BadRequestHandler(Exception e) {
        return commonHandler(e, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponse> commonExceptionHandler(Exception e) {
        return commonHandler(e, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> commonHandler(Exception e, String message, HttpStatus status) {
        log.error(e.getMessage(), e);
        ExceptionResponse error = new ExceptionResponse(message);
        return new ResponseEntity<>(error, status);
    }
}
