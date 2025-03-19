package com.effectivemobile.taskmanagement.DTO.response;

public class EditTaskResponse {
  private final Long id;
  private final String name;

  public EditTaskResponse(Long id, String name) {
    this.name = name;
    this.id = id;
  }
}
