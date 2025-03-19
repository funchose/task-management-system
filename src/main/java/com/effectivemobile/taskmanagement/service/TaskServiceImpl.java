package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.AddTaskRequest;
import com.effectivemobile.taskmanagement.DTO.request.EditTaskRequest;
import com.effectivemobile.taskmanagement.DTO.response.AddTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.DeleteTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.EditTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.TaskResponse;
import com.effectivemobile.taskmanagement.exceptions.AccessDeniedException;
import com.effectivemobile.taskmanagement.exceptions.AccountNotFoundException;
import com.effectivemobile.taskmanagement.exceptions.TaskNotFoundException;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.model.Task;
import com.effectivemobile.taskmanagement.repository.AccountRepository;
import com.effectivemobile.taskmanagement.repository.TaskRepository;
import com.effectivemobile.taskmanagement.utils.Role;
import com.effectivemobile.taskmanagement.utils.Status;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
  private final TaskRepository taskRepository;
  private final AccountRepository accountRepository;

  public TaskServiceImpl(TaskRepository taskRepository, AccountRepository accountRepository) {
    this.taskRepository = taskRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  @Transactional
  public List<TaskResponse> getTasks(Account account) {
    if (account.getRole().equals(Role.ROLE_ADMIN)) {
      return taskRepository.findAll().stream().map(TaskResponse::new).toList();
    } else {
      return taskRepository.findByPerformerAccountId(account.getId())
          .orElseThrow(() -> new AccountNotFoundException(account.getId()))
          .stream().map(TaskResponse::new).toList();
    }
  }

  @Override
  @Transactional
  public List<TaskResponse> getAuthorTasks(Account account, Long authorId) {
    return taskRepository.findByAuthorAccountId(authorId)
        .orElseThrow(() -> new AccountNotFoundException(authorId))
        .stream().map(TaskResponse::new).toList();
  }

  @Override
  @Transactional
  public List<TaskResponse> getPerformerTasks(Account account, Long performerId) {
    return taskRepository.findByPerformerAccountId(performerId)
        .orElseThrow(() -> new AccountNotFoundException(performerId))
        .stream().map(TaskResponse::new).toList();
  }

  @Override
  @Transactional
  public AddTaskResponse createTask(AddTaskRequest request, Long authorId) {
    var authorAndPerformer = accountRepository.findById(authorId).get();
    var taskToCreate = new Task()
        .setId(null)
        .setName(request.getName())
        .setAuthorAccount(authorAndPerformer)
        .setDescription(request.getDescription())
        .setPriority(request.getPriority())
        .setActive(true)
        .setStatus(Status.TODO);
    if (request.getPerformerId() != null) {
      var performerId = request.getPerformerId();
      var performer = accountRepository.findById(performerId)
          .orElseThrow(() -> new AccountNotFoundException(performerId));
        taskToCreate.setPerformerAccount(performer);
    } else {
        taskToCreate.setPerformerAccount(authorAndPerformer);
    }
      var savedTask = taskRepository.save(taskToCreate);
    return new AddTaskResponse(savedTask.getId(), savedTask.getName());
  }

  @Override
  @Transactional
  public EditTaskResponse editTask(Account account, Long taskId, EditTaskRequest request) {
    var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    boolean isPerformer = account.getRole().equals(Role.ROLE_USER)
        && task.getPerformerAccount().getId().equals(account.getId());
    boolean isAdmin = account.getRole().equals(Role.ROLE_ADMIN);
    if (isPerformer) {
      return editTaskByUser(task, request);
    } else if (isAdmin) {
      return editTaskByAdmin(task, request);
    } else {
      throw new AccessDeniedException("Unauthorized task editing");
    }
  }

  @Override
  @Transactional
  public DeleteTaskResponse deleteTask(Long taskId) {
    var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    task.setActive(false);
    taskRepository.save(task);
    return new DeleteTaskResponse(taskId);
  }

  private EditTaskResponse editTaskByUser(Task task, EditTaskRequest request) {
    if (request.getStatus() != null) {
      task.setStatus(request.getStatus());
      return new EditTaskResponse(task.getId(), task.getName());
    } else {
      throw new RuntimeException("User can edit status task only or add comment");
    }
  }

  private EditTaskResponse editTaskByAdmin(Task task, EditTaskRequest request) {
    if (request.getStatus() != null) {
      task.setStatus(request.getStatus());
    }
    if (request.getDescription() != null) {
      task.setDescription(request.getDescription());
    }
    if (request.getName() != null) {
      task.setName(request.getName());
    }
    if (request.getPriority() != null) {
      task.setPriority(request.getPriority());
    }
    if (request.getPerformerId() != null) {
      task.setPerformerAccount(accountRepository.findById(request.getPerformerId())
          .orElseThrow(() -> new AccountNotFoundException(request.getPerformerId())));
    }
    return new EditTaskResponse(task.getId(), task.getName());
  }
}