package com.restaurants.exception;

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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler to manage exceptions thrown across the application.
 * <p>
 * This class uses Spring's {@link ControllerAdvice} to handle exceptions globally
 * and provide consistent error responses for various types of exceptions.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link MethodArgumentNotValidException} thrown when validation of method arguments fails.
   * <p>
   * This method collects validation error messages from the exception and returns them
   * in a standardized error response with HTTP status 400 (Bad Request).
   * </p>
   *
   * @param ex      the {@link MethodArgumentNotValidException} thrown
   * @param request the HTTP request that caused the exception
   * @return an {@link ErrorResponse} containing validation error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
    List<String> errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(FieldError::getDefaultMessage)
      .collect(Collectors.toList());

    String errorMessage = String.join(", ", errors);
    return new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      "Validation failed: " + errorMessage,
      request.getRequestURI()
    );
  }

  /**
   * Handles {@link ResourceNotFoundException} thrown when a requested resource is not found.
   * <p>
   * This method returns a standardized error response with HTTP status 404 (Not Found).
   * </p>
   *
   * @param ex      the {@link ResourceNotFoundException} thrown
   * @param request the HTTP request that caused the exception
   * @return an {@link ErrorResponse} containing the error message
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleResourceNotFoundException(final ResourceNotFoundException ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link ResourceConflictException} thrown when there is a conflict in the request.
   * <p>
   * This method returns a standardized error response with HTTP status 409 (Conflict).
   * </p>
   *
   * @param ex      the {@link ResourceConflictException} thrown
   * @param request the HTTP request that caused the exception
   * @return an {@link ErrorResponse} containing the error message
   */
  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorResponse handleConflictException(final ResourceConflictException ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link AccessDeniedException} thrown when a request is deemed invalid.
   * <p>
   * This method returns a standardized error response with HTTP status 400 (Bad Request).
   * </p>
   *
   * @param ex      the {@link AccessDeniedException} thrown
   * @param request the HTTP request that caused the exception
   * @return an {@link ErrorResponse} containing the error message
   */
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public ErrorResponse handleResourceNotFoundException(final AccessDeniedException ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link InvalidRequestException} thrown when a request is deemed invalid.
   * <p>
   * This method returns a standardized error response with HTTP status 400 (Bad Request).
   * </p>
   *
   * @param ex      the {@link InvalidRequestException} thrown
   * @param request the HTTP request that caused the exception
   * @return an {@link ErrorResponse} containing the error message
   */
  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public final ErrorResponse handleInvalidRequestException(final InvalidRequestException ex, final HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link FeignException} thrown by Feign clients during communication with external services.
   * <p>
   * This method attempts to extract and parse the error message from the Feign exception response.
   * If parsing fails, a generic message is used. The response is returned with the appropriate HTTP status.
   * </p>
   *
   * @param ex      the {@link FeignException} thrown
   * @param request the HTTP request that caused the exception
   * @return a {@link ResponseEntity} containing the {@link ErrorResponse} and the HTTP status
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
   * Represents the error response returned by the exception handler.
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
