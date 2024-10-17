package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSeatsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.service.ConcertCommandService;
import com.example.hhplus.concert.domain.concert.service.ConcertQueryService;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.service.UserQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

  private final ConcertQueryService concertQueryService;

  private final ConcertCommandService concertCommandService;

  private final UserQueryService userQueryService;

  @Transactional(readOnly = true)
  public List<ConcertSchedule> getReservableConcertSchedules(Long concertId) {
    var concert = concertQueryService.getConcert(new GetConcertByIdQuery(concertId));

    return concertQueryService.findReservableConcertSchedules(
        new FindReservableConcertSchedulesQuery(concert.getId()));
  }

  @Transactional(readOnly = true)
  public List<ConcertSeat> getReservableConcertSeats(Long concertScheduleId) {
    var concertSchedule = concertQueryService.getConcertSchedule(
        new GetConcertScheduleByIdQuery(concertScheduleId));

    concertSchedule.validateReservationTime();

    return concertQueryService.findReservableConcertSeats(
        new FindReservableConcertSeatsQuery(concertScheduleId));
  }

  @Transactional
  public Reservation reserveConcertSeat(Long concertSeatId, Long userId) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    var concertSeat = concertQueryService.getConcertSeat(
        new GetConcertSeatByIdWithLockQuery(concertSeatId));

    var concertSchedule = concertQueryService.getConcertSchedule(
        new GetConcertScheduleByIdQuery(concertSeat.getConcertScheduleId()));

    concertSchedule.validateReservationTime();

    concertCommandService.reserveConcertSeat(
        new ReserveConcertSeatCommand(concertSeat.getId()));

    Long reservationId = concertCommandService.createReservation(
        new CreateReservationCommand(concertSeat.getId(), user.getId()));

    return concertQueryService.getReservation(new GetReservationByIdQuery(reservationId));
  }

}
