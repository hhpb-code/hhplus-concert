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

  public record GetReservationByIdParam(Long id) {

  }

  public record FindReservableConcertSeatsByConcertIdParam(
      Long concertScheduleId
  ) {

  }

  public record GetConcertSeatByIdWithLockParam(Long concertSeatId) {

  }

}
