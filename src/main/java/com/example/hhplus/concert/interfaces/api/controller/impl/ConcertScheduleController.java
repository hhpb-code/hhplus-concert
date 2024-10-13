package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.interfaces.api.controller.IConcertScheduleController;
import com.example.hhplus.concert.interfaces.api.dto.ConcertScheduleControllerDto.ConcertSeatDto;
import com.example.hhplus.concert.interfaces.api.dto.ConcertScheduleControllerDto.GetAvailableSeatsResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concert-schedules")
public class ConcertScheduleController implements IConcertScheduleController {

  @GetMapping("{scheduleId}/available-seats")
  public ResponseEntity<GetAvailableSeatsResponse> getAvailableSeats(
      @PathVariable Long scheduleId) {
    List<ConcertSeat> concertSeats = List.of(new ConcertSeat(1L, scheduleId, 1L, 100L, false));
    List<ConcertSeatDto> concertSeatDtos = concertSeats.stream()
        .map(ConcertSeatDto::new)
        .toList();

    return ResponseEntity.ok(new GetAvailableSeatsResponse(concertSeatDtos));
  }
}
