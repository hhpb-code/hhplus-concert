package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.controller.IConcertController;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ConcertScheduleResponse;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSchedulesResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertController implements IConcertController {

  @GetMapping("/{concertId}/available-schedules")
  public ResponseEntity<GetAvailableSchedulesResponse> getAvailableSchedules(
      @PathVariable Long concertId,
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid
  ) {
    // TODO: 토큰 검증
    
    List<ConcertScheduleResponse> concertSchedules = List.of(new ConcertScheduleResponse(
        1L,
        concertId,
        LocalDateTime.now().plusDays(1),
        LocalDateTime.now().minusDays(1),
        LocalDateTime.now().plusDays(1),
        LocalDateTime.now(),
        null
    ));

    return ResponseEntity.ok(new GetAvailableSchedulesResponse(concertSchedules));
  }

}
