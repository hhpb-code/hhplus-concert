package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.Payment;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class ReservationControllerDto {

  public record CreateReservationRequest(
      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1234")
      Long userId
  ) {

  }

  public record CreateReservationResponse(
      @Schema(description = "생성된 예약 정보")
      ReservationDto reservation
  ) {

  }

  public record PayReservationRequest(
      @Schema(description = "사용자 ID", example = "1234")
      Long userId
  ) {

  }

  public record PayReservationResponse(
      @Schema(description = "생성된 결제 정보")
      PaymentDto payment
  ) {

  }

  // NOTE: Presentation POJO (DTO) for Reservation
  @Schema(description = "예약 정보")
  public record ReservationDto(
      @Schema(description = "예약 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1234")
      Long userId,

      @Schema(description = "예약 상태", example = "RESERVED")
      ReservationStatus status,

      @Schema(description = "예약 일시", example = "2024-10-13T14:30:00")
      LocalDateTime reservedAt
  ) {

    public ReservationDto(Reservation reservation) {
      this(reservation.id(), reservation.concertSeatId(), reservation.userId(),
          reservation.status(),
          reservation.reservedAt());
    }
  }

  // NOTE: Presentation POJO (DTO) for Payment
  @Schema(description = "결제 정보")
  public record PaymentDto(
      @Schema(description = "결제 ID", example = "1")
      Long id,

      @Schema(description = "예약 ID", example = "1")
      Long reservationId,

      @Schema(description = "사용자 ID", example = "1234")
      Long userId,

      @Schema(description = "결제 금액", example = "100")
      Long amount
  ) {

    public PaymentDto(Payment payment) {
      this(payment.id(), payment.reservationId(), payment.userId(), payment.amount());
    }
  }

}
