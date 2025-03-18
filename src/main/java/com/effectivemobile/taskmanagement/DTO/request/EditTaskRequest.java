package com.effectivemobile.taskmanagement.DTO.request;

import com.effectivemobile.taskmanagement.utils.Status;
import com.effectivemobile.taskmanagement.utils.TaskPriority;

public class EditTaskRequest {
  private String name;
  private String description;
  private TaskPriority priority;
  private Status status;

  public EditTaskRequest(String name, String description, TaskPriority priority, Status status) {
    this.name = name;
    this.description = description;
    this.priority = priority;
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public EditTaskRequest setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public EditTaskRequest setDescription(String description) {
    this.description = description;
    return this;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public EditTaskRequest setPriority(TaskPriority priority) {
    this.priority = priority;
    return this;
  }

  public Status getStatus() {
    return status;
  }

  public EditTaskRequest setStatus(Status status) {
    this.status = status;
    return this;
  }
}
