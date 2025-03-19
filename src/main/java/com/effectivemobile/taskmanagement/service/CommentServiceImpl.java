package com.effectivemobile.taskmanagement.service;

import com.effectivemobile.taskmanagement.DTO.request.AddCommentRequest;
import com.effectivemobile.taskmanagement.DTO.request.EditCommentRequest;
import com.effectivemobile.taskmanagement.DTO.response.AddCommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.CommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.DeleteCommentResponse;
import com.effectivemobile.taskmanagement.DTO.response.EditCommentResponse;
import com.effectivemobile.taskmanagement.exceptions.AccessDeniedException;
import com.effectivemobile.taskmanagement.exceptions.CommentNotFoundException;
import com.effectivemobile.taskmanagement.exceptions.TaskNotFoundException;
import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.model.Comment;
import com.effectivemobile.taskmanagement.repository.CommentRepository;
import com.effectivemobile.taskmanagement.repository.TaskRepository;
import com.effectivemobile.taskmanagement.utils.Role;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final TaskRepository taskRepository;

  public CommentServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository) {
    this.commentRepository = commentRepository;
    this.taskRepository = taskRepository;
  }

  @Override
  public List<CommentResponse> getTaskComments(Account account, Long taskId) {
    var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    boolean isPerformer = account.getRole().equals(Role.ROLE_USER)
        && task.getPerformerAccount().getId().equals(account.getId());
    boolean isAdmin = account.getRole().equals(Role.ROLE_ADMIN);
    if (isAdmin || isPerformer) {
      return commentRepository.findByTaskId(taskId).get().stream()
          .map(CommentResponse::new).toList();
    } else {
      throw new AccessDeniedException("Unauthorized comment receiving");
    }
  }

  @Override
  public AddCommentResponse addTaskComment(Account account,
                                           Long taskId,
                                           AddCommentRequest request) {
    var task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    var comment = new Comment();
    boolean isPerformer = account.getRole().equals(Role.ROLE_USER)
        && task.getPerformerAccount().getId().equals(account.getId());
    boolean isAdmin = account.getRole().equals(Role.ROLE_ADMIN);
    if (isAdmin || isPerformer) {
      comment.setId(null)
          .setCommentAuthorId(account.getId())
          .setTaskId(taskId)
          .setText(request.text())
          .setActive(true);
      Comment newComment = commentRepository.save(comment);
      return new AddCommentResponse(newComment.getId());
    } else {
      throw new AccessDeniedException("Unauthorized comment creation");
    }
  }

  @Override
  public EditCommentResponse editTaskComment(Account account,
                                             EditCommentRequest request,
                                             Long commentId) {
    var comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CommentNotFoundException(commentId));
    boolean isCommentAuthor = comment.getCommentAuthorId().equals(account.getId());
    if (isCommentAuthor) {
      comment.setText(request.text());
      commentRepository.save(comment);
      return new EditCommentResponse(commentId);
    } else {
      throw new AccessDeniedException("Unauthorized comment editing");
    }
  }

  @Override
  public DeleteCommentResponse deleteComment(Long commentId) {
    var comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CommentNotFoundException(commentId));
    comment.setActive(false);
    commentRepository.save(comment);
    return new DeleteCommentResponse(commentId);
  }
}
