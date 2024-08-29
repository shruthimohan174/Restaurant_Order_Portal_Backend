package com.users.service;

import com.users.dto.indto.UpdateUserInDto;
import com.users.dto.indto.UserInDto;
import com.users.dto.indto.UserLoginInDto;
import com.users.dto.outdto.UpdateUserOutDto;
import com.users.dto.outdto.UserOutDto;
import com.users.entities.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing users.
 * <p>
 * This service provides methods for user registration, login, retrieval, update, deletion, and wallet balance management.
 * </p>
 */
public interface UserService {

  /**
   * Registers a new user.
   *
   * @param request the user request DTO containing registration details
   * @return the {@link UserOutDto} DTO with details of the registered user
   */
  UserOutDto registerUser(UserInDto request);

  /**
   * Logs in a user.
   *
   * @param request the user login request DTO containing login credentials
   * @return the {@link UserOutDto} DTO with details of the logged-in user
   */
  UserOutDto loginUser(UserLoginInDto request);

  /**
   * Retrieves all users.
   *
   * @return a list of {@link User} entities
   */
  List<UserOutDto> getAllUsers();

  /**
   * Finds a user by their ID.
   *
   * @param id the ID of the user to find
   * @return the {@link User} entity with the specified ID
   */
  User findUserById(Integer id);

  /**
   * Updates an existing user.
   *
   * @param id      the ID of the user to update
   * @param request the update user request DTO containing updated user details
   * @return the {@link UpdateUserOutDto} DTO with details of the updated user
   */
  UpdateUserOutDto updateUser(Integer id, UpdateUserInDto request);

  /**
   * Deletes a user by their ID.
   *
   * @param id the ID of the user to delete
   */
  void deleteUser(Integer id);

  /**
   * Updates the wallet balance of a user.
   *
   * @param userId the ID of the user whose wallet balance is to be updated
   * @param amount the amount to be deducted or added to the user's wallet balance
   * @return the {@link UserOutDto} DTO with updated user details
   */
  UserOutDto updateWalletBalance(Integer userId, BigDecimal amount);
}
