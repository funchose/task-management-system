package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.AddTaskRequest;
import com.effectivemobile.taskmanagement.DTO.request.EditTaskRequest;
import com.effectivemobile.taskmanagement.DTO.response.CreateTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.DeleteTaskResponse;
import com.effectivemobile.taskmanagement.DTO.response.TaskResponse;
import com.effectivemobile.taskmanagement.model.Account;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
  List<TaskResponse> getTasks(Account account);

  List<TaskResponse> getAuthorTasks(Account account, Long authorId);

  List<TaskResponse> getPerformerTasks(Account account, Long performerId);

  CreateTaskResponse createTask(AddTaskRequest request, Long id);

  TaskResponse editTask(Account account, Long taskId, EditTaskRequest request);

  DeleteTaskResponse deleteTask(Long taskId);
}
