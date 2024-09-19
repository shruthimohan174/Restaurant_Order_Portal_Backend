package com.users.service;

import com.users.dto.UpdateUserInDto;
import com.users.dto.UserInDto;
import com.users.dto.UserLoginInDto;
import com.users.dto.ContactUsInDto;
import com.users.dto.MessageOutDto;
import com.users.dto.UserOutDto;
import com.users.entities.User;
import com.users.utils.UserRole;

import javax.transaction.Transactional;
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
   * @return a {@link MessageOutDto} containing details of the registration outcome
   */
  MessageOutDto registerUser(UserInDto request);

  /**
   * Logs in a user.
   *
   * @param request the user login request DTO containing login credentials
   * @return a {@link UserOutDto} containing details of the logged-in user
   */
  UserOutDto loginUser(UserLoginInDto request);

  /**
   * Retrieves all users.
   *
   * @return a list of {@link UserOutDto} representing all users
   */
  List<UserOutDto> getAllUsers();

  /**
   * Finds a user by their ID.
   *
   * @param id the ID of the user to find
   * @return a {@link User} entity with the specified ID
   */
  User findUserById(Integer id);

  /**
   * Updates an existing user.
   *
   * @param id      the ID of the user to update
   * @param request the update user request DTO containing updated user details
   * @return a {@link MessageOutDto} containing details of the update outcome
   */
  MessageOutDto updateUser(Integer id, UpdateUserInDto request);

  /**
   * Updates the wallet balance of a user.
   *
   * @param userId the ID of the user whose wallet balance is to be updated
   * @param amount the amount to be deducted or added to the user's wallet balance
   * @return a {@link MessageOutDto} containing details of the wallet balance update
   */
  MessageOutDto updateWalletBalance(Integer userId, BigDecimal amount);

  /**
   * Adds money to a user's wallet.
   *
   * @param userId the ID of the user to whom money will be added
   * @param amount the amount to be added to the user's wallet
   * @return a {@link MessageOutDto} containing details of the wallet update
   */
  @Transactional
  MessageOutDto addMoneyToWallet(Integer userId, BigDecimal amount);

  /**
   * Sends a contact us email.
   *
   * @param contactUsDto the contact us request DTO containing the email details
   * @return a {@link MessageOutDto} containing details of the email sending outcome
   */
  MessageOutDto sendContactUsEmail(ContactUsInDto contactUsDto);

  /**
   * Validates a user's role.
   *
   * @param role the role to be validated
   */
  void validateUserRole(UserRole role);
}
