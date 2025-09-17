package com.pepe.foodforge.config;

import com.pepe.foodforge.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  public JwtAuthFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String token = null;

    token = getJwtFromCookies(request, token);

    checkToken(token);
    filterChain.doFilter(request, response);

  }

  private void checkToken(String token) {
    if (token != null) {
      if (jwtUtil.validateToken(token)) {
        String username = jwtUtil.extractUsername(token);
        var auth = new UsernamePasswordAuthenticationToken(
            username,
            null,
            List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
  }

  private String getJwtFromCookies(HttpServletRequest request, String token) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("jwt".equals(cookie.getName())) {
          token = cookie.getValue();
          break;
        }
      }
    }
    return token;
  }
}
