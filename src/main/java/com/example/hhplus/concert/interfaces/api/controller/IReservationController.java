package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.ReservationControllerDto.PayReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Reservation", description = "예약 내역 API")
public interface IReservationController {

  @Operation(summary = "결제", description = "결제를 진행합니다.")
  @ApiResponse(responseCode = "201", description = "결제 성공")
  ResponseEntity<PayReservationResponse> payReservation(
      @Schema(description = "예약 내역 ID", example = "1")
      Long reservationId,

      @Schema(description = "사용자 ID", example = "1")
      Long userId
  );

}
