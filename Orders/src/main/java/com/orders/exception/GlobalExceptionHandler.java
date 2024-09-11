package com.orders.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Global exception handler for managing application-wide exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles AddressNotFoundException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(AddressNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleAddressNotFoundException(AddressNotFoundException ex, HttpServletRequest request) {
    logger.error("Address not found: {}", ex.getMessage());
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles CartNotFoundException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(CartNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleCartNotFoundException(CartNotFoundException ex, HttpServletRequest request) {
    logger.error("Cart not found: {}", ex.getMessage());
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles PriceMismatchException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(PriceMismatchException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handlePriceMismatchException(PriceMismatchException ex, HttpServletRequest request) {
    logger.error("Price mismatch: {}", ex.getMessage());
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles InsufficientBalanceException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(InsufficientBalanceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public final ErrorResponse handleInsufficientBalanceException(InsufficientBalanceException ex, HttpServletRequest request) {
    logger.error("Insufficient balance: {}", ex.getMessage());
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles CustomerNotFoundException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(CustomerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleCustomerNotFoundException(CustomerNotFoundException ex, HttpServletRequest request) {
    logger.error("Customer not found: {}", ex.getMessage());
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles OrderNotFoundException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(OrderNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleOrderNotFoundException(OrderNotFoundException ex, HttpServletRequest request) {
    logger.error("Order not found: {}", ex.getMessage());
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handles OrderUpdateException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(OrderUpdateException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public final ErrorResponse handleOrderUpdateException(OrderUpdateException ex, HttpServletRequest request) {
    logger.error("Order update failed: {}", ex.getMessage());
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

    String errorMessage = String.join(", ", errors);
    logger.error("Validation failed: {}", errorMessage);
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation failed: " + errorMessage,
      request.getRequestURI());
  }

  /**
   * Handles FeignException.
   *
   * @param ex      the exception
   * @param request the HTTP request
   * @return the error response
   */
  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.valueOf(ex.status());
    String message = "An error occurred while processing your request";

    String feignResponseBody = ex.contentUTF8();
    if (feignResponseBody != null) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(feignResponseBody);
        message = jsonNode.get("message").asText();
      } catch (Exception e) {
        logger.error("Failed to parse Feign error response: {}", e.getMessage());
        message = ex.getMessage();
      }
    }

    logger.error("Feign exception: {}", message);
    ErrorResponse errorResponse = new ErrorResponse(
      LocalDateTime.now(),
      status.value(),
      message,
      request.getRequestURI()
    );

    return new ResponseEntity<>(errorResponse, status);
  }

  /**
   * ErrorResponse class to represent standardized error information.
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
