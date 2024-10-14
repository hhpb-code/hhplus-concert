package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ConcertSeatResponse;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSeatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ConcertSchedule", description = "콘서트 일정 API")
@RestController
@RequestMapping("/api/v1/concert-schedules")
public class ConcertScheduleController {

  @GetMapping("{scheduleId}/available-seats")
  @Operation(summary = "예약 가능한 좌석 조회", description = "예약 가능한 좌석을 조회합니다.")
  public ResponseEntity<GetAvailableSeatsResponse> getAvailableSeats(
      @PathVariable Long scheduleId) {
    List<ConcertSeatResponse> concertSeats = List.of(
        new ConcertSeatResponse(1L, scheduleId, 1, 100, false, LocalDateTime.now(),
            null));

    return ResponseEntity.ok(new GetAvailableSeatsResponse(concertSeats));
  }
}
