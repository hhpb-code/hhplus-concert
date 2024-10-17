package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.controller.IConcertSeatController;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.ReserveSeatResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concert-seats")
public class ConcertSeatController implements IConcertSeatController {

  @PostMapping("/{concertSeatId}/reservation")
  public ResponseEntity<ReserveSeatResponse> reserveSeat(
      @PathVariable Long concertSeatId,
      @RequestHeader(CommonHttpHeader.X_USER_ID) Long userId,
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid
  ) {
    // TODO: 토큰 검증
    
    ReservationResponse reservation = new ReservationResponse(1L,
        concertSeatId, userId, ReservationStatus.WAITING, LocalDateTime.now(), LocalDateTime.now(),
        null);

    return ResponseEntity.status(HttpStatus.CREATED).body(new ReserveSeatResponse(reservation));
  }
}
