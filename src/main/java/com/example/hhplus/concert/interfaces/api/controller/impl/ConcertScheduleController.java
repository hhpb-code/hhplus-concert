package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.application.ConcertFacade;
import com.example.hhplus.concert.application.WaitingQueueFacade;
import com.example.hhplus.concert.interfaces.api.controller.IConcertScheduleController;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ConcertSeatResponse;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSeatsResponse;
import com.example.hhplus.concert.interfaces.api.support.CommonHttpHeader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concert-schedules")
@RequiredArgsConstructor
public class ConcertScheduleController implements IConcertScheduleController {

  private final ConcertFacade concertFacade;

  private final WaitingQueueFacade waitingQueueFacade;

  @GetMapping("{scheduleId}/available-seats")
  public ResponseEntity<GetAvailableSeatsResponse> getAvailableSeats(
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid,
      @PathVariable Long scheduleId
  ) {
    waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
        scheduleId);

    List<ConcertSeatResponse> concertSeats = concertFacade.getReservableConcertSeats(scheduleId)
        .stream()
        .map(ConcertSeatResponse::new)
        .toList();

    return ResponseEntity.ok(new GetAvailableSeatsResponse(concertSeats));
  }
}
