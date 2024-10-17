package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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

  public record GetWaitingQueuePositionResponse(
      WaitingQueueResponseWithPosition waitingQueue
  ) {

  }

  // NOTE: Common DTO

  public record WaitingQueueResponse(
      @Schema(description = "대기열 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 ID", example = "1")
      Long concertId,

      @Schema(description = "대기열 UUID", example = "1")
      String uuid,

      @Schema(description = "대기열 상태", example = "WAITING")
      WaitingQueueStatus status,

      @Schema(description = "만료 일시", example = "2024-12-31T23:59:59")
      LocalDateTime expiredAt,

      @Schema(description = "생성 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

    public WaitingQueueResponse(WaitingQueue waitingQueueToken) {
      this(waitingQueueToken.getId(), waitingQueueToken.getConcertId(),
          waitingQueueToken.getUuid(), waitingQueueToken.getStatus(),
          waitingQueueToken.getExpiredAt(), waitingQueueToken.getCreatedAt(),
          waitingQueueToken.getUpdatedAt());
    }
  }

  public record WaitingQueueResponseWithPosition(
      @Schema(description = "대기열 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 ID", example = "1")
      Long concertId,

      @Schema(description = "대기열 UUID", example = "1")
      String uuid,

      @Schema(description = "대기열 상태", example = "WAITING")
      WaitingQueueStatus status,

      @Schema(description = "대기열 순번", example = "1")
      Integer position,

      @Schema(description = "만료 일시", example = "2024-12-31T23:59:59")
      LocalDateTime expiredAt,

      @Schema(description = "생성 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

  }


}
