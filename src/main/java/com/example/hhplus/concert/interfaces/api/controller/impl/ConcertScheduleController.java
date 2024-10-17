package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.controller.IConcertScheduleController;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ConcertSeatResponse;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSeatsResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concert-schedules")
public class ConcertScheduleController implements IConcertScheduleController {

  @GetMapping("{scheduleId}/available-seats")
  public ResponseEntity<GetAvailableSeatsResponse> getAvailableSeats(
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid,
      @PathVariable Long scheduleId) {
    // TODO: 토큰 검증

    List<ConcertSeatResponse> concertSeats = List.of(
        new ConcertSeatResponse(1L, scheduleId, 1, 100, false, LocalDateTime.now(),
            null));

    return ResponseEntity.ok(new GetAvailableSeatsResponse(concertSeats));
  }
}
