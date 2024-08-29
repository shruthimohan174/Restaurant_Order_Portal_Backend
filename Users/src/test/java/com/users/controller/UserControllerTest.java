package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.dto.indto.UpdateUserInDto;
import com.users.dto.indto.UserInDto;
import com.users.dto.indto.UserLoginInDto;
import com.users.dto.outdto.UpdateUserOutDto;
import com.users.dto.outdto.UserOutDto;
import com.users.entities.User;
import com.users.entities.UserRole;
import com.users.service.UserService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private UserInDto userRequest;
  private UserLoginInDto userLoginRequest;
  private UserOutDto userResponse;
  private User user;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    objectMapper = new ObjectMapper();

    userRequest = new UserInDto();
    userRequest.setFirstName("Shruthi");
    userRequest.setLastName("Mohan");
    userRequest.setEmail("shruthimohan1708@gmail.com");
    userRequest.setPassword("Pass123!");
    userRequest.setPhoneNumber("8434972888");
    userRequest.setUserRole(UserRole.RESTAURANT_OWNER);

    userLoginRequest = new UserLoginInDto();
    userLoginRequest.setEmail("shruthimohan1708@gmail.com");
    userLoginRequest.setPassword("Pass123!");

    userResponse = new UserOutDto(1, "Shruthi", "Mohan", "shruthimohan1708@gmail.com",
      "8434972888", UserRole.RESTAURANT_OWNER, null);

    user = new User();
    user.setId(1);
    user.setFirstName("Shruthi");
    user.setLastName("Mohan");
    user.setEmail("shruthimohan1708@gmail.com");
    user.setPhoneNumber("8434972888");
    user.setUserRole(UserRole.RESTAURANT_OWNER);
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
      .andExpect(jsonPath("$.firstName").value("Shruthi"))
      .andExpect(jsonPath("$.lastName").value("Mohan"))
      .andExpect(jsonPath("$.email").value("shruthimohan1708@gmail.com"))
      .andExpect(jsonPath("$.phoneNumber").value("8434972888"))
      .andExpect(jsonPath("$.userRole").value("RESTAURANT_OWNER"));
  }

  @Test
  void getAllUsersTests() throws Exception {
    when(userService.getAllUsers()).thenReturn(Collections.singletonList(userResponse));

    mockMvc.perform(get("/user"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].firstName").value("Shruthi"))
      .andExpect(jsonPath("$[0].lastName").value("Mohan"))
      .andExpect(jsonPath("$[0].email").value("shruthimohan1708@gmail.com"))
      .andExpect(jsonPath("$[0].phoneNumber").value("8434972888"))
      .andExpect(jsonPath("$[0].userRole").value("RESTAURANT_OWNER"));
  }

  @Test
  void registerUser() throws Exception {
    when(userService.registerUser(any(UserInDto.class))).thenReturn(userResponse);

    ResponseEntity<UserOutDto> response = userController.registerUser(userRequest);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
  }

  @Test
  void updateUserTests() throws Exception {
    UpdateUserInDto updateUserInDto = new UpdateUserInDto();
    updateUserInDto.setFirstName("Shruthi");
    updateUserInDto.setLastName("Mohan");
    updateUserInDto.setPhoneNumber("8434972888");

    UpdateUserOutDto updateUserResponse = new UpdateUserOutDto(1, "Shruthi", "Mohan",
      "8434972888");

    when(userService.updateUser(eq(1), any(UpdateUserInDto.class))).thenReturn(updateUserResponse);

    mockMvc.perform(put("/user/update/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateUserInDto)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.firstName").value("Shruthi"))
      .andExpect(jsonPath("$.lastName").value("Mohan"))
      .andExpect(jsonPath("$.phoneNumber").value("8434972888"));
  }

  @Test
  void deleteUserTests() throws Exception {
    doNothing().when(userService).deleteUser(1);

    mockMvc.perform(delete("/user/delete/{id}", 1))
      .andExpect(status().isNoContent());
  }

  @Test
  void getByUserIdTests() throws Exception {
    when(userService.findUserById(1)).thenReturn(user);

    mockMvc.perform(get("/user/{id}", 1))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.firstName").value("Shruthi"))
      .andExpect(jsonPath("$.lastName").value("Mohan"))
      .andExpect(jsonPath("$.email").value("shruthimohan1708@gmail.com"))
      .andExpect(jsonPath("$.phoneNumber").value("8434972888"))
      .andExpect(jsonPath("$.userRole").value("RESTAURANT_OWNER"));
  }

  @Test
  void updateWalletBalance() throws Exception {
    BigDecimal amount = BigDecimal.valueOf(100);
    UserOutDto updatedUserResponse = new UserOutDto(1, "Shruthi", "Mohan",
      "shruthimohan1708@gmail.com", "8434972888", UserRole.RESTAURANT_OWNER, BigDecimal.valueOf(900.00));

    when(userService.updateWalletBalance(eq(1), any(BigDecimal.class))).thenReturn(updatedUserResponse);

    mockMvc.perform(put("/user/wallet/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(amount)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.walletBalance").value(900.00));
  }
}
