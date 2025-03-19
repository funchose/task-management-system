package com.effectivemobile.taskmanagement.DTO.response;

import com.effectivemobile.taskmanagement.utils.Role;

public class AccountResponse {
  private Long id;
  private String email;

  private String username;

  private String password;

  private Role role;

  public AccountResponse(Long id, String email, String username, String password, Role role) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public AccountResponse setId(Long id) {
    this.id = id;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public AccountResponse setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public AccountResponse setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public AccountResponse setPassword(String password) {
    this.password = password;
    return this;
  }

  public Role getRole() {
    return role;
  }

  public AccountResponse setRole(Role role) {
    this.role = role;
    return this;
  }
}
