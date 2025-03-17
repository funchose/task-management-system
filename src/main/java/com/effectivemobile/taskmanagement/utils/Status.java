package com.effectivemobile.taskmanagement.utils;

public enum Status {
  TODO("To do"),
  IN_PROGRESS("In progress"),
  DONE("Done");
  private final String status;

  Status(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
