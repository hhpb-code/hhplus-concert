package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.queuetoken.model.Queuetoken;

public class QueueTokenControllerDto {

  public record CreateQueueTokenRequestBody(
      Long userId
  ) {

  }

  public record CreateQueueTokenResponse(
      Queuetoken queueToken
  ) {

  }

}
