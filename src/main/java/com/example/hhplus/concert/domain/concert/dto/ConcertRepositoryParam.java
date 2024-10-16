package com.example.hhplus.concert.domain.concert.dto;

import java.time.LocalDateTime;

public class ConcertRepositoryParam {

  public record GetConcertByIdParam(Long id) {

  }

  public record FindAvailableConcertSchedulesByConcertIdAndNowParam(
      Long concertId,
      LocalDateTime now
  ) {

  }

  public record GetReservationByIdParam(Long id) {

  }

  public record GetConcertSeatByIdWithLockParam(Long concertSeatId) {

  }

}
