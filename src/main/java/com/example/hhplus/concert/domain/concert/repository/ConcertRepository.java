package com.example.hhplus.concert.domain.concert.repository;

import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAvailableConcertSchedulesByConcertIdAndNowParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdParam;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import java.util.List;

public interface ConcertRepository {

  ConcertSeat saveConcertSeat(ConcertSeat concertSeat);

  Reservation saveReservation(Reservation reservation);

  Concert getConcert(GetConcertByIdParam param);

  List<ConcertSchedule> findAvailableConcertSchedules(
      FindAvailableConcertSchedulesByConcertIdAndNowParam param);

  ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockParam param);

  Reservation getReservation(GetReservationByIdParam param);

}
