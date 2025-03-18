package com.effectivemobile.taskmanagement.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpRequest {
  @NotBlank(message = "Username is required")
  @Size(min = 5, max = 25,
  message = "Username length should not be less than 5 and more that 25 symbols")
  private String username;
  @NotBlank(message = "Password is required")
  @Size(min = 5, max = 25,
      message = "Password length should not be less than 5 and more that 25 symbols")
  private String password;

  public String getUsername() {
    return username;
  }

  public SignUpRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public SignUpRequest setPassword(String password) {
    this.password = password;
    return this;
  }
}
