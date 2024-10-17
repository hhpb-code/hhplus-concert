package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.controller.IReservationController;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PaymentResponse;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController implements IReservationController {

  @PostMapping("{reservationId}/payments")
  public ResponseEntity<PayReservationResponse> payReservation(
      @PathVariable Long reservationId,
      @RequestHeader(CommonHttpHeader.X_USER_ID) Long userId
  ) {
    PaymentResponse payment = new PaymentResponse(1L, reservationId, userId, 100L,
        LocalDateTime.now(),
        null);

    return ResponseEntity.ok(new PayReservationResponse(payment));
  }

}
