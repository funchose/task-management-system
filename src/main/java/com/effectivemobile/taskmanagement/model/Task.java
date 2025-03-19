package com.effectivemobile.taskmanagement.model;

import com.effectivemobile.taskmanagement.utils.Status;
import com.effectivemobile.taskmanagement.utils.TaskPriority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "task")
@SQLRestriction("is_active <> 'false'")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @JoinColumn(name = "author_id", referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "Fk_task_author"))
  @ManyToOne(fetch = FetchType.LAZY)
  private Account authorAccount;
  @JoinColumn(name = "performer_id", referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "Fk_task_performer"))
  @ManyToOne(fetch = FetchType.LAZY)
  private Account performerAccount;
  @NotBlank(message = "Task name is required")
  @Column
  private String name;
  @NotBlank(message = "Task description is required")
  @Column
  private String description;
  @OneToMany(
      mappedBy = "taskId",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  @Column
  private List<Comment> comments = new ArrayList<>();
  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  @NotNull
  private TaskPriority priority;
  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private Status status;
  @Column(name = "is_active")
  private boolean isActive;

  public boolean isActive() {
    return isActive;
  }

  public Task setActive(boolean active) {
    isActive = active;
    return this;
  }

  public Task(Account authorAccount, Account performerAccount,
              String name, String description, List<Comment> comments,
              TaskPriority priority, Status status) {
    this.authorAccount = authorAccount;
    this.performerAccount = performerAccount;
    this.name = name;
    this.description = description;
    this.comments.addAll(comments);
    this.priority = priority;
    this.status = status;
    this.isActive = true;
  }

  public Task() {
  }

  public Task setId(Long id) {
    this.id = id;
    return this;
  }

  public Task setAuthorAccount(Account authorAccount) {
    this.authorAccount = authorAccount;
    return this;
  }

  public Task setPerformerAccount(Account performerAccount) {
    this.performerAccount = performerAccount;
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

  public Task setComments(List<Comment> comments) {
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

  public Account getAuthorAccount() {
    return authorAccount;
  }

  public Account getPerformerAccount() {
    return performerAccount;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public Status getStatus() {
    return status;
  }
}
