package com.effectivemobile.taskmanagement.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class SignInRequest {
  @NotBlank(message = "Username cannot be blank")
  private String username;
  @NotBlank(message = "Password cannot be blank")
  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public SignInRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public SignInRequest setPassword(String password) {
    this.password = password;
    return this;
  }
}
