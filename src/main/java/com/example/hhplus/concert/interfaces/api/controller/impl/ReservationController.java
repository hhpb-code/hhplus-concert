package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.domain.concert.model.Payment;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.interfaces.api.controller.IReservationController;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.CreateReservationRequest;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.CreateReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationRequest;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PaymentDto;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.ReservationDto;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController implements IReservationController {

  @PostMapping
  public ResponseEntity<CreateReservationResponse> createReservation(
      @RequestBody CreateReservationRequest request
  ) {
    Reservation reservation = new Reservation(1L, request.concertSeatId(), request.userId(),
        ReservationStatus.WAITING, LocalDateTime.now());
    ReservationDto reservationDto = new ReservationDto(reservation.id(),
        reservation.concertSeatId(),
        reservation.userId(), reservation.status(), reservation.reservedAt());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateReservationResponse(reservationDto));

  }

  @PostMapping("{reservationId}/payments")
  public ResponseEntity<PayReservationResponse> payReservation(
      @PathVariable Long reservationId,
      @RequestBody PayReservationRequest request
  ) {
    Payment payment = new Payment(1L, reservationId, request.userId(), 100L);
    PaymentDto paymentDto = new PaymentDto(payment.id(), payment.reservationId(), payment.userId(),
        payment.amount());

    return ResponseEntity.ok(new PayReservationResponse(paymentDto));
  }

}
