package com.effectivemobile.taskmanagement.exceptions;

public class AccessDeniedException extends RuntimeException{
  public AccessDeniedException(String message) {
    super(message);
  }
}
