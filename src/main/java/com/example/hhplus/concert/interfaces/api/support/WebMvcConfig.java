package com.example.hhplus.concert.interfaces.api.support;

import com.example.hhplus.concert.interfaces.api.interceptor.WaitingQueueValidationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final WaitingQueueValidationInterceptor waitingQueueValidationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(waitingQueueValidationInterceptor)
        .addPathPatterns(
            "/api/v1/concerts/**",
            "/api/v1/concert-seats/**",
            "/api/v1/concert-schedules/**"
        );
  }
}
