package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;

public class ConcertRepositoryParam {

  public record GetConcertByIdParam(Long id) {

  }

  public record GetConcertByIdWithLockParam(Long id) {

  }

  public record GetConcertScheduleByIdParam(Long concertScheduleId) {

  }

  public record FindAllConcertSchedulesByConcertIdAndNowParam(
      Long concertId,
      LocalDateTime now
  ) {

  }


  public record FindAllConcertSeatsByConcertIdAndIsReservedParam(
      Long concertScheduleId,
      Boolean isReserved
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

  public record FindAllReservationsByIdsWithLockParam(
      List<Long> reservationIds
  ) {

  }

  public record FindAllConcertSeatsByIdsWithLockParam(
      List<Long> concertSeatIds
  ) {

  }

  public record FindAllReservationsByStatusAndReservedAtBeforeWithLockParam(
      ReservationStatus status,
      LocalDateTime expiredAt
  ) {

  }

  public record FindAllConcertSchedulesByReservationStartAtBetweenParam(
      LocalDateTime startAt,
      LocalDateTime endAt
  ) {

  }
}
