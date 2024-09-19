package com.restaurants.service;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignConfiguration {
  /**
   * Provides a request interceptor for logging Feign client requests.
   *
   * @return an instance of {@link RequestInterceptor} that logs request details
   */
  @Bean
  public RequestInterceptor requestInterceptor() {
    return new RequestInterceptor() {
      @Override
      public void apply(final RequestTemplate template) {
        log.info("Feign Request to URL: {}", template.url());
      }
    };
  }
}
