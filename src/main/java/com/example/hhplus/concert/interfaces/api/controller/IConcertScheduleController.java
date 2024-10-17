package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSeatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "ConcertSchedule", description = "콘서트 일정 API")
public interface IConcertScheduleController {

  @Operation(summary = "예약 가능한 좌석 조회", description = "예약 가능한 좌석을 조회합니다.")
  ResponseEntity<GetAvailableSeatsResponse> getAvailableSeats(
      @Schema(description = "대기열 토큰 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
      String waitingQueueTokenUuid,
      
      @Schema(description = "콘서트 일정 ID", example = "1")
      Long scheduleId);

}
