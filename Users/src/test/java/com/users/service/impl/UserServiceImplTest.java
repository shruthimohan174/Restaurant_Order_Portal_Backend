package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.dto.UpdateUserInDto;
import com.users.dto.UpdateUserOutDto;
import com.users.dto.UserInDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.users.dto.UserLoginInDto;
import com.users.dto.ContactUsInDto;
import com.users.dto.MessageOutDto;
import com.users.dto.UserOutDto;
import com.users.entities.User;
import com.users.exception.ResourceConflictException;
import com.users.exception.InvalidRequestException;
import com.users.exception.ResourceNotFoundException;
import com.users.exception.UnauthorizedAccessException;
import com.users.repositories.UserRepository;
import com.users.utils.PasswordEncodingDecodingUtils;
import com.users.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Unit tests for {@link UserServiceImpl}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link UserServiceImpl} class, including testing various methods related
 * to user management, such as creating, updating, and retrieving user
 * information.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  /**
   * The service under test.
   * <p>
   * {@code UserServiceImpl} is the class that contains the business logic
   * for managing users, including operations like registration, updates,
   * and user retrieval.
   * </p>
   */
  @InjectMocks
  private UserServiceImpl userService;

  /**
   * Mock of the {@link UserRepository}.
   * <p>
   * This mock is used to simulate interactions with the user repository
   * and verify the behavior of the service without depending on the actual
   * repository implementation.
   * </p>
   */
  @Mock
  private UserRepository userRepository;

  /**
   * Mock of the {@link EmailServiceImpl}.
   * <p>
   * This mock is used to simulate interactions with the email service
   * for testing the email-related functionality within the user service.
   * </p>
   */
  @Mock
  private EmailServiceImpl emailService;

  /**
   * An instance of {@link User} used for testing.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of the {@link UserServiceImpl} methods that interact with
   * user entities.
   * </p>
   */
  private User user;

  /**
   * An instance of {@link UserInDto} used for testing user creation and
   * registration.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of methods that process input data for user registration or
   * creation.
   * </p>
   */
  private UserInDto userRequest;

  /**
   * An instance of {@link UserOutDto} used for testing user response data.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of methods that generate or process output data for users.
   * </p>
   */
  private UserOutDto userResponse;

  /**
   * An instance of {@link UpdateUserInDto} used for testing user update
   * requests.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of methods that process input data for updating user details.
   * </p>
   */
  private UpdateUserInDto updateUserRequest;

  /**
   * An instance of {@link UpdateUserOutDto} used for testing user update
   * response data.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of methods that generate or process output data for updated
   * user information.
   * </p>
   */
  private UpdateUserOutDto updateUserResponse;

  /**
   * Sets up the test environment by initializing test objects before each test.
   * <p>
   * This method is executed before each test method and is used to create
   * instances of {@link User}, {@link UserInDto}, {@link UserOutDto},
   * {@link UpdateUserInDto}, and {@link UpdateUserOutDto} with predefined
   * values.
   * </p>
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    user = new User();
    user.setId(1);
    user.setEmail("email");
    user.setPassword(PasswordEncodingDecodingUtils.encodePassword("Password@123"));
    user.setFirstName("first");
    user.setLastName("last");
    user.setPhoneNumber("8434972888");
    user.setUserRole(UserRole.CUSTOMER);
    user.setWalletBalance(new BigDecimal("100.00"));

    userRequest = new UserInDto();
    userRequest.setFirstName("first");
    userRequest.setLastName("last");
    userRequest.setEmail("email");
    userRequest.setPassword("Password@123");
    userRequest.setPhoneNumber("8434972888");
    userRequest.setUserRole(UserRole.CUSTOMER);

    userResponse = new UserOutDto();
    userResponse.setId(1);
    userResponse.setFirstName("first");
    userResponse.setLastName("last");
    userResponse.setEmail("email");
    userResponse.setPhoneNumber("8434972888");
    userResponse.setUserRole(UserRole.CUSTOMER);

    updateUserRequest = new UpdateUserInDto();
    updateUserRequest.setFirstName("first");
    updateUserRequest.setLastName("last");
    updateUserRequest.setPhoneNumber("8434972888");

    updateUserResponse = new UpdateUserOutDto();
    updateUserResponse.setId(1);
    updateUserResponse.setFirstName("first");
    updateUserResponse.setLastName("last");
    updateUserResponse.setPhoneNumber("8434972888");
  }

  @Test
  void registerUserSuccessTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    MessageOutDto response = userService.registerUser(userRequest);

    assertEquals(UserConstants.USER_REGISTRATION_SUCCESS, response.getMessage());
  }

  @Test
  void registerUserUserAlreadyExistsTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    assertThrows(ResourceConflictException.class, () -> {
      userService.registerUser(userRequest);
    });
  }

  @Test
  void loginUserSuccessTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    UserOutDto response = userService.loginUser(new UserLoginInDto("email", "Password@123"));

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

    assertThrows(UnauthorizedAccessException.class, () -> {
      userService.loginUser(new UserLoginInDto("email", "WrongPassword"));
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

    assertThrows(ResourceNotFoundException.class, () -> {
      userService.findUserById(1);
    });
  }

  @Test
  void updateUserSuccessTest() {
    UpdateUserInDto updateUserRequest = new UpdateUserInDto();
    updateUserRequest.setPassword("validpassword");

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    MessageOutDto response = userService.updateUser(1, updateUserRequest);

    assertEquals(UserConstants.USER_PROFILE_UPDATE_SUCCESS, response.getMessage());
  }


  @Test
  void updateWalletBalanceSuccessTest() {
    BigDecimal amountToDeduct = new BigDecimal("50.00");
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    MessageOutDto response = userService.updateWalletBalance(1, amountToDeduct);

    assertEquals(UserConstants.WALLET_FUND_WITHDRAWAL_SUCCESS, response.getMessage());
  }

  @Test
  void updateWalletBalanceInsufficientFundsTest() {
  }



  @Test
  void updateWalletBalanceUserNotFoundTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    BigDecimal amountToDeduct = new BigDecimal("50.00");

    assertThrows(ResourceNotFoundException.class, () -> {
      userService.updateWalletBalance(1, amountToDeduct);
    });

    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  void addMoneyToWalletSuccessTest() {
    BigDecimal amountToAdd = new BigDecimal("50.00");
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    MessageOutDto response = userService.addMoneyToWallet(1, amountToAdd);

    assertEquals(UserConstants.WALLET_ADDITION_SUCCESS, response.getMessage());
  }

  @Test
  void addMoneyToWalletInvalidAmountTest() {
    BigDecimal amountToAdd = new BigDecimal("-50.00");
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    assertThrows(InvalidRequestException.class, () -> {
      userService.addMoneyToWallet(1, amountToAdd);
    });

    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  void sendContactUsEmailTest() {
    ContactUsInDto contactUsDto = new ContactUsInDto();
    contactUsDto.setSubject("Inquiry");
    contactUsDto.setName("first last");
    contactUsDto.setMessage("Message content");

    doNothing().when(emailService).sendContactUsEmail(
      anyList(), anyString(), anyString(), anyString()
    );

    MessageOutDto response = userService.sendContactUsEmail(contactUsDto);

    assertEquals(UserConstants.EMAIL_SENT_SUCCESS, response.getMessage());
    verify(emailService, times(1)).sendContactUsEmail(
      eq(Arrays.asList(
        "shruthimohan174@gmail.com",
        "shruthimohan104@gmail.com",
        "shruthimohan1708@gmail.com"
      )),
      eq("Inquiry"),
      eq("first last"),
      eq("Message content")
    );
  }
}
