package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.RefreshTokenRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignInRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignUpRequest;
import com.effectivemobile.taskmanagement.DTO.response.RefreshTokenResponse;
import com.effectivemobile.taskmanagement.DTO.response.SignInResponse;


public interface AuthService {
  SignInResponse signIn(SignInRequest request);
  void signUp(SignUpRequest request);
  RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
