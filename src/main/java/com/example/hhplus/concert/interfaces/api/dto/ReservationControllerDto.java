package com.example.hhplus.concert.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class ReservationControllerDto {

  // NOTE: Request, Response DTO

  public record PayReservationResponse(
      PaymentResponse payment
  ) {

  }

  // NOTE: Common DTO

  public record PaymentResponse(
      @Schema(description = "결제 내역 ID", example = "1")
      Long id,

      @Schema(description = "예약 내역 ID", example = "1")
      Long reservationId,

      @Schema(description = "사용자 ID", example = "1")
      Long userId,

      @Schema(description = "결제 금액", example = "10000")
      Long amount,

      @Schema(description = "결제 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

  }
}
