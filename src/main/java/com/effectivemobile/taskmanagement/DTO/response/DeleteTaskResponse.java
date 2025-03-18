package com.effectivemobile.taskmanagement.DTO.response;

public class DeleteTaskResponse {
  private final Long id;

  public DeleteTaskResponse(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
