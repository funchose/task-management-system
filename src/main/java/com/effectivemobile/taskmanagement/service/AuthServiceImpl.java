package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.RefreshTokenRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignInRequest;
import com.effectivemobile.taskmanagement.DTO.request.SignUpRequest;
import com.effectivemobile.taskmanagement.DTO.response.RefreshTokenResponse;
import com.effectivemobile.taskmanagement.DTO.response.SignInResponse;
import com.effectivemobile.taskmanagement.exceptions.AccountAlreadyExistsException;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.repository.AccountRepository;
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
  private final AccountRepository accountRepository;


  public AuthServiceImpl(JwtService jwtService, PasswordEncoder passwordEncoder,
                         AuthenticationManager authenticationManager,
                         AccountServiceImpl accountService, AccountRepository accountRepository) {
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.accountService = accountService;
    this.accountRepository = accountRepository;
  }

  @Transactional
  @Override
  public SignInResponse signIn(SignInRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getEmail(), request.getPassword()));
    var account = accountService.loadUserByEmail(request.getEmail());
    return new SignInResponse(jwtService.generateToken(account));
  }

  @Transactional
  @Override
  public void signUp(SignUpRequest request) {
    if (accountRepository.existsByUsername(request.getUsername())) {
      throw new AccountAlreadyExistsException(request.getUsername());
    }
    var account = new Account();
    account.setEmail(request.getEmail());
    account.setUsername(request.getUsername());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    account.setRole(Role.ROLE_USER);
    accountService.addProfile(account);
  }

  @Transactional
  @Override
  public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
    var username = jwtService.extractEmail(request.getToken());
    var account = accountService.loadUserByEmail(username);
    return new RefreshTokenResponse(jwtService.generateToken(account));
  }
}
