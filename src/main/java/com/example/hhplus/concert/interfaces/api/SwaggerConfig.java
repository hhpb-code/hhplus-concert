package com.example.hhplus.concert.interfaces.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .addSecurityItem(
            new SecurityRequirement().addList(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID))
        .components(new Components()
            .addSecuritySchemes(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID,
                createQueueTokenScheme()));
  }


  private SecurityScheme createQueueTokenScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .in(SecurityScheme.In.HEADER)
        .name(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID);
  }
}
