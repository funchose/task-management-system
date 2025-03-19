package com.effectivemobile.taskmanagement.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
@Hidden
@ControllerAdvice
public class CustomRestExceptionHandler {

  /**
   * Handles AccountNotFoundException, which is thrown
   * when the searched in the DB account is absent
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({AccountNotFoundException.class})
  public ResponseEntity<Object> handleAccountNotFoundException(
      AccountNotFoundException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles CommentNotFoundException, which is thrown
   * when the searched in the DB comment is absent
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({CommentNotFoundException.class})
  public ResponseEntity<Object> handleCommentNotFoundException(
      CommentNotFoundException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles TaskNotFoundException, which is thrown
   * when the searched in the DB task is absent
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({TaskNotFoundException.class})
  public ResponseEntity<Object> handleTaskNotFoundException(
      TaskNotFoundException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles HttpMessageNotReadableException, which is thrown
   * when the HTTP request is invalid and cannot be parsed into
   * entity of request class
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    return new ResponseEntity<>(
        errorResponse, new HttpHeaders(), errorResponse.status());
  }

  /**
   * Handles MethodArgumentNotValidException, which is thrown
   * when required arguments are absent. If the constraints are violated at the same time -
   * they are handled here as well
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    Map<String, List<String>> body = new HashMap<>();
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    body.put("errors", errors);
    return new ResponseEntity<>(
        body, new HttpHeaders(), errorResponse.status());
  }

  /**
   * Handles DataIntegrityViolationException, which is thrown
   * when the request entity contains parameters of entity, which must be unique
   * but already present in the DB (email, phone number)
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<Object> Exception(
      DataIntegrityViolationException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(
        errorResponse, new HttpHeaders(), errorResponse.status());
  }

  /**
   * Handles AccountAlreadyExistsException, which is thrown
   * when the SignUpRequest contains username, already existing in the DB
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({AccountAlreadyExistsException.class})
  public ResponseEntity<Object> handlerAccountAlreadyExistsException(
      AccountAlreadyExistsException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(
        errorResponse, new HttpHeaders(), errorResponse.status());
  }

  /**
   * Handles HttpClientErrorException, which is thrown
   * when the unauthorized user performs requests
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<Object> handlerHttpClientErrorException(
      HttpClientErrorException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    return new ResponseEntity<>(
        errorResponse, new HttpHeaders(), errorResponse.status());
  }


  /**
   * Handles AccessDeniedException, which is thrown
   * when the unauthorized user performs requests
   *
   * @param ex exception
   * @return response with HTTP code and exception message
   */
  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handlerAccessDeniedException(
      AccessDeniedException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    return new ResponseEntity<>(
        errorResponse, new HttpHeaders(), errorResponse.status());
  }
}