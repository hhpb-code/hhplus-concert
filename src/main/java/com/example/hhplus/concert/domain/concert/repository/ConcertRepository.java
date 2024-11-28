package com.example.hhplus.concert.domain.concert.repository;

import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSchedulesByConcertIdAndNowParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSchedulesByReservationStartAtBetweenParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSeatsByConcertIdAndIsReservedParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSeatsByIdsWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllReservationsByIdsWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllReservationsByStatusAndReservedAtBeforeWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertScheduleByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.support.CacheName;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface ConcertRepository {

  ConcertSeat saveConcertSeat(ConcertSeat concertSeat);

  Reservation saveReservation(Reservation reservation);

  void saveAllReservations(List<Reservation> reservations);

  void saveAllConcertSeats(List<ConcertSeat> concertSeats);

  @Cacheable(value = CacheName.CONCERT, key = "#param.id")
  Concert getConcert(GetConcertByIdParam param);

  @Cacheable(value = CacheName.CONCERT, key = "#param.id")
  Concert getConcert(GetConcertByIdWithLockParam param);

  @Cacheable(value = CacheName.CONCERT_SCHEDULE, key = "#param.concertScheduleId")
  ConcertSchedule getConcertSchedule(GetConcertScheduleByIdParam param);

  List<ConcertSchedule> findAllConcertSchedules(
      FindAllConcertSchedulesByConcertIdAndNowParam param);

  List<ConcertSchedule> findAllConcertSchedules(
      FindAllConcertSchedulesByReservationStartAtBetweenParam param);

  ConcertSeat getConcertSeat(GetConcertSeatByIdParam param);

  ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockParam param);

  List<ConcertSeat> findAllConcertSeats(
      FindAllConcertSeatsByConcertIdAndIsReservedParam param);

  Reservation getReservation(GetReservationByIdParam param);

  Reservation getReservation(GetReservationByIdWithLockParam param);

  List<Reservation> findAllReservations(
      FindAllReservationsByStatusAndReservedAtBeforeWithLockParam param);

  List<Reservation> findAllReservations(FindAllReservationsByIdsWithLockParam param);

  List<ConcertSeat> findAllConcertSeats(FindAllConcertSeatsByIdsWithLockParam param);

}
