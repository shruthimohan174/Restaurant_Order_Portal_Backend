package com.users.controller;

import com.users.dto.UpdateUserInDto;
import com.users.dto.UserInDto;
import com.users.dto.UserLoginInDto;
import com.users.dto.ContactUsInDto;
import com.users.dto.MessageOutDto;
import com.users.dto.UserOutDto;
import com.users.converter.DtoConversion;
import com.users.entities.User;
import com.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for managing users.
 * <p>
 * This controller provides endpoints to register, login, update, delete, and fetch user details.
 * It interacts with the {@link UserService} to perform these operations.
 * </p>
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
  /**
   * Service layer dependency for user-related operations.
   */
  @Autowired
  private UserService userService;

  /**
   * Registers a new user.
   * <p>
   * This endpoint creates a new user based on the provided details in the request body.
   * It returns the created user's details with HTTP 201 Created status upon successful registration.
   * </p>
   *
   * @param userRequest the request body containing user registration details
   * @return a {@link ResponseEntity} containing the user response and HTTP status
   */
  @PostMapping("/register")
  public ResponseEntity<MessageOutDto> registerUser(final @Valid @RequestBody UserInDto userRequest) {
    log.info("Registering user with email: {}", userRequest.getEmail());
    MessageOutDto userResponse = userService.registerUser(userRequest);
    log.info("User registered successfully with email: {}", userRequest.getEmail());
    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
  }

  /**
   * Logs in a user.
   * <p>
   * This endpoint authenticates the user based on the provided login details in the request body.
   * It returns the user's details with HTTP 200 OK status upon successful login.
   * </p>
   *
   * @param userRequest the request body containing user login details
   * @return a {@link ResponseEntity} containing the user response and HTTP status
   */
  @PostMapping("/login")
  public ResponseEntity<UserOutDto> loginUser(final @RequestBody UserLoginInDto userRequest) {
    log.info("Logging in user with email: {}", userRequest.getEmail());
    UserOutDto userResponse = userService.loginUser(userRequest);
    log.info("User logged in successfully with email: {}", userRequest.getEmail());
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  /**
   * Retrieves all users.
   * <p>
   * This endpoint fetches and returns a list of all users with HTTP 200 OK status.
   * </p>
   *
   * @return a {@link ResponseEntity} containing a list of users and HTTP status
   */
  @GetMapping("")
  public ResponseEntity<List<UserOutDto>> getAllUsers() {
    log.info("Fetching all users");
    List<UserOutDto> userList = userService.getAllUsers();
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }

  /**
   * Updates an existing user's details.
   * <p>
   * This endpoint updates the user identified by the provided ID with the details
   * in the request body. It returns the updated user details with HTTP 200 OK status.
   * </p>
   *
   * @param id      the ID of the user to update
   * @param request the request body containing updated user details
   * @return a {@link ResponseEntity} containing the updated user response and HTTP status
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<MessageOutDto> updateUser(final @PathVariable Integer id,
                                                  final @Valid @RequestBody UpdateUserInDto request) {
    log.info("Updating user with ID: {}", id);
    MessageOutDto userResponse = userService.updateUser(id, request);
    log.info("User updated successfully with ID: {}", id);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  /**
   * Retrieves a user by ID.
   * <p>
   * This endpoint fetches user details by the provided ID and returns them in the response body with HTTP 200 OK.
   * </p>
   *
   * @param id the ID of the user to fetch
   * @return a {@link ResponseEntity} containing the user response and HTTP status
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserOutDto> getByUserId(final @PathVariable Integer id) {
    log.info("Fetching user details with id: {}", id);
    User user = userService.findUserById(id);
    UserOutDto userResponse = DtoConversion.convertUserToUserResponse(user);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  /**
   * Updates the wallet balance of a user.
   * <p>
   * This endpoint updates the wallet balance for the user identified by the provided ID.
   * It returns the updated user details with HTTP 200 OK status.
   * </p>
   *
   * @param id     the ID of the user whose wallet balance is to be updated
   * @param amount the amount to be deducted or added to the user's wallet balance
   * @return a {@link ResponseEntity} containing the updated user response and HTTP status
   */
  @PutMapping("/wallet/{id}")
  public ResponseEntity<MessageOutDto> updateWalletBalance(final @PathVariable Integer id,
                                                           final @RequestBody BigDecimal amount) {
    log.info("Deducting {} from user ID: {}", amount, id);
    MessageOutDto userResponse = userService.updateWalletBalance(id, amount);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  /**
   * Sends a "Contact Us" email.
   * <p>
   * This endpoint allows users to send a contact us email with the details provided in the request body.
   * It returns a message indicating the success or failure of the operation.
   * </p>
   *
   * @param contactUsDto the request body containing contact us details
   * @return a {@link ResponseEntity} containing the response message and HTTP status
   */
  @PostMapping("/contact-us")
  public ResponseEntity<MessageOutDto> sendContactUsEmail(final @RequestBody @Valid ContactUsInDto contactUsDto) {
    log.info("Sending contact us email from user");
    MessageOutDto response = userService.sendContactUsEmail(contactUsDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Adds money to a user's wallet.
   * <p>
   * This endpoint allows a user to add a specified amount of money to their wallet.
   * It returns a message indicating the success of the operation.
   * </p>
   *
   * @param userId the ID of the user to whom money will be added
   * @param amount the amount of money to be added to the user's wallet
   * @return a {@link ResponseEntity} containing the response message and HTTP status
   */
  @PostMapping("/{userId}/addWallet")
  public ResponseEntity<MessageOutDto> addMoneyToWallet(final @PathVariable Integer userId,
                                                        final @RequestBody BigDecimal amount) {
    log.info("Adding {} to user ID: {}", amount, userId);
    MessageOutDto response = userService.addMoneyToWallet(userId, amount);
    return ResponseEntity.ok(response);
  }
}
