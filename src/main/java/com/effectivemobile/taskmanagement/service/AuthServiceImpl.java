package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.SignInRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignUpRequest;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.security.JwtService;
import com.effectivemobile.taskmanagement.utils.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final AccountServiceImpl accountService;

  public AuthServiceImpl(JwtService jwtService, PasswordEncoder passwordEncoder,
                         AuthenticationManager authenticationManager,
                         AccountServiceImpl accountService) {
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.accountService = accountService;
  }

  @Transactional
  public String signIn(SignInRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    ));
    var profile = accountService.loadUserByUsername(request.getUsername());
    return jwtService.generateToken(profile);
  }

  @Transactional
  public void signUp(SignUpRequest request) {
    var account = new Account();
    account.setUsername(request.getUsername());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    account.setRole(Role.ROLE_USER);
    accountService.addProfile(account);
  }
}
