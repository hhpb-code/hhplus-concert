package com.example.hhplus.concert.domain.concert.repository;

import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdParam;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;

public interface ConcertRepository {

  ConcertSeat saveConcertSeat(ConcertSeat concertSeat);

  Reservation saveReservation(Reservation reservation);

  Reservation getReservation(GetReservationByIdParam param);

  ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockParam query);
  
}
