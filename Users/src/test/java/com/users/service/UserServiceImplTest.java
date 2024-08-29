package com.users.service;

import com.users.dto.indto.UpdateUserInDto;
import com.users.dto.indto.UserInDto;
import com.users.dto.indto.UserLoginInDto;
import com.users.dto.outdto.UpdateUserOutDto;
import com.users.dto.outdto.UserOutDto;
import com.users.entities.User;
import com.users.entities.UserRole;
import com.users.exception.InsufficientBalanceException;
import com.users.exception.InvalidCredentialsException;
import com.users.exception.UserAlreadyExistsException;
import com.users.exception.UserNotFoundException;
import com.users.repositories.UserRepository;
import com.users.serviceimpl.UserServiceImpl;
import com.users.utils.Base64Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  private User user;
  private UserInDto userRequest;
  private UserOutDto userResponse;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    user = new User();
    user.setId(1);
    user.setEmail("shruthimohan1708@gmail.com");
    user.setPassword(Base64Utils.encodePassword("Password@123"));
    user.setFirstName("Shruthi");
    user.setLastName("Mohan");
    user.setPhoneNumber("8434972888");
    user.setUserRole(UserRole.CUSTOMER);

    userRequest = new UserInDto();
    userRequest.setFirstName("Shruthi");
    userRequest.setLastName("Mohan");
    userRequest.setEmail("shruthimohan1708@gmail.com");
    userRequest.setPassword("Password@123");
    userRequest.setPhoneNumber("8434972888");
    userRequest.setUserRole(UserRole.CUSTOMER);

    userResponse = new UserOutDto();
    userResponse.setId(1);
    userResponse.setFirstName("Shruthi");
    userResponse.setLastName("Mohan");
    userResponse.setEmail("shruthimohan1708@gmail.com");
    userResponse.setPhoneNumber("8434972888");
    userResponse.setUserRole(UserRole.CUSTOMER);
  }

  @Test
  void registerUserSuccessTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserOutDto response = userService.registerUser(userRequest);

    assertEquals(userResponse.getId(), response.getId());
    assertEquals(userResponse.getFirstName(), response.getFirstName());
    assertEquals(userResponse.getLastName(), response.getLastName());
    assertEquals(userResponse.getEmail(), response.getEmail());
    assertEquals(userResponse.getPhoneNumber(), response.getPhoneNumber());
    assertEquals(userResponse.getUserRole(), response.getUserRole());
  }

  @Test
  void registerUserUserAlreadyExistsTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    assertThrows(UserAlreadyExistsException.class, () -> {
      userService.registerUser(userRequest);
    });
  }

  @Test
  void loginUserSuccessTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    UserOutDto response = userService.loginUser(new UserLoginInDto("shruthimohan1708@gmail.com", "Password@123"));

    assertEquals(userResponse.getId(), response.getId());
    assertEquals(userResponse.getFirstName(), response.getFirstName());
    assertEquals(userResponse.getLastName(), response.getLastName());
    assertEquals(userResponse.getEmail(), response.getEmail());
    assertEquals(userResponse.getPhoneNumber(), response.getPhoneNumber());
    assertEquals(userResponse.getUserRole(), response.getUserRole());
  }

  @Test
  void loginUserInvalidCredentialsTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

    assertThrows(InvalidCredentialsException.class, () -> {
      userService.loginUser(new UserLoginInDto("shruthimohan1708@gmail.com", "WrongPassword"));
    });
  }

  @Test
  void getAllUsersSuccessTest() {
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    List<UserOutDto> users = userService.getAllUsers();

    assertNotNull(users);
    assertFalse(users.isEmpty());
    assertEquals(1, users.size());
    assertEquals(user.getId(), users.get(0).getId());
  }

  @Test
  void findUserByIdSuccessTests() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    User foundUser = userService.findUserById(1);

    assertEquals(user.getId(), foundUser.getId());
    assertEquals(user.getEmail(), foundUser.getEmail());
  }

  @Test
  void findUserByIdUserNotFoundTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> {
      userService.findUserById(1);
    });
  }

  @Test
  void updateUserSuccessTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    UpdateUserInDto updateUserInDto = new UpdateUserInDto();
    updateUserInDto.setFirstName("Shruthi");
    updateUserInDto.setLastName("Mohan");
    updateUserInDto.setPhoneNumber("8434972888");

    UpdateUserOutDto updateUserResponse = new UpdateUserOutDto();
    updateUserResponse.setId(1);
    updateUserResponse.setFirstName("Shruthi");
    updateUserResponse.setLastName("Mohan");
    updateUserResponse.setPhoneNumber("8434972888");

    UpdateUserOutDto response = userService.updateUser(1, updateUserInDto);

    assertEquals(updateUserResponse.getId(), response.getId());
    assertEquals(updateUserResponse.getFirstName(), response.getFirstName());
    assertEquals(updateUserResponse.getLastName(), response.getLastName());
    assertEquals(updateUserResponse.getPhoneNumber(), response.getPhoneNumber());
  }

  @Test
  void deleteUserSuccessTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    userService.deleteUser(1);

    verify(userRepository, times(1)).delete(user);
  }

  @Test
  void deleteUserUserNotFoundTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> {
      userService.deleteUser(1);
    });
  }

  @Test
  void updateWalletBalanceSuccessTest() {
    User user = new User();
    user.setId(1);
    user.setWalletBalance(new BigDecimal("100.00"));

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
    BigDecimal amountToDeduct = new BigDecimal("50.00");
    UserOutDto response = userService.updateWalletBalance(1, amountToDeduct);
    assertEquals(new BigDecimal("50.00"), response.getWalletBalance());
  }

  @Test
  void updateWalletBalanceInsufficientFundsTest() {
    user.setWalletBalance(new BigDecimal("30.00"));

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    BigDecimal amountToDeduct = new BigDecimal("50.00");

    // Act & Assert
    assertThrows(InsufficientBalanceException.class, () -> {
      userService.updateWalletBalance(1, amountToDeduct);
    });

    verify(userRepository, times(0)).save(any(User.class)); // Save should not be called
  }

  @Test
  void updateWalletBalanceUserNotFoundTest() {
    // Arrange
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    BigDecimal amountToDeduct = new BigDecimal("50.00");

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> {
      userService.updateWalletBalance(1, amountToDeduct);
    });

    verify(userRepository, times(0)).save(any(User.class)); // Save should not be called
  }
}

