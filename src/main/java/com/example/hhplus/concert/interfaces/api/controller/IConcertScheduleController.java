package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ConcertScheduleControllerDto.GetAvailableSeatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "ConcertSchedule", description = "콘서트 일정 API")
public interface IConcertScheduleController {

  @Operation(summary = "예약 가능한 좌석 조회", description = "예약 가능한 좌석을 조회합니다.")
  ResponseEntity<GetAvailableSeatsResponse> getAvailableSeats(
      @Schema(description = "콘서트 일정 ID", example = "1")
      Long scheduleId
  );

}
