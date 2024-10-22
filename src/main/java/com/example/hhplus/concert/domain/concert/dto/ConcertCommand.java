package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import java.util.List;

public class ConcertCommand {

  public record ReserveConcertSeatCommand(Long concertSeatId) {

    public ReserveConcertSeatCommand {
      if (concertSeatId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_FOUND);
      }
    }
  }

  public record CreateReservationCommand(Long concertSeatId, Long userId) {

    public CreateReservationCommand {
      if (concertSeatId == null) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_FOUND);
      }
      if (userId == null) {
        throw new BusinessException(ConcertErrorCode.RESERVATION_USER_NOT_MATCHED);
      }
    }
  }

  public record ConfirmReservationCommand(Long reservationId) {

    public ConfirmReservationCommand {
      if (reservationId == null) {
        throw new BusinessException(ConcertErrorCode.RESERVATION_NOT_FOUND);
      }
    }
  }

  public record CancelReservationsByIdsCommand(List<Long> reservationIds) {

    public CancelReservationsByIdsCommand {
      if (reservationIds == null || reservationIds.isEmpty()) {
        throw new BusinessException(ConcertErrorCode.RESERVATION_NOT_FOUND);
      }
    }
  }

  public record ReleaseConcertSeatsByIdsCommand(List<Long> concertSeatIds) {

    public ReleaseConcertSeatsByIdsCommand {
      if (concertSeatIds == null || concertSeatIds.isEmpty()) {
        throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_RESERVED);
      }
    }
  }
}
