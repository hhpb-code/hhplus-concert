package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.interfaces.api.dto.ConcertControllerDto.GetAvailableSchedulesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Concert", description = "콘서트 API")
@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertController {

  @GetMapping("/{concertId}/available-schedules")
  @Operation(summary = "예약 가능한 콘서트 일정 조회", description = "예약 가능한 콘서트 일정을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "예약 가능한 콘서트 일정 조회 성공")
  public ResponseEntity<GetAvailableSchedulesResponse> getAvailableSchedules(
      @PathVariable Long concertId
  ) {
    List<ConcertSchedule> concertSchedules = List.of(
        new ConcertSchedule(1L, concertId, LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2))
    );

    return ResponseEntity.ok(new GetAvailableSchedulesResponse(concertSchedules));
  }

}
