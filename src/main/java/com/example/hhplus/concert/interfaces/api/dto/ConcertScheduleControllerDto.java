package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class ConcertScheduleControllerDto {

  public record GetAvailableSeatsResponse(
      @Schema(description = "콘서트 좌석 목록")
      List<ConcertSeatDto> concertSeats
  ) {

  }

  // NOTE: Presentation POJO (DTO) for ConcertSeat
  public record ConcertSeatDto(
      @Schema(description = "콘서트 좌석 ID", example = "1")
      Long id,

      @Schema(description = "콘서트 일정 ID", example = "1")
      Long concertScheduleId,

      @Schema(description = "좌석 번호", example = "1")
      Long number,

      @Schema(description = "좌석 가격", example = "100")
      Long price,

      @Schema(description = "예약 여부", example = "false")
      Boolean isReserved
  ) {

    public ConcertSeatDto(ConcertSeat concertSeat) {
      this(concertSeat.id(), concertSeat.concertScheduleId(), concertSeat.number(),
          concertSeat.price(), concertSeat.isReserved());
    }

  }

}
