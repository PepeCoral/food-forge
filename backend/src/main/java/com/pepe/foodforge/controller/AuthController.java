package com.pepe.foodforge.controller;

import com.pepe.foodforge.dto.AuthResponse;
import com.pepe.foodforge.dto.LoginRequest;
import com.pepe.foodforge.entity.User;
import com.pepe.foodforge.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@RequestBody User user) {
    String token = authService.signup(user);
    return ResponseEntity.ok(new AuthResponse("User registered and logged in", token));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    String token = authService.login(request);
    return ResponseEntity.ok(new AuthResponse("Login successful", token));
  }
}
