package com.users.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for managing various exceptions across the application.
 * <p>
 * This class uses Spring's {@link ControllerAdvice} to handle exceptions globally
 * and return standardized error responses for different types of exceptions.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link ResourceConflictException} and returns a standardized error response with HTTP status 409 (Conflict).
   *
   * @param ex     the exception to handle
   * @param request the HTTP request
   * @return an {@link ErrorResponse} containing error details
   */
  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public final ErrorResponse handleConflictException(final ResourceConflictException ex,
                                                     final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link UnauthorizedAccessException} and returns a standardized error response with HTTP status 401 (Unauthorized).
   *
   * @param ex     the exception to handle
   * @param request the HTTP request
   * @return an {@link ErrorResponse} containing error details
   */
  @ExceptionHandler(UnauthorizedAccessException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleUnauthorizedException(final UnauthorizedAccessException ex,
                                                   final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link MethodArgumentNotValidException} and returns a standardized error response with HTTP status 400 (Bad Request).
   * <p>
   * This method extracts validation error messages from the exception and includes them in the error response.
   * </p>
   *
   * @param ex     the exception to handle
   * @param request the HTTP request
   * @return an {@link ErrorResponse} containing error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationException(final MethodArgumentNotValidException ex,
                                                 final HttpServletRequest request) {
    List<String> errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(FieldError::getDefaultMessage)
      .collect(Collectors.toList());

    String errorMessage = String.join(",", errors);
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation failed: " + errorMessage,
      request.getRequestURI());
  }

  /**
   * Handles {@link ResourceNotFoundException} and returns a standardized error response with HTTP status 404 (Not Found).
   *
   * @param ex     the exception to handle
   * @param request the HTTP request
   * @return an {@link ErrorResponse} containing error details
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleResourceNotFoundException(final ResourceNotFoundException ex,
                                                       final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link InvalidRequestException} and returns a standardized error response with HTTP status 400 (Bad Request).
   *
   * @param ex     the exception to handle
   * @param request the HTTP request
   * @return an {@link ErrorResponse} containing error details
   */
  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public final ErrorResponse handleInvalidRequestException(final InvalidRequestException ex,
                                                           final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link HttpMessageNotReadableException} and returns a standardized error response with HTTP status 400 (Bad Request).
   * <p>
   * This method provides a more detailed error message if the exception is caused by an invalid format for an enum value.
   * </p>
   *
   * @param ex     the exception to handle
   * @param request the HTTP request
   * @return an {@link ErrorResponse} containing error details
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex,
                                                             final HttpServletRequest request) {
    String errorMessage = "Invalid input format";
    if (ex.getCause() instanceof InvalidFormatException) {
      InvalidFormatException ife = (InvalidFormatException) ex.getCause();
      if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
        errorMessage = String.format("Invalid value for %s. Accepted values are: %s",
          ife.getPath().get(ife.getPath().size() - 1).getFieldName(),
          String.join(", ", getEnumValues(ife.getTargetType())));
      }
    }
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errorMessage, request.getRequestURI());
  }

  /**
   * Represents an error response returned to the client.
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


  /**
   * Retrieves the list of enum values for a given enum class.
   *
   * @param enumClass the enum class
   * @return a list of enum values as strings
   */
  private List<String> getEnumValues(final Class<?> enumClass) {
    return java.util.Arrays.stream(enumClass.getEnumConstants())
      .map(Object::toString)
      .collect(Collectors.toList());
  }
}
