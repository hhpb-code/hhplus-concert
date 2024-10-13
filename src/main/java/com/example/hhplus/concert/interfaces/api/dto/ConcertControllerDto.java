package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public class ConcertControllerDto {

  @Schema(description = "사용 가능한 콘서트 일정 응답")
  public record GetAvailableSchedulesResponse(
      @Schema(description = "콘서트 일정 목록")
      List<ConcertScheduleDto> concertSchedules
  ) {

  }


  // NOTE: Presentation POJO (DTO) for ConcertSchedule
  @Schema(description = "콘서트 일정 정보")
  public record ConcertScheduleDto(
      @Schema(description = "콘서트 일정 ID", example = "2")
      Long id,

      @Schema(description = "콘서트 ID", example = "1")
      Long concertId,

      @Schema(description = "콘서트 시작 일시", example = "2024-10-13T14:00:00")
      LocalDateTime concertAt,

      @Schema(description = "예약 시작 일시", example = "2024-10-01T14:00:00")
      LocalDateTime reservationStartAt,

      @Schema(description = "예약 종료 일시", example = "2024-10-12T14:00:00")
      LocalDateTime reservationEndAt
  ) {

    public ConcertScheduleDto(ConcertSchedule concertSchedule) {
      this(concertSchedule.id(), concertSchedule.concertId(), concertSchedule.concertAt(),
          concertSchedule.reservationStartAt(), concertSchedule.reservationEndAt());
    }
  }
}