package com.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point of the Restaurants application.
 * This class contains the main method which is used to launch the Spring Boot application.
 * It also enables Feign clients for inter-service communication.
 */
@SpringBootApplication
@EnableFeignClients
@SuppressWarnings("PMD.UseUtilityClass")
public class RestaurantsApplication {

  /**
   * The main method that serves as the entry point for the Spring Boot application.
   * It initializes the application context and starts the embedded server.
   *
   * @param args command-line arguments passed to the application.
   */
  public static void main(final String[] args) {
    SpringApplication.run(RestaurantsApplication.class, args);
  }

}
