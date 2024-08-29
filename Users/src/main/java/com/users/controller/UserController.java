package com.users.controller;

import com.users.dto.indto.UpdateUserInDto;
import com.users.dto.indto.UserInDto;
import com.users.dto.indto.UserLoginInDto;
import com.users.dto.outdto.UpdateUserOutDto;
import com.users.dto.outdto.UserOutDto;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.User;
import com.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
  public ResponseEntity<UserOutDto> registerUser(@Valid @RequestBody UserInDto userRequest) {
    logger.info("Registering user with email: {}", userRequest.getEmail());
    UserOutDto userResponse = userService.registerUser(userRequest);
    logger.info("User registered successfully with email: {}", userRequest.getEmail());
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
  public ResponseEntity<UserOutDto> loginUser(@RequestBody UserLoginInDto userRequest) {
    logger.info("Logging in user with email: {}", userRequest.getEmail());
    UserOutDto userResponse = userService.loginUser(userRequest);
    logger.info("User logged in successfully with email: {}", userRequest.getEmail());
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
    logger.info("Fetching all users");
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
  public ResponseEntity<UpdateUserOutDto> updateUser(@PathVariable Integer id, @Valid @RequestBody UpdateUserInDto request) {
    logger.info("Updating user with ID: {}", id);
    UpdateUserOutDto userResponse = userService.updateUser(id, request);
    logger.info("User updated successfully with ID: {}", id);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  /**
   * Deletes a user.
   * <p>
   * This endpoint deletes the user identified by the provided ID. It returns HTTP 204 No Content
   * status upon successful deletion.
   * </p>
   *
   * @param id the ID of the user to delete
   * @return a {@link ResponseEntity} with HTTP status
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    logger.info("Deleting user with ID: {}", id);
    userService.deleteUser(id);
    logger.info("User deleted successfully with ID: {}", id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
  public ResponseEntity<UserOutDto> getByUserId(@PathVariable Integer id) {
    logger.info("Fetching user details with id: {}", id);
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
  public ResponseEntity<UserOutDto> updateWalletBalance(@PathVariable Integer id, @RequestBody BigDecimal amount) {
    logger.info("Deducting {} from user ID: {}", amount, id);
    UserOutDto userResponse = userService.updateWalletBalance(id, amount);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }
}
