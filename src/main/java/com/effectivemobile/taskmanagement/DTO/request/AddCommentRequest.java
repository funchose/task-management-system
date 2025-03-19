package com.effectivemobile.taskmanagement.DTO.request;

import jakarta.validation.constraints.NotNull;

public record AddCommentRequest(@NotNull String text) {
  public AddCommentRequest(String text) {
    this.text = text;
  }

  @Override
  public String text() {
    return text;
  }
}
