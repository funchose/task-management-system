package com.effectivemobile.taskmanagement.DTO.response;

import com.effectivemobile.taskmanagement.model.Comment;

public class CommentResponse {
  private final Long id;
  private final Long taskId;
  private final Long commentAuthorId;
  private final String text;

  public CommentResponse(Comment comment) {
    this.id = comment.getId();
    this.taskId = comment.getTaskId();
    this.commentAuthorId = comment.getCommentAuthorId();
    this.text = comment.getText();
  }

  public Long getId() {
    return id;
  }

  public Long getTaskId() {
    return taskId;
  }

  public Long getCommentAuthorId() {
    return commentAuthorId;
  }

  public String getText() {
    return text;
  }
}
