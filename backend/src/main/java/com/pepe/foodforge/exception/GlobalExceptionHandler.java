package com.pepe.foodforge.exception;

import com.pepe.foodforge.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<AuthResponse> handleAuthException(AuthException ex) {
    return ResponseEntity.status(ex.getStatus())
        .body(new AuthResponse(ex.getMessage(), null));
  }
}
