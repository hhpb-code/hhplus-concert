package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import io.swagger.v3.oas.annotations.media.Schema;

public class WaitingQueueControllerDto {

  // NOTE: Request, Response DTO

  public record CreateWaitingQueueTokenResponse(
      WaitingQueueResponse waitingQueue
  ) {

  }

  public record GetWaitingQueuePositionResponse(
      WaitingQueueResponseWithPosition waitingQueue
  ) {

  }

  // NOTE: Common DTO

  public record WaitingQueueResponse(
      @Schema(description = "대기열 UUID", example = "1")
      String uuid

  ) {

    public WaitingQueueResponse(WaitingQueue waitingQueueToken) {
      this(waitingQueueToken.getUuid());
    }
  }

  public record WaitingQueueResponseWithPosition(

      @Schema(description = "대기열 UUID", example = "1")
      String uuid,

      @Schema(description = "대기열 순번", example = "1")
      Long position

  ) {

    public WaitingQueueResponseWithPosition(WaitingQueueWithPosition waitingQueueWithPosition) {
      this(
          waitingQueueWithPosition.uuid(),
          waitingQueueWithPosition.position()
      );
    }
  }


}
