package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import java.time.LocalDateTime;

public class ReservationControllerDto {

  // NOTE: Request, Response DTO

  public record CreateReservationRequest(
      Long concertSeatId
  ) {

  }

  public record CreateReservationResponse(
      ReservationResponse reservation
  ) {

  }

  public record PayReservationResponse(
      PaymentResponse payment
  ) {

  }

  // NOTE: Common DTO

  public record ReservationResponse(
      Long id,
      Long concertSeatId,
      Long userId,
      ReservationStatus status,
      LocalDateTime reservationAt,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }

  public record PaymentResponse(
      Long id,
      Long reservationId,
      Long userId,
      Long amount,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }
}
