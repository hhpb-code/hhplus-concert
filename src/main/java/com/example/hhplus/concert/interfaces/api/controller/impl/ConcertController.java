package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.application.ConcertFacade;
import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.controller.IConcertController;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ConcertScheduleResponse;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSchedulesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController implements IConcertController {

  private final ConcertFacade concertFacade;

  @GetMapping("/{concertId}/available-schedules")
  public ResponseEntity<GetAvailableSchedulesResponse> getAvailableSchedules(
      @PathVariable Long concertId,
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid
  ) {
    List<ConcertScheduleResponse> concertSchedules = concertFacade.getReservableConcertSchedules(
            concertId)
        .stream()
        .map(ConcertScheduleResponse::new)
        .toList();

    return ResponseEntity.ok(new GetAvailableSchedulesResponse(concertSchedules));
  }

}
