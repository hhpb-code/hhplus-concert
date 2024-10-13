package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.queuetoken.model.QueueTokenStatus;
import com.example.hhplus.concert.domain.queuetoken.model.Queuetoken;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class QueueTokenControllerDto {

  public record CreateQueueTokenRequest(
      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1234")
      Long userId
  ) {

  }

  public record CreateQueueTokenResponse(
      @Schema(description = "생성된 대기열 토큰 정보")
      QueueTokenDto queueToken
  ) {

  }

  public record GetQueueTokenResponse(
      @Schema(description = "대기열 토큰 정보 (대기열 내 위치 포함)")
      QueueTokenDtoWithPosition queueToken
  ) {

  }

  // NOTE: Presentation POJO (DTO) for QueueToken
  @Schema(description = "대기열 토큰 정보")
  public record QueueTokenDto(
      @Schema(description = "대기열 토큰 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1234")
      Long userId,

      @Schema(description = "대기열 토큰 상태", example = "WAITING")
      QueueTokenStatus status,

      @Schema(description = "만료 일시", example = "2024-10-13T14:30:00")
      LocalDateTime expiredAt
  ) {

    public QueueTokenDto(Queuetoken queuetoken) {
      this(queuetoken.id(), queuetoken.concertSeatId(), queuetoken.userId(), queuetoken.status(),
          queuetoken.expiredAt());
    }

  }

  @Schema(description = "대기열 토큰 정보 (대기열 내 위치 포함)")
  public record QueueTokenDtoWithPosition(
      @Schema(description = "대기열 토큰 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1234")
      Long userId,

      @Schema(description = "대기열 토큰 상태", example = "WAITING")
      QueueTokenStatus status,

      @Schema(description = "만료 일시", example = "2024-10-13T14:30:00")
      LocalDateTime expiredAt,

      @Schema(description = "대기열 내 위치", example = "5")
      Integer position
  ) {

    public QueueTokenDtoWithPosition(Queuetoken queuetoken, Integer position) {
      this(queuetoken.id(), queuetoken.concertSeatId(), queuetoken.userId(), queuetoken.status(),
          queuetoken.expiredAt(), position);
    }

  }

}