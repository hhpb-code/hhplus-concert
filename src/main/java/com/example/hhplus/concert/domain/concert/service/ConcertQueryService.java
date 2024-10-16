package com.example.hhplus.concert.domain.concert.service;

import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindReservableConcertSchedulesByConcertIdAndNowParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdParam;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertQueryService {

  private final ConcertRepository concertRepository;

  @Transactional(readOnly = true)
  public Reservation getReservation(GetReservationByIdQuery query) {
    return concertRepository.getReservation(new GetReservationByIdParam(query.reservationId()));
  }

  @Transactional(readOnly = true)
  public ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockQuery query) {
    return concertRepository.getConcertSeat(
        new GetConcertSeatByIdWithLockParam(query.concertSeatId()));
  }

  public Concert getConcert(GetConcertByIdQuery query) {
    return concertRepository.getConcert(new GetConcertByIdParam(query.id()));
  }

  public List<ConcertSchedule> findReservableConcertSchedules(
      FindReservableConcertSchedulesQuery query) {
    return concertRepository.findReservableConcertSchedules(
        new FindReservableConcertSchedulesByConcertIdAndNowParam(query.concertId(),
            LocalDateTime.now()));
  }
}
