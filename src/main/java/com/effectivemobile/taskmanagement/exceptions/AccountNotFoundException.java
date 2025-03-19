package com.effectivemobile.taskmanagement.exceptions;

public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(Long id) {
    super(String.format("Account with id %d not found", id));
  }

  public AccountNotFoundException(String credentials) {
    super(String.format("Account with credentials %s not found", credentials));
  }
}
