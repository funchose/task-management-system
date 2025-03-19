package com.effectivemobile.taskmanagement.DTO.request;

import com.effectivemobile.taskmanagement.utils.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddTaskRequest {
  private Long performerId;
  @NotBlank(message = "Task name is required")
  private String name;
  @NotBlank(message = "Task description is required")
  private String description;
  @NotNull(message = "Task status is required")
  private TaskPriority priority;

  public AddTaskRequest(Long performerId, String name, String description, TaskPriority priority) {
    this.performerId = performerId;
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  public Long getPerformerId() {
    return performerId;
  }

  public AddTaskRequest setPerformerId(Long performerId) {
    this.performerId = performerId;
    return this;
  }

  public String getName() {
    return name;
  }

  public AddTaskRequest setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AddTaskRequest setDescription(String description) {
    this.description = description;
    return this;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public AddTaskRequest setPriority(TaskPriority priority) {
    this.priority = priority;
    return this;
  }
}
