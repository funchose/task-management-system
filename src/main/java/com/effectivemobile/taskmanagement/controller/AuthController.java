package com.effectivemobile.taskmanagement.controller;

import com.effectivemobile.taskmanagement.DTO.request.RefreshTokenRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignInRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignUpRequest;
import com.effectivemobile.taskmanagement.DTO.response.RefreshTokenResponse;
import com.effectivemobile.taskmanagement.DTO.response.SignInResponse;
import com.effectivemobile.taskmanagement.service.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthServiceImpl authService;

  public AuthController(AuthServiceImpl authService) {
    this.authService = authService;
  }
    @PostMapping("/auth/sign-up")
  public void signUp(@RequestBody @Valid SignUpRequest request) {
    authService.signUp(request);
  }

  @PostMapping("/auth/sign-in")
  public SignInResponse signIn(@RequestBody @Valid SignInRequest request) {
    return authService.signIn(request);
  }

  @PostMapping("/auth/refresh")
  public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
    return authService.refreshToken(request);
  }
}
