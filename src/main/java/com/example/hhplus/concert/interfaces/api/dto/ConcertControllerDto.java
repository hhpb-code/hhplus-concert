package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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

  public record ReserveSeatResponse(
      ReservationResponse reservation
  ) {

  }

  // NOTE: Common DTO

  public record ConcertScheduleResponse(
      @Schema(description = "콘서트 일정 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 ID", example = "1")
      Long concertId,

      @Schema(description = "콘서트 일정", example = "2024-12-31T23:59:59")
      LocalDateTime concertAt,

      @Schema(description = "예약 시작 일시", example = "2024-12-31T23:59:59")
      LocalDateTime reservationStartAt,

      @Schema(description = "예약 종료 일시", example = "2024-12-31T23:59:59")
      LocalDateTime reservationEndAt,

      @Schema(description = "생성 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

    public ConcertScheduleResponse(ConcertSchedule concertSchedule) {
      this(concertSchedule.getId(), concertSchedule.getConcertId(), concertSchedule.getConcertAt(),
          concertSchedule.getReservationStartAt(), concertSchedule.getReservationEndAt(),
          concertSchedule.getCreatedAt(), concertSchedule.getUpdatedAt());
    }

  }

  public record ConcertSeatResponse(
      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 일정 ID", example = "1")
      Long concertScheduleId,

      @Schema(description = "좌석 번호", example = "1")
      Integer number,

      @Schema(description = "좌석 가격", example = "10000")
      Integer price,

      @Schema(description = "예약 여부", example = "false")
      Boolean isReserved,

      @Schema(description = "생성 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

    public ConcertSeatResponse(ConcertSeat concertSeat) {
      this(concertSeat.getId(), concertSeat.getConcertScheduleId(), concertSeat.getNumber(),
          concertSeat.getPrice(), concertSeat.getIsReserved(), concertSeat.getCreatedAt(),
          concertSeat.getUpdatedAt());
    }

  }

  public record ReservationResponse(
      @Schema(description = "예약 내역 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1")
      Long userId,

      @Schema(description = "예약 상태", example = "RESERVED")
      ReservationStatus status,

      @Schema(description = "예약 일시", example = "2024-12-31T23:59:59")
      LocalDateTime reservedAt,

      @Schema(description = "생성 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

  }

}
