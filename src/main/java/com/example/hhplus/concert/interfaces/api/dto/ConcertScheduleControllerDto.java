package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import java.util.List;

public class ConcertScheduleControllerDto {

  public record GetAvailableSeatsResponse(
      List<ConcertSeat> concertSeats
  ) {

  }

}
