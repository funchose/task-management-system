package com.effectivemobile.taskmanagement.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
  @NotBlank
  private String token;

  public RefreshTokenRequest(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public RefreshTokenRequest setToken(String token) {
    this.token = token;
    return this;
  }
}
