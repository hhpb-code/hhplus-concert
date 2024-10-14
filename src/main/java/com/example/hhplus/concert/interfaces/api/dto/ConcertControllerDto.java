package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import java.util.List;

public class ConcertControllerDto {

  public record GetAvailableSchedulesResponse(
      List<ConcertSchedule> concertSchedules
  ) {

  }

}
