package com.restaurants.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) settings.
 * <p>
 * This class configures the CORS policy to allow requests from specific origins and
 * methods for all endpoints in the application.
 * </p>
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  /**
   * Configures the CORS mappings.
   * <p>
   * Allows all HTTP methods and headers, and specifies that only requests from
   * "http://localhost:3000" are permitted.
   * </p>
   *
   * @param registry the {@link CorsRegistry} to which CORS mappings are added
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:3000")
      .allowedMethods("*")
      .allowedHeaders("*");
  }
}
