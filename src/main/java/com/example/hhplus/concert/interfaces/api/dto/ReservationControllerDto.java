package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.Payment;
import com.example.hhplus.concert.domain.concert.model.Reservation;

public class ReservationControllerDto {

  public record CreateReservationRequest(
      Long concertSeatId,
      Long userId
  ) {

  }

  public record CreateReservationResponse(
      Reservation reservation
  ) {

  }

  public record PayReservationRequest(
      Long userId
  ) {

  }

  public record PayReservationResponse(
      Payment payment
  ) {

  }

}
