package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.dto.ContactUsInDto;
import com.users.dto.MessageOutDto;
import com.users.dto.UpdateUserInDto;
import com.users.dto.UserInDto;
import com.users.dto.UserLoginInDto;
import com.users.dto.UserOutDto;
import com.users.converter.DtoConversion;
import com.users.entities.User;
import com.users.exception.ResourceConflictException;
import com.users.exception.InvalidRequestException;
import com.users.exception.ResourceNotFoundException;
import com.users.exception.UnauthorizedAccessException;
import com.users.repositories.UserRepository;
import com.users.service.UserService;
import com.users.utils.PasswordEncodingDecodingUtils;
import com.users.utils.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface.
 * <p>
 * This service provides methods for managing users, including registration, login,
 * updating user information, and managing wallet balances.
 * </p>
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  /**
   * Service layer dependency for email service-related operations.
   */
  @Autowired
  private EmailServiceImpl emailService;

  /**
   * Service layer dependency for userRepository-related operations.
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * Registers a new user in the system.
   *
   * @param userRequest the user information to be registered
   * @return a message indicating the success of the registration
   * @throws ResourceConflictException if a user with the given email already exists
   * @throws InvalidRequestException if the user role is invalid
   */
  @Override
  public MessageOutDto registerUser(final UserInDto userRequest) {
    String normalizedEmail = normalizeEmail(userRequest.getEmail());
    log.info("Registering new user with email: {}", normalizedEmail);

    Optional<User> emailExists = userRepository.findByEmail(normalizedEmail);
    if (emailExists.isPresent()) {
      log.error("User with email {} already exists", normalizedEmail);
      throw new ResourceConflictException(UserConstants.USER_ALREADY_EXISTS);
    }

    validateUserRole(userRequest.getUserRole());

    User user = DtoConversion.convertUserRequestToUser(userRequest);
    user.setEmail(normalizedEmail);
    user.setPassword(PasswordEncodingDecodingUtils.encodePassword(userRequest.getPassword()));
    User savedUser = userRepository.save(user);
    log.info("User registered successfully with ID: {}", savedUser.getId());
    DtoConversion.convertUserToUserResponse(savedUser);
    return new MessageOutDto(UserConstants.USER_REGISTRATION_SUCCESS);
  }

  /**
   * Logs in a user based on provided credentials.
   *
   * @param userRequest the login credentials
   * @return the user details if login is successful
   * @throws UnauthorizedAccessException if the credentials are invalid
   */
  @Override
  public UserOutDto loginUser(final UserLoginInDto userRequest) {
    String normalizedEmail = normalizeEmail(userRequest.getEmail());
    log.info("Login for email: {}", normalizedEmail);

    Optional<User> user = userRepository.findByEmail(normalizedEmail);

    if (!user.isPresent()
      || !PasswordEncodingDecodingUtils.decodePassword(user.get().getPassword()).equals(userRequest.getPassword())) {
      log.error("Invalid credentials for email: {}", normalizedEmail);
      throw new UnauthorizedAccessException(UserConstants.INVALID_CREDENTIALS);
    }

    validateUserRole(user.get().getUserRole());

    log.info("Login successful for email: {}", normalizedEmail);
    return DtoConversion.convertUserToUserResponse(user.get());
  }

  private String normalizeEmail(final String email) {
    if (email == null) {
      return null;
    }
    return email.trim().toLowerCase();
  }

  /**
   * Updates user information based on provided data.
   *
   * @param id      the ID of the user to be updated
   * @param request the updated user information
   * @return a message indicating the success of the update
   */
  @Override
  public MessageOutDto updateUser(final Integer id, final UpdateUserInDto request) {
    log.info("Updating user with ID: {}", id);
    User existingUser = findUserById(id);

    DtoConversion.convertUpdateUserRequestToUser(existingUser, request);
    existingUser.setPassword(PasswordEncodingDecodingUtils.encodePassword(request.getPassword()));

    User updatedUser = userRepository.save(existingUser);
    log.info("User updated successfully with ID: {}", updatedUser.getId());
    DtoConversion.convertUserToUpdateUserResponse(updatedUser);
    return new MessageOutDto(UserConstants.USER_PROFILE_UPDATE_SUCCESS);
  }
  /**
   * Retrieves all users from the system.
   *
   * @return a list of all users
   */
  @Override
  public List<UserOutDto> getAllUsers() {
    log.info("Fetching all users");
    List<User> userList = userRepository.findAll();
    List<UserOutDto> list = new ArrayList<>();
    for (User user : userList) {
      list.add(DtoConversion.convertUserToUserResponse(user));
    }
    log.info("Number of users fetched: {}", list.size());
    return list;
  }

  /**
   * Retrieves a specific user by ID.
   *
   * @param id the ID of the user to be retrieved
   * @return the user with the specified ID
   * @throws ResourceNotFoundException if the user is not found
   */
  @Override
  public User findUserById(final Integer id) {
    log.info("Finding user by ID: {}", id);
    return userRepository.findById(id).orElseThrow(() -> {
      log.error("User not found with ID: {}", id);
      return new ResourceNotFoundException(UserConstants.USER_NOT_FOUND);
    });
  }
  /**
   * Updates the wallet balance for a user.
   *
   * @param userId        the ID of the user whose wallet is to be updated
   * @param amount    the amount to be added to the wallet
   * @return a message indicating the success of the operation
   * @throws InvalidRequestException if the operation is not valid
   */
  @Override
  @Transactional
  public MessageOutDto updateWalletBalance(final Integer userId, final BigDecimal amount) {
    log.info("Updating wallet balance for user ID: {}", userId);
    User user = findUserById(userId);
    BigDecimal currentBalance = user.getWalletBalance();

    if (amount.compareTo(BigDecimal.ZERO) < 0 && currentBalance.compareTo(amount.negate()) < 0) {
      log.error("Insufficient funds for user ID: {}", userId);
      throw new InvalidRequestException(UserConstants.INSUFFICIENT_BALANCE);
    }

    user.setWalletBalance(currentBalance.add(amount));
    userRepository.save(user);

    log.info("Wallet balance updated successfully for user ID: {}", userId);
    DtoConversion.convertUserToUserResponse(user);
    return new MessageOutDto(UserConstants.WALLET_FUND_WITHDRAWAL_SUCCESS);
  }

  /**
   * Updates the add money to the wallet for a user.
   *
   * @param userId        the ID of the user whose wallet is to be updated
   * @param amount    the amount to be added to the wallet
   * @return a message indicating the success of the operation
   * @throws InvalidRequestException if the operation is not valid
   */
  @Override
  @Transactional
  public MessageOutDto addMoneyToWallet(final Integer userId, final BigDecimal amount) {
    log.info("Adding money to wallet for user ID: {}", userId);
    User user = findUserById(userId);

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      log.error("Invalid amount: {}. Amount must be greater than zero.", amount);
      throw new InvalidRequestException(UserConstants.INVALID_AMOUNT);
    }

    user.setWalletBalance(user.getWalletBalance().add(amount));
    userRepository.save(user);

    log.info("Money added to wallet successfully for user ID: {}", userId);
    DtoConversion.convertUserToUserResponse(user);
    return new MessageOutDto(UserConstants.WALLET_ADDITION_SUCCESS);
  }
  /**
   * Sends an email in response to a "Contact Us" form submission.
   * <p>
   * The email is sent to the specified recipient with the provided subject and message.
   * The sender and contact information are configured through application properties.
   * </p>
   **/
  @Override
  public MessageOutDto sendContactUsEmail(final ContactUsInDto contactUsDto) {
    List<String> supportEmails = Arrays.asList("shruthimohan174@gmail.com",
      "shruthimohan104@gmail.com", "shruthimohan1708@gmail.com");

    String subject = contactUsDto.getSubject();
    String customerName = contactUsDto.getName();
    String customMessage = contactUsDto.getMessage();
    log.info("Sending contact us email...");

    emailService.sendContactUsEmail(supportEmails, subject, customerName, customMessage);
    return new MessageOutDto(UserConstants.EMAIL_SENT_SUCCESS);
  }

  /**
   * Validates the user role.
   *
   * @param role the role to be validated
   * @throws InvalidRequestException if the user role is invalid
   */
  public void validateUserRole(final UserRole role) {
    if (role == null) {
      log.error("User role is null");
      throw new InvalidRequestException(UserConstants.INVALID_USER_ROLE);
    }
  }
}
