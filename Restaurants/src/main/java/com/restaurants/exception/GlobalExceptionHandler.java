package com.restaurants.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
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
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link CategoryNotFoundException} and returns a standardized error response.
   *
   * @param ex      the exception thrown
   * @param request the HTTP request that caused the exception
   * @return the error response
   */
  @ExceptionHandler(CategoryNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleCategoryNotFoundException(CategoryNotFoundException ex, HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link RestaurantNotFoundException} and returns a standardized error response.
   *
   * @param ex      the exception thrown
   * @param request the HTTP request that caused the exception
   * @return the error response
   */
  @ExceptionHandler(RestaurantNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleRestaurantNotFoundException(RestaurantNotFoundException ex, HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link FoodItemNotFoundException} and returns a standardized error response.
   *
   * @param ex      the exception thrown
   * @param request the HTTP request that caused the exception
   * @return the error response
   */
  @ExceptionHandler(FoodItemNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleFoodItemNotFoundException(FoodItemNotFoundException ex, HttpServletRequest request) {
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles {@link MethodArgumentNotValidException} and returns a standardized error response for validation errors.
   *
   * @param ex      the exception thrown
   * @param request the HTTP request that caused the exception
   * @return the error response
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
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
   * Represents the error response returned by the exception handler.
   */
  @Data
  @AllArgsConstructor
  public static class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
  }
}
