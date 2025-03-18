package com.effectivemobile.taskmanagement.DTO.response;

import com.effectivemobile.taskmanagement.model.Comment;
import com.effectivemobile.taskmanagement.model.Task;
import java.util.List;

public class TaskResponse {

  private Long id;
  private Long authorId;
  private Long performerId;
  private String name;
  private String description;
  private List<Comment> comments;
  private String priority;
  private String status;

  public TaskResponse(Task task) {
    this.id = task.getId();
    this.authorId = task.getAuthorAccount().getId();
    this.performerId = task.getPerformerAccount().getId();
    this.name = task.getName();
    this.description = task.getDescription();
    this.comments = task.getComments();
    this.priority = task.getPriority().getPriority();
    this.status = task.getStatus().getStatus();
  }

  public TaskResponse() {
  }

  public Long getId() {
    return id;
  }

  public TaskResponse setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public TaskResponse setAuthorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  public Long getPerformerId() {
    return performerId;
  }

  public TaskResponse setPerformerId(Long performerId) {
    this.performerId = performerId;
    return this;
  }

  public String getName() {
    return name;
  }

  public TaskResponse setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public TaskResponse setDescription(String description) {
    this.description = description;
    return this;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public TaskResponse setComments(List<Comment> comments) {
    this.comments = comments;
    return this;
  }

  public String getPriority() {
    return priority;
  }

  public TaskResponse setPriority(String priority) {
    this.priority = priority;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public TaskResponse setStatus(String status) {
    this.status = status;
    return this;
  }
}
