package com.users.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1);
    user.setFirstName("Shruthi");
    user.setLastName("Mohan");
    user.setEmail("shruthimohan1708@gmail.com");
    user.setPassword("Password@123");
    user.setPhoneNumber("8434972888");
    user.setWalletBalance(new BigDecimal("1000.00"));
    user.setUserRole(UserRole.CUSTOMER);
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, user.getId());
    assertEquals("Shruthi", user.getFirstName());
    assertEquals("Mohan", user.getLastName());
    assertEquals("shruthimohan1708@gmail.com", user.getEmail());
    assertEquals("Password@123", user.getPassword());
    assertEquals("8434972888", user.getPhoneNumber());
    assertEquals(new BigDecimal("1000.00"), user.getWalletBalance());
    assertEquals(UserRole.CUSTOMER, user.getUserRole());
  }

  @Test
  void testToString() {
    String expected =
      "User(id=1, firstName=Shruthi, lastName=Mohan, email=shruthimohan1708@gmail.com, password=Password@123," +
        " phoneNumber=8434972888, walletBalance=1000.00, userRole=CUSTOMER)";
    assertEquals(expected, user.toString());
  }

  @Test
  void testEquals() {
    User anotherUser = new User();
    anotherUser.setId(1);
    anotherUser.setFirstName("Shruthi");
    anotherUser.setLastName("Mohan");
    anotherUser.setEmail("shruthimohan1708@gmail.com");
    anotherUser.setPassword("Password@123");
    anotherUser.setPhoneNumber("8434972888");
    anotherUser.setWalletBalance(new BigDecimal("1000.00"));
    anotherUser.setUserRole(UserRole.CUSTOMER);

    assertEquals(user, anotherUser);
  }

  @Test
  void testHashCode() {
    User anotherUser = new User();
    anotherUser.setId(1);
    anotherUser.setFirstName("Shruthi");
    anotherUser.setLastName("Mohan");
    anotherUser.setEmail("shruthimohan1708@gmail.com");
    anotherUser.setPassword("Password@123");
    anotherUser.setPhoneNumber("8434972888");
    anotherUser.setWalletBalance(new BigDecimal("1000.00"));
    anotherUser.setUserRole(UserRole.CUSTOMER);

    assertEquals(user.hashCode(), anotherUser.hashCode());
  }
}
