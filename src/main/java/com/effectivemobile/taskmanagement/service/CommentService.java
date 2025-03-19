package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.AddCommentRequest;
import com.effectivemobile.taskmanagement.DTO.request.EditCommentRequest;
import com.effectivemobile.taskmanagement.DTO.response.AddCommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.CommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.DeleteCommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.EditCommentResponse;
import com.effectivemobile.taskmanagement.model.Account;
import java.util.List;

public interface CommentService {

  List<CommentResponse> getTaskComments(Account account, Long taskId);

  AddCommentResponse addTaskComment(Account account, Long taskId, AddCommentRequest request);

  EditCommentResponse editTaskComment(Account account, EditCommentRequest request, Long commentId);

  DeleteCommentResponse deleteComment(Long commentId);
}

