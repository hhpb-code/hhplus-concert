package com.example.hhplus.concert.infra.db.concert.impl;

import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSchedulesByConcertIdAndNowParam;
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
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ReservationJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

  private final ConcertJpaRepository concertJpaRepository;

  private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

  private final ConcertSeatJpaRepository concertSeatJpaRepository;

  private final ReservationJpaRepository reservationJpaRepository;

  @Override
  public ConcertSeat saveConcertSeat(ConcertSeat concertSeat) {
    return concertSeatJpaRepository.save(concertSeat);
  }

  @Override
  public Reservation saveReservation(Reservation reservation) {
    return reservationJpaRepository.save(reservation);
  }

  @Override
  public void saveAllReservations(List<Reservation> reservations) {
    reservationJpaRepository.saveAll(reservations);
  }

  @Override
  public void saveAllConcertSeats(List<ConcertSeat> concertSeats) {
    concertSeatJpaRepository.saveAll(concertSeats);
  }

  @Override
  public Concert getConcert(GetConcertByIdParam param) {
    return concertJpaRepository.findById(param.id())
        .orElseThrow(() -> new CoreException(ErrorType.Concert.CONCERT_NOT_FOUND));
  }

  @Override
  public Concert getConcert(GetConcertByIdWithLockParam param) {
    return concertJpaRepository.findByIdWithLock(param.id())
        .orElseThrow(() -> new CoreException(ErrorType.Concert.CONCERT_NOT_FOUND));
  }

  @Override
  public ConcertSchedule getConcertSchedule(GetConcertScheduleByIdParam param) {
    return concertScheduleJpaRepository.findById(param.concertScheduleId()).orElseThrow(
        () -> new CoreException(ErrorType.Concert.CONCERT_SCHEDULE_NOT_FOUND));
  }

  @Override
  public List<ConcertSchedule> findAllConcertSchedules(
      FindAllConcertSchedulesByConcertIdAndNowParam param) {
    return concertScheduleJpaRepository.findByConcertIdAndReservationPeriod(param.concertId(),
        LocalDateTime.now());
  }

  @Override
  public ConcertSeat getConcertSeat(GetConcertSeatByIdParam param) {
    return concertSeatJpaRepository.findById(param.concertSeatId()).orElseThrow(
        () -> new CoreException(ErrorType.Concert.CONCERT_SEAT_NOT_FOUND));
  }

  @Override
  public ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockParam param) {
    return concertSeatJpaRepository.findByIdWithLock(param.concertSeatId()).orElseThrow(
        () -> new CoreException(ErrorType.Concert.CONCERT_SEAT_NOT_FOUND));
  }

  @Override
  public List<ConcertSeat> findAllConcertSeats(
      FindAllConcertSeatsByConcertIdAndIsReservedParam param) {
    return concertSeatJpaRepository.findSeatsByScheduleIdAndIsReserved(param.concertScheduleId(),
        param.isReserved());
  }

  @Override
  public Reservation getReservation(GetReservationByIdParam param) {
    return reservationJpaRepository.findById(param.reservationId()).orElseThrow(
        () -> new CoreException(ErrorType.Concert.RESERVATION_NOT_FOUND));
  }

  @Override
  public Reservation getReservation(GetReservationByIdWithLockParam param) {
    return reservationJpaRepository.findByIdWithLock(param.reservationId()).orElseThrow(
        () -> new CoreException(ErrorType.Concert.RESERVATION_NOT_FOUND));
  }

  @Override
  public List<Reservation> findAllReservations(
      FindAllReservationsByStatusAndReservedAtBeforeWithLockParam param) {
    return reservationJpaRepository.findAllByStatusAndReservedAtBefore(param.status(),
        param.expiredAt());
  }

  @Override
  public List<Reservation> findAllReservations(FindAllReservationsByIdsWithLockParam param) {
    return reservationJpaRepository.findAllByIdsWithLock(param.reservationIds());
  }

  @Override
  public List<ConcertSeat> findAllConcertSeats(FindAllConcertSeatsByIdsWithLockParam param) {
    return concertSeatJpaRepository.findAllByIdsWithLock(param.concertSeatIds());
  }
}
