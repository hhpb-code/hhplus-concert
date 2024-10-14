package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import java.time.LocalDateTime;

public class WaitingQueueControllerDto {

  // NOTE: Request, Response DTO

  public record CreateWaitingQueueTokenRequest(
      Long concertId
  ) {

  }

  public record CreateWaitingQueueTokenResponse(
      WaitingQueueResponse waitingQueue
  ) {

  }

  public record GetWaitingQueueStatusResponse(
      WaitingQueueResponse waitingQueue
  ) {

  }

  // NOTE: Common DTO

  public record WaitingQueueResponse(
      Long id,
      Long concertId,
      String uuid,
      WaitingQueueStatus status,
      LocalDateTime expiredAt,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }

  public record WaitingQueueResponseWithPosition(
      Long id,
      Long concertId,
      String uuid,
      WaitingQueueStatus status,
      Long position,
      LocalDateTime expiredAt,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }


}
