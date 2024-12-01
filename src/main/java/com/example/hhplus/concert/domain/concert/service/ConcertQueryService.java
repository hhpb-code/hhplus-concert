package com.example.hhplus.concert.domain.concert.service;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindAllExpiredReservationsWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSeatsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindUpcomingConcertsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSchedulesByConcertIdAndNowParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSchedulesByReservationStartAtBetweenParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSeatsByConcertIdAndIsReservedParam;
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
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ConcertQueryService {

  private final ConcertRepository concertRepository;

  public Concert getConcert(GetConcertByIdQuery query) {
    return concertRepository.getConcert(new GetConcertByIdParam(query.id()));
  }

  @Transactional
  public Concert getConcert(GetConcertByIdWithLockQuery query) {
    return concertRepository.getConcert(new GetConcertByIdWithLockParam(query.id()));
  }

  public ConcertSeat getConcertSeat(GetConcertSeatByIdQuery query) {
    return concertRepository.getConcertSeat(new GetConcertSeatByIdParam(query.concertSeatId()));
  }

  @Transactional
  public ConcertSeat getConcertSeat(GetConcertSeatByIdWithLockQuery query) {
    return concertRepository.getConcertSeat(
        new GetConcertSeatByIdWithLockParam(query.concertSeatId()));
  }

  public Reservation getReservation(GetReservationByIdQuery query) {
    return concertRepository.getReservation(new GetReservationByIdParam(query.reservationId()));
  }

  public List<ConcertSchedule> findReservableConcertSchedules(
      FindReservableConcertSchedulesQuery query) {
    return concertRepository.findAllConcertSchedules(
        new FindAllConcertSchedulesByConcertIdAndNowParam(query.concertId(),
            LocalDateTime.now()));
  }

  public ConcertSchedule getConcertSchedule(GetConcertScheduleByIdQuery query) {
    return concertRepository.getConcertSchedule(
        new GetConcertScheduleByIdParam(query.concertScheduleId()));
  }

  public List<ConcertSeat> findReservableConcertSeats(FindReservableConcertSeatsQuery query) {
    return concertRepository.findAllConcertSeats(
        new FindAllConcertSeatsByConcertIdAndIsReservedParam(query.concertScheduleId(), false));
  }

  @Transactional
  public Reservation getReservation(GetReservationByIdWithLockQuery query) {
    return concertRepository.getReservation(
        new GetReservationByIdWithLockParam(query.reservationId()));
  }

  public List<Reservation> findAllExpiredReservations(
      FindAllExpiredReservationsWithLockQuery query) {
    return concertRepository.findAllReservations(
        new FindAllReservationsByStatusAndReservedAtBeforeWithLockParam(ReservationStatus.WAITING,
            LocalDateTime.now().minusMinutes(ConcertConstants.RESERVATION_EXPIRATION_MINUTES)));
  }

  public List<ConcertSchedule> findAllConcertSchedules(
      FindUpcomingConcertsQuery query) {
    return concertRepository.findAllConcertSchedules(
        new FindAllConcertSchedulesByReservationStartAtBetweenParam(
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(query.minutesBeforeReservationStartAt())
        )
    );
  }
}
