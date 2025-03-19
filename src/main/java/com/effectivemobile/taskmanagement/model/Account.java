package com.effectivemobile.taskmanagement.model;

import com.effectivemobile.taskmanagement.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "account")
public class Account implements UserDetails {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Email is required")
  @Column(unique = true)
  @Email
  private String email;

  @NotBlank(message = "Username is required")
  @Column(unique = true)
  private String username;
  @NotBlank(message = "Password is required")
  @Column
  private String password;
  @Enumerated(EnumType.STRING)
  @Column
  private Role role;
  public Account(Long id, String email, String username, String password, Role role) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Account() {
  }

  public String getEmail() {
    return email;
  }

  public Account setEmail(String email) {
    this.email = email;
    return this;
  }

  public Account setId(Long id) {
    this.id = id;
    return this;
  }

  public Account setUsername(String username) {
    this.username = username;
    return this;
  }

  public Account setPassword(String password) {
    this.password = password;
    return this;
  }

  public Account setRole(Role role) {
    this.role = role;
    return this;
  }

  public Long getId() {
    return id;
  }

  public Role getRole() {
    return role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getRole()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }
}

