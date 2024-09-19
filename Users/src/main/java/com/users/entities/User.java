package com.users.entities;

import com.users.utils.UserRole;
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
 * This entity maps to the 'users' table in the database and holds user-related information,
 * including personal details, authentication credentials, and wallet balance.
 * </p>
 */
@Data
@Entity
@Table(name = "users")
public class User {

  /**
   * Unique identifier for the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * The first name of the user.
   */
  private String firstName;

  /**
   * The last name of the user.
   */
  private String lastName;

  /**
   * The email address of the user, used as a unique identifier for authentication.
   */
  private String email;

  /**
   * The encrypted password for the user.
   */
  private String password;

  /**
   * The phone number of the user.
   */
  private String phoneNumber;

  /**
   * The balance of the user's wallet.
   */
  private BigDecimal walletBalance;

  /**
   * The role of the user, indicating their permissions and access level within the system.
   */
  @Enumerated(EnumType.STRING)
  private UserRole userRole;
}
