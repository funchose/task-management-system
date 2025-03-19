package com.effectivemobile.taskmanagement.controller;

import com.effectivemobile.taskmanagement.DTO.request.AddCommentRequest;
import com.effectivemobile.taskmanagement.DTO.request.EditCommentRequest;
import com.effectivemobile.taskmanagement.DTO.response.AddCommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.CommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.DeleteCommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.EditCommentResponse;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.service.CommentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }


  @GetMapping("/tasks/{taskId}/comments")
  public ResponseEntity<List<CommentResponse>> getTaskComments(
      @AuthenticationPrincipal Account account, @PathVariable Long taskId) {
    var comments = commentService.getTaskComments(account, taskId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @PostMapping("/tasks/{taskId}/comments")
  public ResponseEntity<AddCommentResponse> addTaskComment(
      @AuthenticationPrincipal Account account,
      @PathVariable Long taskId,
      @RequestBody AddCommentRequest request) {
    var addCommentResponse = commentService.addTaskComment(account, taskId, request);
    return new ResponseEntity<>(addCommentResponse, HttpStatus.CREATED);
  }

  @PutMapping("/tasks/comments/{commentId}")
  public ResponseEntity<EditCommentResponse> addTaskComment(
      @AuthenticationPrincipal Account account,
      @RequestBody EditCommentRequest request,
      @PathVariable Long commentId) {
    var editCommentResponse = commentService.editTaskComment(account, request, commentId);
    return new ResponseEntity<>(editCommentResponse, HttpStatus.OK);
  }

  @DeleteMapping("/tasks/comments/{commentId}")
  public ResponseEntity<DeleteCommentResponse> deleteComment(@PathVariable Long commentId) {
    var deletedComment = commentService.deleteComment(commentId);
    return new ResponseEntity<>(deletedComment, HttpStatus.OK);
  }
}
