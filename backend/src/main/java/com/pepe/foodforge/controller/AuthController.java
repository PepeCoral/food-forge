package com.pepe.foodforge.controller;

import com.pepe.foodforge.dto.AuthResponse;
import com.pepe.foodforge.dto.LoginRequest;
import com.pepe.foodforge.dto.SignUpRequest;
import com.pepe.foodforge.entity.User;
import com.pepe.foodforge.service.AuthService;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest.Headers;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @Value("${jwt.expiration-ms}")
  private long tokenExpirationMs;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
    String token = authService.signup(signUpRequest);

    ResponseCookie cookie = ResponseCookie.from("jwt", token)
        .httpOnly(true)
        .path("/")
        .secure(false)
        .sameSite("Lax")
        .maxAge(Duration.ofSeconds(tokenExpirationMs))
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("{\"message\": \"User registered and logged in\"}");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    String token = authService.login(request);

    ResponseCookie cookie = ResponseCookie.from("jwt", token)
        .httpOnly(true)
        .path("/")
        .secure(false)
        .sameSite("Lax")
        .maxAge(Duration.ofSeconds(tokenExpirationMs))
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("{\"message\": \"Login successful\"}");

  }
}
