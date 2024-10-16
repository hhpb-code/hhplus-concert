package com.example.hhplus.concert.domain.concert.dto;

import java.time.LocalDateTime;

public class ConcertRepositoryParam {

  public record GetConcertByIdParam(Long id) {

  }

  public record GetConcertScheduleByIdParam(Long concertScheduleId) {

  }

  public record FindReservableConcertSchedulesByConcertIdAndNowParam(
      Long concertId,
      LocalDateTime now
  ) {

  }


  public record FindReservableConcertSeatsByConcertIdParam(
      Long concertScheduleId
  ) {

  }

  public record GetConcertSeatByIdParam(Long concertSeatId) {

  }

  public record GetConcertSeatByIdWithLockParam(Long concertSeatId) {

  }

  public record GetReservationByIdParam(Long reservationId) {

  }

  public record GetReservationByIdWithLockParam(Long reservationId) {

  }

}
