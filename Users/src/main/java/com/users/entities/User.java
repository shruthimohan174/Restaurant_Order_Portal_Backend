package com.users.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Entity representing a user.
 * <p>
 * This entity maps to the 'users' table in the database and holds user-related information.
 * </p>
 */
@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private String phoneNumber;

  private BigDecimal walletBalance;

  @Enumerated(EnumType.STRING)
  private UserRole userRole;
}
