package com.example.hhplus.concert.domain.concert.dto;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import jakarta.validation.constraints.NotNull;

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

}
