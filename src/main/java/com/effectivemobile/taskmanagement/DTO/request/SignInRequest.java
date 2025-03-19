package com.effectivemobile.taskmanagement.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class SignInRequest {
  @NotBlank(message = "Email cannot be blank")
  private String email;
  @NotBlank(message = "Password cannot be blank")
  private String password;

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public SignInRequest setEmail(String email) {
    this.email = email;
    return this;
  }

  public SignInRequest setPassword(String password) {
    this.password = password;
    return this;
  }
}
