package com.example.hhplus.concert.domain.concert.repository;

import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindReservableConcertSchedulesByConcertIdAndNowParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindReservableConcertSeatsByConcertIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertScheduleByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import java.util.List;

public interface ConcertRepository {

  ConcertSeat saveConcertSeat(ConcertSeat concertSeat);

  Reservation saveReservation(Reservation reservation);

  Concert getConcert(GetConcertByIdParam param);

  ConcertSchedule getConcertSchedule(GetConcertScheduleByIdParam param);

  List<ConcertSchedule> findReservableConcertSchedules(
      FindReservableConcertSchedulesByConcertIdAndNowParam param);

  ConcertSeat getConcertSeat(GetConcertSeatByIdParam param);

  ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockParam param);

  List<ConcertSeat> findReservableConcertSeats(
      FindReservableConcertSeatsByConcertIdParam param);

  Reservation getReservation(GetReservationByIdParam param);

  Reservation getReservation(GetReservationByIdWithLockParam param);

}
