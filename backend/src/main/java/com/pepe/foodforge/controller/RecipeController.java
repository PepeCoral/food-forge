package com.pepe.foodforge.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

  @PostMapping("/hey")
  public ResponseEntity<String> hello() {

    return ResponseEntity.ok()
        .body("{\"message\": \" This is auth exclusive content\"}");
  }

}
