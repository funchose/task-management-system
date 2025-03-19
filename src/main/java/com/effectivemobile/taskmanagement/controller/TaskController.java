package com.effectivemobile.taskmanagement.controller;

import com.effectivemobile.taskmanagement.DTO.request.AddTaskRequest;
import com.effectivemobile.taskmanagement.DTO.request.EditTaskRequest;
import com.effectivemobile.taskmanagement.DTO.response.CreateTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.DeleteTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.EditTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.TaskResponse;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.service.TaskService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/tasks")
  public ResponseEntity<List<TaskResponse>> getAllTasks(@AuthenticationPrincipal Account account) {
    var taskDTOs = taskService.getTasks(account);
    return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
  }

  @GetMapping("/tasks/author/{authorId}")
  public ResponseEntity<List<TaskResponse>> getAuthorTasks(@AuthenticationPrincipal Account account,
                                                           @PathVariable Long authorId) {
    var taskDTOs = taskService.getAuthorTasks(account, authorId);
    return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
  }

  @GetMapping("/tasks/performer/{performerId}")
  public ResponseEntity<List<TaskResponse>> getPerformerTasks(
      @AuthenticationPrincipal Account account,
      @PathVariable Long performerId) {
    var taskDTOs = taskService.getPerformerTasks(account, performerId);
    return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
  }


  @PostMapping("/tasks")
  public ResponseEntity<CreateTaskResponse> createTask(@RequestBody AddTaskRequest request,
                                                       @AuthenticationPrincipal Account account) {
    var createTaskDTO = taskService.createTask(request, account.getId());
    return new ResponseEntity<>(createTaskDTO, HttpStatus.CREATED);
  }

  @PutMapping("/tasks/{taskId}")
  public ResponseEntity<EditTaskResponse> editTask(@RequestBody EditTaskRequest request,
                                                   @AuthenticationPrincipal Account account,
                                                   @PathVariable Long taskId) {
    var editedTask = taskService.editTask(account, taskId, request);
    return new ResponseEntity<>(editedTask, HttpStatus.OK);
  }

  @DeleteMapping("/tasks/{taskId}")
  public ResponseEntity<DeleteTaskResponse> deleteTask(@PathVariable Long taskId) {
    var deletedTask = taskService.deleteTask(taskId);
    return new ResponseEntity<>(deletedTask, HttpStatus.OK);
  }
}
