package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.application.ConcertFacade;
import com.example.hhplus.concert.interfaces.api.controller.IReservationController;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PaymentResponse;
import com.example.hhplus.concert.interfaces.api.support.CommonHttpHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController implements IReservationController {

  private final ConcertFacade concertFacade;

  @PostMapping("{reservationId}/payments")
  public ResponseEntity<PayReservationResponse> payReservation(
      @PathVariable Long reservationId,
      @RequestHeader(CommonHttpHeader.X_USER_ID) Long userId
  ) {
    PaymentResponse payment = new PaymentResponse(
        concertFacade.payReservation(reservationId, userId));

    return ResponseEntity.ok(new PayReservationResponse(payment));
  }

}
