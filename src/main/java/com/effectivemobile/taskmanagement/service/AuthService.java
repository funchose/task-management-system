package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.SignInRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignUpRequest;


public interface AuthService {
  String signIn(SignInRequest request);

  void signUp(SignUpRequest request);
}
