package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.dto.indto.UpdateUserInDto;
import com.users.dto.indto.UserInDto;
import com.users.dto.indto.UserLoginInDto;
import com.users.dto.outdto.UpdateUserOutDto;
import com.users.dto.outdto.UserOutDto;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.User;
import com.users.exception.InsufficientBalanceException;
import com.users.exception.InvalidCredentialsException;
import com.users.exception.UserAlreadyExistsException;
import com.users.exception.UserNotFoundException;
import com.users.repositories.UserRepository;
import com.users.service.UserService;
import com.users.utils.PasswordEncodingDecodingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface.
 * <p>
 * This service provides methods for user registration, login, retrieval, update, deletion, and wallet balance management.
 * </p>
 */
@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserOutDto registerUser(UserInDto userRequest) {
    logger.info("Registering new user with email: {}", userRequest.getEmail());
    Optional<User> emailExists = userRepository.findByEmail(userRequest.getEmail());
    if (emailExists.isPresent()) {
      logger.error("User with email {} already exists", userRequest.getEmail());
      throw new UserAlreadyExistsException(UserConstants.USER_ALREADY_EXISTS);
    }
    User user = DtoConversion.convertUserRequestToUser(userRequest);
    user.setPassword(PasswordEncodingDecodingUtils.encodePassword(userRequest.getPassword()));
    User savedUser = userRepository.save(user);
    logger.info("User registered successfully with ID: {}", savedUser.getId());
    return DtoConversion.convertUserToUserResponse(savedUser);
  }

  @Override
  public UserOutDto loginUser(UserLoginInDto userRequest) {
    logger.info("Login for email: {}", userRequest.getEmail());
    Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
    if (!user.isPresent() || !PasswordEncodingDecodingUtils.decodePassword(user.get().getPassword()).equals(userRequest.getPassword())) {
      logger.error("Invalid credentials for email: {}", userRequest.getEmail());
      throw new InvalidCredentialsException(UserConstants.INVALID_CREDENTIALS);
    }
    logger.info("Login successful for email: {}", userRequest.getEmail());
    return DtoConversion.convertUserToUserResponse(user.get());
  }

  @Override
  public List<UserOutDto> getAllUsers() {
    logger.info("Fetching all users");
    List<User> userList = userRepository.findAll();
    List<UserOutDto> list = new ArrayList<>();
    for (User user : userList) {
      list.add(DtoConversion.convertUserToUserResponse(user));
    }
    logger.info("Number of users fetched: {}", list.size());
    return list;
  }

  @Override
  public User findUserById(Integer id) {
    logger.info("Finding user by ID: {}", id);
    return userRepository.findById(id).orElseThrow(() -> {
      logger.error("User not found with ID: {}", id);
      return new UserNotFoundException(UserConstants.USER_NOT_FOUND);
    });
  }

  @Override
  public UpdateUserOutDto updateUser(Integer id, UpdateUserInDto request) {
    logger.info("Updating user with ID: {}", id);
    User existingUser = findUserById(id);
    DtoConversion.convertUpdateUserRequestToUser(existingUser, request);
    existingUser.setPassword(PasswordEncodingDecodingUtils.encodePassword(request.getPassword()));

    User updatedUser = userRepository.save(existingUser);
    logger.info("User updated successfully with ID: {}", updatedUser.getId());
    return DtoConversion.convertUserToUpdateUserResponse(updatedUser);
  }


  @Override
  public void deleteUser(Integer id) {
    logger.info("Deleting user with ID: {}", id);
    User user = findUserById(id);
    userRepository.delete(user);
    logger.info("User deleted successfully with ID: {}", id);
  }

  @Override
  @Transactional
  public UserOutDto updateWalletBalance(Integer userId, BigDecimal amount) {
    logger.info("Updating wallet balance for user ID: {}", userId);
    User user = findUserById(userId);
    BigDecimal currentBalance = user.getWalletBalance();
    if (currentBalance.compareTo(amount) < 0) {
      logger.error("Insufficient funds for user ID: {}", userId);
      throw new InsufficientBalanceException(UserConstants.INSUFFICIENT_BALANCE);
    }
    user.setWalletBalance(currentBalance.subtract(amount));
    userRepository.save(user);
    logger.info("Wallet balance updated successfully for user ID: {}", userId);
    return DtoConversion.convertUserToUserResponse(user);
  }
}
