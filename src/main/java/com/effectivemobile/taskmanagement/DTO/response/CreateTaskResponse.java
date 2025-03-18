package com.effectivemobile.taskmanagement.DTO.response;

public class CreateTaskResponse {
  private final Long id;
  private final String name;

  public CreateTaskResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
