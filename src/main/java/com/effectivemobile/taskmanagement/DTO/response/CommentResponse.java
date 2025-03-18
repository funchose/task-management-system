package com.effectivemobile.taskmanagement.DTO.response;

import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.model.Task;

public class CommentResponse {
  private Long id;
  private Task task;

  private Account commentAuthor;

  private String text;
}
