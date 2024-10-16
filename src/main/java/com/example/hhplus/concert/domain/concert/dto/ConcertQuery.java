package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import jakarta.validation.constraints.NotNull;

public class ConcertQuery {

  public record GetConcertByIdQuery(
      @NotNull(message = ConcertConstants.CONCERT_ID_MUST_NOT_BE_NULL)
      Long id
  ) {

  }

  public record GetConcertScheduleByIdQuery(
      @NotNull(message = ConcertConstants.CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL)
      Long concertScheduleId
  ) {

  }

  public record FindReservableConcertSchedulesQuery(
      @NotNull(message = ConcertConstants.CONCERT_ID_MUST_NOT_BE_NULL)
      Long concertId
  ) {

  }

  public record GetConcertSeatByIdWithLockQuery(
      @NotNull(message = ConcertConstants.CONCERT_SEAT_ID_MUST_NOT_BE_NULL)
      Long concertSeatId
  ) {

  }


  public record FindReservableConcertSeatsQuery(
      @NotNull(message = ConcertConstants.CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL)
      Long concertScheduleId
  ) {

  }

  public record GetReservationByIdQuery(
      @NotNull(message = ConcertConstants.RESERVATION_ID_MUST_NOT_BE_NULL)
      Long reservationId
  ) {

  }

}
