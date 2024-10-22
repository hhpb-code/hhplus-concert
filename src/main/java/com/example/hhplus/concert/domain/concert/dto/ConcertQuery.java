package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;

public class ConcertQuery {

  public record GetConcertByIdQuery(Long id) {

    public GetConcertByIdQuery {
      if (id == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_NOT_FOUND);
      }
    }
  }

  public record GetConcertByIdWithLockQuery(Long id) {

    public GetConcertByIdWithLockQuery {
      if (id == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_NOT_FOUND);
      }
    }
  }

  public record GetConcertScheduleByIdQuery(Long concertScheduleId) {

    public GetConcertScheduleByIdQuery {
      if (concertScheduleId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
      }
    }
  }

  public record FindReservableConcertSchedulesQuery(Long concertId) {

    public FindReservableConcertSchedulesQuery {
      if (concertId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_NOT_FOUND);
      }
    }
  }

  public record GetConcertSeatByIdQuery(Long concertSeatId) {

    public GetConcertSeatByIdQuery {
      if (concertSeatId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_FOUND);
      }
    }
  }

  public record GetConcertSeatByIdWithLockQuery(Long concertSeatId) {

    public GetConcertSeatByIdWithLockQuery {
      if (concertSeatId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_FOUND);
      }
    }
  }

  public record FindReservableConcertSeatsQuery(Long concertScheduleId) {

    public FindReservableConcertSeatsQuery {
      if (concertScheduleId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
      }
    }
  }

  public record GetReservationByIdQuery(Long reservationId) {

    public GetReservationByIdQuery {
      if (reservationId == null) {
        throw new BusinessException(ConcertErrorCode.RESERVATION_NOT_FOUND);
      }
    }
  }

  public record GetReservationByIdWithLockQuery(Long reservationId) {

    public GetReservationByIdWithLockQuery {
      if (reservationId == null) {
        throw new BusinessException(ConcertErrorCode.RESERVATION_NOT_FOUND);
      }
    }
  }

  public record FindAllExpiredReservationsWithLockQuery() {
    // 이 경우에는 특별한 필드가 없으므로 유효성 검사는 불필요
  }
}
