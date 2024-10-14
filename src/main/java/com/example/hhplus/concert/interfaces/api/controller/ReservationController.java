package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.concert.model.Payment;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.CreateReservationRequest;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.CreateReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationRequest;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reservation", description = "예약 API")
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

  @PostMapping
  @Operation(summary = "예약 생성", description = "예약을 생성합니다.")
  public ResponseEntity<CreateReservationResponse> createReservation(
      @RequestBody CreateReservationRequest request
  ) {
    Reservation reservation = new Reservation(1L, request.concertSeatId(), request.userId(),
        ReservationStatus.WAITING, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateReservationResponse(reservation));

  }

  @PostMapping("{reservationId}/payments")
  @Operation(summary = "결제", description = "결제를 진행합니다.")
  public ResponseEntity<PayReservationResponse> payReservation(
      @PathVariable Long reservationId,
      @RequestBody PayReservationRequest request
  ) {
    Payment payment = new Payment(1L, reservationId, request.userId(), 100L);

    return ResponseEntity.ok(new PayReservationResponse(payment));
  }

}
