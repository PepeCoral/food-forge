package com.pepe.foodforge.service;

import com.pepe.foodforge.dto.LoginRequest;
import com.pepe.foodforge.entity.User;
import com.pepe.foodforge.exception.AuthException;
import com.pepe.foodforge.repository.UserRepository;
import com.pepe.foodforge.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public String signup(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new AuthException("Username already taken", HttpStatus.BAD_REQUEST);
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    return jwtUtil.generateToken(user.getUsername());
  }

  public String login(LoginRequest request) {
    Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
    if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
      throw new AuthException("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    return jwtUtil.generateToken(userOpt.get().getUsername());
  }
}
