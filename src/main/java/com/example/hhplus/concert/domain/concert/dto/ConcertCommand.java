package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.util.List;

public class ConcertCommand {

  public record ReserveConcertSeatCommand(Long concertSeatId) {

    public ReserveConcertSeatCommand {
      if (concertSeatId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record CreateReservationCommand(Long concertSeatId, Long userId) {

    public CreateReservationCommand {
      if (concertSeatId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
      }
      if (userId == null) {
        throw new CoreException(ErrorType.User.USER_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record ConfirmReservationCommand(Long reservationId) {

    public ConfirmReservationCommand {
      if (reservationId == null) {
        throw new CoreException(ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record CancelReservationsByIdsCommand(List<Long> reservationIds) {

    public CancelReservationsByIdsCommand {
      if (reservationIds == null || reservationIds.isEmpty()) {
        throw new CoreException(ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record ReleaseConcertSeatsByIdsCommand(List<Long> concertSeatIds) {

    public ReleaseConcertSeatsByIdsCommand {
      if (concertSeatIds == null || concertSeatIds.isEmpty()) {
        throw new CoreException(ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
      }
    }
  }
}
