package com.users.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLoginInDtoTest {

  private UserLoginInDto userLoginRequest;

  @BeforeEach
  void setUp() {
    userLoginRequest = new UserLoginInDto();
  }

  @Test
  void testSettersAndGetters() {
    userLoginRequest.setEmail("shruthi.mohan@example.com");
    userLoginRequest.setPassword("password123");

    assertThat(userLoginRequest.getEmail()).isEqualTo("shruthi.mohan@example.com");
    assertThat(userLoginRequest.getPassword()).isEqualTo("password123");
  }

  @Test
  void testToString() {
    userLoginRequest.setEmail("shruthi.mohan@example.com");
    userLoginRequest.setPassword("password123");

    String actualString = userLoginRequest.toString();
    String expectedString = "UserLoginInDto(email=shruthi.mohan@example.com, password=password123)";
    assertThat(actualString).isEqualTo(expectedString);
  }

  @Test
  void testHashCode() {
    userLoginRequest.setEmail("shruthi.mohan@example.com");
    userLoginRequest.setPassword("password123");

    int expectedHashCode = userLoginRequest.hashCode();
    assertThat(expectedHashCode).isEqualTo(userLoginRequest.hashCode());
  }

  @Test
  void testEquals() {
    UserLoginInDto request1 = new UserLoginInDto("shruthi.mohan@example.com", "password123");
    UserLoginInDto request2 = new UserLoginInDto("shruthi.mohan@example.com", "password123");

    assertThat(request1).isEqualTo(request2);

  }
}
