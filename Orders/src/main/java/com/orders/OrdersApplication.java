package com.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point of the Orders application.
 * This class contains the main method which is used to launch the Spring Boot application.
 */
@SpringBootApplication
@EnableFeignClients
public class OrdersApplication {

  /**
   * The main method to start the Spring Boot application.
   * It delegates to Spring Boot's {@link SpringApplication#run(Class, String...)} method.
   *
   * @param args command-line arguments passed during application startup
   */
  public static void main(final String[] args) {
    SpringApplication.run(OrdersApplication.class, args);
  }
}
