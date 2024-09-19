package com.orders.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for managing application-wide exceptions.
 * <p>
 * This class handles various types of exceptions thrown in the application, including custom exceptions and validation errors.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link ResourceNotFoundException} and returns an appropriate error response.
   *
   * @param ex The exception that was thrown.
   * @param request The HTTP request that resulted in the exception.
   * @return An {@link ErrorResponse} with details about the error.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleResourceNotFoundException(final ResourceNotFoundException ex,
                                                             final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link ResourceConflictException} and returns an appropriate error response.
   *
   * @param ex The exception that was thrown.
   * @param request The HTTP request that resulted in the exception.
   * @return An {@link ErrorResponse} with details about the error.
   */
  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public final ErrorResponse handleConflictException(final ResourceConflictException ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link MethodArgumentNotValidException} and returns validation error details.
   *
   * @param ex The exception that was thrown.
   * @param request The HTTP request that resulted in the exception.
   * @return An {@link ErrorResponse} with details about the validation errors.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errors.add(fieldError.getDefaultMessage());
    }

    String errorMessage = String.join(", ", errors);
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation failed: " + errorMessage,
      request.getRequestURI());
  }

  /**
   * Handles generic exceptions and returns a generic error response.
   *
   * @param ex The exception that was thrown.
   * @param request The HTTP request that resulted in the exception.
   * @return An {@link ErrorResponse} with details about the error.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public final ErrorResponse handleGenericException(final Exception ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "An error occurred. Please try again later.", request.getRequestURI());
  }

  /**
   * Handles {@link AccessDeniedException} and returns an appropriate error response.
   *
   * @param ex The exception that was thrown.
   * @param request The HTTP request that resulted in the exception.
   * @return An {@link ErrorResponse} with details about the error.
   */
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public final ErrorResponse handleInvalidRequestException(final AccessDeniedException ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link FeignException} and returns an appropriate error response.
   * <p>
   * Extracts and processes the error message from the Feign client exception response.
   * </p>
   *
   * @param ex The Feign exception that was thrown.
   * @param request The HTTP request that resulted in the exception.
   * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with details about the error.
   */
  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ErrorResponse> handleFeignException(final FeignException ex, final HttpServletRequest request) {
    HttpStatus status = HttpStatus.valueOf(ex.status());
    String message = "An error occurred while processing your request";

    String feignResponseBody = ex.contentUTF8();
    if (feignResponseBody != null) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(feignResponseBody);
        message = jsonNode.get("message").asText();
      } catch (Exception e) {
        message = ex.getMessage();
      }
    }

    ErrorResponse errorResponse = new ErrorResponse(
      LocalDateTime.now(),
      status.value(),
      message,
      request.getRequestURI()
    );

    return new ResponseEntity<>(errorResponse, status);
  }

  /**
   * ErrorResponse class to encapsulate error details.
   */
  @Data
  @AllArgsConstructor
  public static class ErrorResponse {

    /**
     * The timestamp when the error occurred.
     * This is useful for logging and debugging to determine when the error happened.
     */
    private LocalDateTime timestamp;

    /**
     * The HTTP status code associated with the error.
     * This code indicates the type of error that occurred (e.g., 404 for Not Found, 500 for Internal Server Error).
     */
    private int status;

    /**
     * A descriptive message explaining the error.
     * This message provides more details about the error to help understand what went wrong.
     */
    private String message;

    /**
     * The path or endpoint where the error occurred.
     * This helps in identifying which part of the application triggered the error.
     */
    private String path;
  }
}

