package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.dto.UserInDto;
import com.users.dto.UserLoginInDto;
import com.users.dto.ContactUsInDto;
import com.users.dto.MessageOutDto;
import com.users.dto.UserOutDto;
import com.users.entities.User;
import com.users.service.UserService;
import com.users.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link UserController} class.
 * <p>
 * This class contains test cases for the various endpoints provided by the
 * {@link UserController} to ensure that they function as expected. The tests
 * use Mockito to mock the {@link UserService} and verify the behavior of the
 * controller methods.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  /**
   * The {@link UserController} instance that will be tested.
   * <p>
   * This field is injected with the mocked {@link UserService} using
   * the {@link InjectMocks} annotation.
   * </p>
   */
  @InjectMocks
  private UserController userController;

  /**
   * The {@link UserService} mock used to simulate interactions with
   * the user service layer.
   * <p>
   * This field is used to provide predefined responses for the
   * {@link UserController} methods during testing.
   * </p>
   */
  @Mock
  private UserService userService;

  /**
   * The {@link MockMvc} instance used to perform HTTP requests and
   * test the {@link UserController} endpoints.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private MockMvc mockMvc;

  /**
   * The {@link ObjectMapper} instance used for serializing and deserializing
   * JSON content in the tests.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private ObjectMapper objectMapper;

  /**
   * The {@link UserInDto} object used as input for user registration
   * and update tests.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private UserInDto userRequest;

  /**
   * The {@link UserLoginInDto} object used as input for user login tests.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private UserLoginInDto userLoginRequest;

  /**
   * The {@link UserOutDto} object representing the user data returned
   * from the service layer.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private UserOutDto userResponse;

  /**
   * The {@link User} entity representing a user in the system.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private User user;

  /**
   * The {@link ContactUsInDto} object used as input for contact us email tests.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private ContactUsInDto contactUsDto;

  /**
   * The {@link MessageOutDto} object used as a response message for
   * various operations in the tests.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private MessageOutDto messageOutDto;

  /**
   * Sets up the test environment before each test is executed.
   * <p>
   * Initializes the {@link MockMvc} object to perform HTTP requests and
   * sets up the {@link ObjectMapper} for JSON serialization/deserialization.
   * Also initializes the DTOs and mock objects used in the tests.
   * </p>
   */
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    objectMapper = new ObjectMapper();

    userRequest = new UserInDto();
    userRequest.setFirstName("First");
    userRequest.setLastName("Last");
    userRequest.setEmail("email");
    userRequest.setPassword("Password123");
    userRequest.setPhoneNumber("1234567890");
    userRequest.setUserRole(UserRole.RESTAURANT_OWNER);

    userLoginRequest = new UserLoginInDto();
    userLoginRequest.setEmail("email");
    userLoginRequest.setPassword("Password123");

    userResponse = new UserOutDto(1, "First", "Last", "email",
      "1234567890", UserRole.RESTAURANT_OWNER, null);

    user = new User();
    user.setId(1);
    user.setFirstName("First");
    user.setLastName("Last");
    user.setPhoneNumber("1234567890");
    user.setUserRole(UserRole.RESTAURANT_OWNER);

    contactUsDto = new ContactUsInDto();
    contactUsDto.setName("Contact Name");
    contactUsDto.setSubject("Subject");
    contactUsDto.setMessage("Message content");

    messageOutDto = new MessageOutDto("Operation successful");
  }

  @Test
  void loginUserTests() throws Exception {
    when(userService.loginUser(any(UserLoginInDto.class))).thenReturn(userResponse);

    mockMvc.perform(post("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userLoginRequest)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.firstName").value("First"))
      .andExpect(jsonPath("$.lastName").value("Last"))
      .andExpect(jsonPath("$.email").value("email"))
      .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
      .andExpect(jsonPath("$.userRole").value("RESTAURANT_OWNER"));
  }

  @Test
  void getAllUsersTests() throws Exception {
    when(userService.getAllUsers()).thenReturn(Collections.singletonList(userResponse));

    mockMvc.perform(get("/user"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].firstName").value("First"))
      .andExpect(jsonPath("$[0].lastName").value("Last"))
      .andExpect(jsonPath("$[0].email").value("email"))
      .andExpect(jsonPath("$[0].phoneNumber").value("1234567890"))
      .andExpect(jsonPath("$[0].userRole").value("RESTAURANT_OWNER"));
  }

  @Test
  void registerUser() throws Exception {
    MessageOutDto messageOutDto = new MessageOutDto("User registered successfully");
    when(userService.registerUser(any(UserInDto.class))).thenReturn(messageOutDto);

    ResponseEntity<MessageOutDto> newResponse = userController.registerUser(userRequest);

    assertEquals(HttpStatus.CREATED, newResponse.getStatusCode());
    assertEquals(messageOutDto, newResponse.getBody());
  }

  @Test
  void getByUserIdTests() throws Exception {
    int userId = 1;
    when(userService.findUserById(1)).thenReturn(user);

    mockMvc.perform(get("/user/" + userId))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.firstName").value("First"))
      .andExpect(jsonPath("$.lastName").value("Last"))
      .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
      .andExpect(jsonPath("$.userRole").value("RESTAURANT_OWNER"));
  }

  @Test
  void updateWalletBalance() throws Exception {
    int userId = 1;
    BigDecimal amount = BigDecimal.valueOf(100);
    MessageOutDto messageOutDto = new MessageOutDto("Wallet balance updated successfully");

    when(userService.updateWalletBalance(eq(1), any(BigDecimal.class))).thenReturn(messageOutDto);

    mockMvc.perform(put("/user/wallet/" + userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(amount)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Wallet balance updated successfully"));
  }

  @Test
  void sendContactUsEmail() throws Exception {
    when(userService.sendContactUsEmail(any(ContactUsInDto.class))).thenReturn(messageOutDto);

    mockMvc.perform(post("/user/contact-us")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contactUsDto)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Operation successful"));
  }
}
