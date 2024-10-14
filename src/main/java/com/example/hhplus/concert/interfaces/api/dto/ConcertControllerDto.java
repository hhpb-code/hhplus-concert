package com.example.hhplus.concert.interfaces.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertControllerDto {

  // NOTE: Request, Response DTO

  public record GetAvailableSchedulesResponse(
      List<ConcertScheduleResponse> concertSchedules
  ) {

  }

  public record GetAvailableSeatsResponse(
      List<ConcertSeatResponse> concertSeats
  ) {

  }

  // NOTE: Common DTO

  public record ConcertScheduleResponse(
      Long id,
      Long concertId,
      LocalDateTime concertAt,
      LocalDateTime reservationStartAt,
      LocalDateTime reservationEndAt,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }

  public record ConcertSeatResponse(
      Long id,
      Long concertScheduleId,
      Integer number,
      Integer price,
      Boolean isReserved,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }


}
