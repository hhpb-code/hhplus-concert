package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.service.ConcertQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

  private final ConcertQueryService concertQueryService;

  public List<ConcertSchedule> getReservableConcertSchedules(Long concertId) {
    var concert = concertQueryService.getConcert(new GetConcertByIdQuery(concertId));

    return concertQueryService.findReservableConcertSchedules(
        new FindReservableConcertSchedulesQuery(concert.getId()));
  }

}
