package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSchedulesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Concert", description = "콘서트 API")
public interface IConcertController {

  @Operation(summary = "예약 가능한 콘서트 일정 조회", description = "예약 가능한 콘서트 일정을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "예약 가능한 콘서트 일정 조회 성공")
  ResponseEntity<GetAvailableSchedulesResponse> getAvailableSchedules(
      @Schema(description = "콘서트 ID", example = "1")
      Long concertId,
      
      @Schema(description = "대기열 토큰 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
      String waitingQueueTokenUuid
  );

}