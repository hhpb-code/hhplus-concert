package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ConcertCommand {

  public record ReserveConcertSeatCommand(
      @NotNull(message = ConcertConstants.CONCERT_SEAT_ID_NOT_NULL)
      Long concertSeatId,

      @NotNull(message = ConcertConstants.USER_ID_NOT_NULL)
      Long userId
  ) {

  }

  public record CreateReservationCommand(
      @NotNull(message = ConcertConstants.CONCERT_SEAT_ID_NOT_NULL)
      Long concertSeatId,

      @NotNull(message = ConcertConstants.USER_ID_NOT_NULL)
      Long userId
  ) {

  }

  public record ConfirmReservationCommand(
      @NotNull(message = ConcertConstants.RESERVATION_ID_MUST_NOT_BE_NULL)
      Long reservationId
  ) {

  }

  public record CancelReservationsByIdsCommand(
      @NotEmpty(message = ConcertConstants.RESERVATION_IDS_MUST_NOT_BE_NULL)
      List<Long> reservationIds
  ) {

  }

  public record ReleaseConcertSeatsByIdsCommand(
      @NotEmpty(message = ConcertConstants.CONCERT_SEAT_IDS_MUST_NOT_BE_NULL)
      List<Long> concertSeatIds
  ) {

  }

}
