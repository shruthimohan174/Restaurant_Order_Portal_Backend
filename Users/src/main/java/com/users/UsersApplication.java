package com.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Users application.
 * <p>
 * This class contains the {@code main} method, which is the starting point of the Spring Boot application.
 * It triggers the application context to be initialized and the application to start.
 * </p>
 */
@SpringBootApplication
@SuppressWarnings("PMD.UseUtilityClass")
public class UsersApplication {

  /**
   * The main method which serves as the entry point for the Spring Boot application.
   * <p>
   * This method uses {@link SpringApplication#run(Class, String[])} to launch the application.
   * </p>
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(final String[] args) {
    SpringApplication.run(UsersApplication.class, args);
  }
}
