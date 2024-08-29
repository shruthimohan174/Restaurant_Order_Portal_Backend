package com.users.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserInDtoTest {

  private UpdateUserInDto updateUserInDto;

  @BeforeEach
  void setUp() {
    updateUserInDto = new UpdateUserInDto();
  }

  @Test
  void testSettersAndGetters() {
    updateUserInDto.setFirstName("Shruthi");
    updateUserInDto.setLastName("Mohan");
    updateUserInDto.setPhoneNumber("8434972888");

    assertThat(updateUserInDto.getFirstName()).isEqualTo("Shruthi");
    assertThat(updateUserInDto.getLastName()).isEqualTo("Mohan");
    assertThat(updateUserInDto.getPhoneNumber()).isEqualTo("8434972888");
  }

  @Test
  void testToString() {
    updateUserInDto.setFirstName("Shruthi");
    updateUserInDto.setLastName("Mohan");
    updateUserInDto.setPhoneNumber("8434972888");

    String actualString = updateUserInDto.toString();
    String expectedString = "UpdateUserInDto(firstName=Shruthi, lastName=Mohan, phoneNumber=8434972888)";
    assertThat(actualString).isEqualTo(expectedString);
  }

  @Test
  void testHashCode() {
    updateUserInDto.setFirstName("Shruthi");
    updateUserInDto.setLastName("Mohan");
    updateUserInDto.setPhoneNumber("8434972888");

    int expectedHashCode = updateUserInDto.hashCode();
    assertThat(expectedHashCode).isEqualTo(updateUserInDto.hashCode());
  }

  @Test
  void testEquals() {
    UpdateUserInDto request1 = new UpdateUserInDto();
    request1.setFirstName("Shruthi");
    request1.setLastName("Mohan");
    request1.setPhoneNumber("8434972888");

    UpdateUserInDto request2 = new UpdateUserInDto();
    request2.setFirstName("Shruthi");
    request2.setLastName("Mohan");
    request2.setPhoneNumber("8434972888");

    assertThat(request1).isEqualTo(request2);

  }
}
