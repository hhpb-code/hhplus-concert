package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class ConcertQuery {

  public record GetConcertByIdQuery(Long id) {

    public GetConcertByIdQuery {
      if (id == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetConcertByIdWithLockQuery(Long id) {

    public GetConcertByIdWithLockQuery {
      if (id == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetConcertScheduleByIdQuery(Long concertScheduleId) {

    public GetConcertScheduleByIdQuery {
      if (concertScheduleId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record FindReservableConcertSchedulesQuery(Long concertId) {

    public FindReservableConcertSchedulesQuery {
      if (concertId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetConcertSeatByIdQuery(Long concertSeatId) {

    public GetConcertSeatByIdQuery {
      if (concertSeatId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetConcertSeatByIdWithLockQuery(Long concertSeatId) {

    public GetConcertSeatByIdWithLockQuery {
      if (concertSeatId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record FindReservableConcertSeatsQuery(Long concertScheduleId) {

    public FindReservableConcertSeatsQuery {
      if (concertScheduleId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetReservationByIdQuery(Long reservationId) {

    public GetReservationByIdQuery {
      if (reservationId == null) {
        throw new CoreException(ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetReservationByIdWithLockQuery(Long reservationId) {

    public GetReservationByIdWithLockQuery {
      if (reservationId == null) {
        throw new CoreException(ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record FindAllExpiredReservationsWithLockQuery() {
    // 이 경우에는 특별한 필드가 없으므로 유효성 검사는 불필요
  }

  public record FindUpcomingConcertsQuery(
      int minutesBeforeReservationStartAt) {

    public FindUpcomingConcertsQuery {
      if (minutesBeforeReservationStartAt <= 0) {
        throw new CoreException(ErrorType.Concert.INVALID_MINUTES_BEFORE_RESERVATION_START_AT);
      }
    }

  }

}
