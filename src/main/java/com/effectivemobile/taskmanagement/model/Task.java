package com.effectivemobile.taskmanagement.model;

import com.effectivemobile.taskmanagement.utils.Status;
import com.effectivemobile.taskmanagement.utils.TaskPriority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "author_id")
  private Long authorId;
  @Column(name = "performer_id")
  private Long performerId;
  @NotBlank(message = "Task name is required")
  @Column
  private String name;
  @NotBlank(message = "Task description is required")
  @Column
  private String description;
  @NotBlank(message = "Task description is required")
  @Column
  private String comments;
  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  private TaskPriority priority;
  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private Status status;

  public Task(Long id, Long authorId, Long performerId, String name,
              String description, String comments, TaskPriority priority, Status status) {
    this.id = id;
    this.authorId = authorId;
    this.performerId = performerId;
    this.name = name;
    this.description = description;
    this.comments = comments;
    this.priority = priority;
    this.status = status;
  }

  public Task() {
  }

  public Task setId(Long id) {
    this.id = id;
    return this;
  }

  public Task setAuthorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  public Task setPerformerId(Long performerId) {
    this.performerId = performerId;
    return this;
  }

  public Task setName(String name) {
    this.name = name;
    return this;
  }

  public Task setDescription(String description) {
    this.description = description;
    return this;
  }

  public Task setComments(String comments) {
    this.comments = comments;
    return this;
  }

  public Task setPriority(TaskPriority priority) {
    this.priority = priority;
    return this;
  }

  public Task setStatus(Status status) {
    this.status = status;
    return this;
  }

  public Long getId() {
    return id;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public Long getPerformerId() {
    return performerId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getComments() {
    return comments;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public Status getStatus() {
    return status;
  }
}
