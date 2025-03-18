package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.exceptions.AccountNotFoundException;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements UserDetailsService, AccountService{

  private final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;

  }
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getByUsername(username);
  }

  public UserDetails getByUsername(String username) {
    return accountRepository.findByUsername(username)
        .orElseThrow(() -> new AccountNotFoundException(username));
  }
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  @Override
  public Long addProfile(Account account) {
    return accountRepository.save(account).getId();
  }
}
