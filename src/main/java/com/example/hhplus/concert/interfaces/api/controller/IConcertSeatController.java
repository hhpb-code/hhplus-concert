package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ReserveSeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "ConcertSeat", description = "콘서트 좌석 API")
public interface IConcertSeatController {

  @Operation(summary = "좌석 예약 (임시 예약)", description = "좌석을 예약합니다.")
  @ApiResponse(responseCode = "201", description = "좌석 예약 성공")
  ResponseEntity<ReserveSeatResponse> reserveSeat(
      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long concertSeatId,

      @Schema(description = "사용자 ID", example = "1")
      Long userId,
      
      @Schema(description = "대기열 토큰 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
      String waitingQueueTokenUuid
  );

}
