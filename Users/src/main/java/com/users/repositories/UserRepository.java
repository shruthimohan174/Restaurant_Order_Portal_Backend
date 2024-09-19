package com.users.repositories;

import com.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing User entities.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Finds a user by their email address.
   *
   * @param email the email address of the user to be retrieved.
   * @return an Optional containing the user if found, or empty if not found.
   */
  Optional<User> findByEmail(String email);
}
