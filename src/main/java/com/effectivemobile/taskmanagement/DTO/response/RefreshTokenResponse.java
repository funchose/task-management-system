package com.effectivemobile.taskmanagement.DTO.response;

public class RefreshTokenResponse {
  String token;

  public RefreshTokenResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public RefreshTokenResponse setToken(String token) {
    this.token = token;
    return this;
  }
}
