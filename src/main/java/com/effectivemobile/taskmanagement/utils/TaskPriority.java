package com.effectivemobile.taskmanagement.utils;

public enum TaskPriority {
  HIGH("High"),
  MEDIUM("Medium"),
  LOW("Low");
  private final String priority;

  TaskPriority(String priority) {
    this.priority = priority;
  }

  public String getPriority() {
    return priority;
  }
}
