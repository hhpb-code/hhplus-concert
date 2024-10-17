package com.example.hhplus.concert.infra.db.concert.impl;

import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSeatsByIdsWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllReservationsByIdsWithLockParam;
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
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class concertRepositoryImpl implements ConcertRepository {

  @Override
  public ConcertSeat saveConcertSeat(ConcertSeat concertSeat) {
    return null;
  }

  @Override
  public Reservation saveReservation(Reservation reservation) {
    return null;
  }

  @Override
  public void saveAllReservations(List<Reservation> reservations) {

  }

  @Override
  public void saveAllConcertSeats(List<ConcertSeat> concertSeats) {

  }

  @Override
  public Concert getConcert(GetConcertByIdParam param) {
    return null;
  }

  @Override
  public ConcertSchedule getConcertSchedule(GetConcertScheduleByIdParam param) {
    return null;
  }

  @Override
  public List<ConcertSchedule> findReservableConcertSchedules(
      FindReservableConcertSchedulesByConcertIdAndNowParam param) {
    return List.of();
  }

  @Override
  public ConcertSeat getConcertSeat(GetConcertSeatByIdParam param) {
    return null;
  }

  @Override
  public ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockParam param) {
    return null;
  }

  @Override
  public List<ConcertSeat> findReservableConcertSeats(
      FindReservableConcertSeatsByConcertIdParam param) {
    return List.of();
  }

  @Override
  public Reservation getReservation(GetReservationByIdParam param) {
    return null;
  }

  @Override
  public Reservation getReservation(GetReservationByIdWithLockParam param) {
    return null;
  }

  @Override
  public List<Reservation> findAllExpiredReservations() {
    return List.of();
  }

  @Override
  public List<Reservation> findAllReservations(FindAllReservationsByIdsWithLockParam param) {
    return List.of();
  }

  @Override
  public List<ConcertSeat> findAllConcertSeats(FindAllConcertSeatsByIdsWithLockParam param) {
    return List.of();
  }
}