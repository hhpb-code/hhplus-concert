package com.example.hhplus.concert.domain.concert.service;

import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ConfirmReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSeatsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdWithLockQuery;
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
  public ConcertSeat getConcertSeat(GetConcertSeatByIdQuery query) {
    return concertRepository.getConcertSeat(new GetConcertSeatByIdParam(query.concertSeatId()));
  }

  @Transactional
  public ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockQuery query) {
    return concertRepository.getConcertSeat(
        new GetConcertSeatByIdWithLockParam(query.concertSeatId()));
  }

  @Transactional(readOnly = true)
  public Concert getConcert(GetConcertByIdQuery query) {
    return concertRepository.getConcert(new GetConcertByIdParam(query.id()));
  }

  @Transactional(readOnly = true)
  public List<ConcertSchedule> findReservableConcertSchedules(
      FindReservableConcertSchedulesQuery query) {
    return concertRepository.findReservableConcertSchedules(
        new FindReservableConcertSchedulesByConcertIdAndNowParam(query.concertId(),
            LocalDateTime.now()));
  }

  @Transactional(readOnly = true)
  public ConcertSchedule getConcertSchedule(GetConcertScheduleByIdQuery query) {
    return concertRepository.getConcertSchedule(
        new GetConcertScheduleByIdParam(query.concertScheduleId()));
  }

  @Transactional(readOnly = true)
  public List<ConcertSeat> findReservableConcertSeats(FindReservableConcertSeatsQuery query) {
    return concertRepository.findReservableConcertSeats(
        new FindReservableConcertSeatsByConcertIdParam(query.concertScheduleId()));
  }

  @Transactional
  public Reservation getReservation(GetReservationByIdWithLockQuery query) {
    return concertRepository.getReservation(
        new GetReservationByIdWithLockParam(query.reservationId()));
  }

  // TODO: command service로 이동
  @Transactional
  public void confirmReservation(ConfirmReservationCommand command) {
    Reservation reservation = getReservation(
        new GetReservationByIdWithLockQuery(command.reservationId()));

    reservation.confirm();

    concertRepository.saveReservation(reservation);
  }

  @Transactional(readOnly = true)
  public List<Reservation> findAllExpiredReservations() {
    return concertRepository.findAllExpiredReservations();
  }
}
