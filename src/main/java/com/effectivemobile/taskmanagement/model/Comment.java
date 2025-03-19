package com.effectivemobile.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "comment")
@SQLRestriction("is_active <> 'false'")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "task_id")
  private Long taskId;
  @Column(name = "comment_author_id")
  private Long commentAuthorId;
  @NotBlank(message = "Comment text is required")
  @Column
  private String text;

  @Column(name = "is_active")
  private boolean isActive;

  public Comment(Long id, Long taskId, Long commentAuthorId, String text) {
    this.id = id;
    this.taskId = taskId;
    this.commentAuthorId = commentAuthorId;
    this.text = text;
    this.isActive = true;
  }

  public Long getTaskId() {
    return taskId;
  }

  public Comment setTaskId(Long taskId) {
    this.taskId = taskId;
    return this;
  }

  public Long getCommentAuthorId() {
    return commentAuthorId;
  }

  public Comment setCommentAuthorId(Long commentAuthorId) {
    this.commentAuthorId = commentAuthorId;
    return this;
  }

  public Comment() {
  }

  public Comment setId(Long id) {
    this.id = id;
    return this;
  }

  public String getText() {
    return text;
  }

  public Comment setText(String text) {
    this.text = text;
    return this;
  }

  public Long getId() {
    return id;
  }

  public boolean isActive() {
    return isActive;
  }

  public Comment setActive(boolean active) {
    isActive = active;
    return this;
  }
}
