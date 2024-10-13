package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.CreateReservationRequest;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.CreateReservationResponse;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationRequest;
import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Reservation", description = "예약 API")
public interface IReservationController {

  @Operation(summary = "예약 생성", description = "예약을 생성합니다.")
  ResponseEntity<CreateReservationResponse> createReservation(
      CreateReservationRequest request
  );

  @Operation(summary = "결제", description = "결제를 진행합니다.")
  ResponseEntity<PayReservationResponse> payReservation(
      @Schema(description = "예약 ID", example = "1")
      Long reservationId,
      PayReservationRequest request
  );

}
