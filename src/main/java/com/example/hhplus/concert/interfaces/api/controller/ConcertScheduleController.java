package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.interfaces.api.dto.ConcertScheduleControllerDto.GetAvailableSeatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    List<ConcertSeat> concertSeats = List.of(new ConcertSeat(1L, scheduleId, 1L, 100L, false));

    return ResponseEntity.ok(new GetAvailableSeatsResponse(concertSeats));
  }
}
