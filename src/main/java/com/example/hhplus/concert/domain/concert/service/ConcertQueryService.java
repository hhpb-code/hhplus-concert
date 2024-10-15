package com.example.hhplus.concert.domain.concert.service;

import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdParam;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertQueryService {

  private final ConcertRepository concertRepository;

  public Reservation getReservation(GetReservationByIdQuery query) {
    return concertRepository.getReservation(new GetReservationByIdParam(query.reservationId()));
  }

  public ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockQuery query) {
    return concertRepository.getConcertSeat(
        new GetConcertSeatByIdWithLockParam(query.concertSeatId()));
  }
}
