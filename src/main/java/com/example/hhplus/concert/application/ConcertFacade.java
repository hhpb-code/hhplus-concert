package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CancelReservationsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ConfirmReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReleaseConcertSeatsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindAllExpiredReservationsWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSeatsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.service.ConcertCommandService;
import com.example.hhplus.concert.domain.concert.service.ConcertQueryService;
import com.example.hhplus.concert.domain.payment.dto.PaymentCommand.CreatePaymentCommand;
import com.example.hhplus.concert.domain.payment.dto.PaymentQuery.GetPaymentByIdQuery;
import com.example.hhplus.concert.domain.payment.model.Payment;
import com.example.hhplus.concert.domain.payment.service.PaymentCommandService;
import com.example.hhplus.concert.domain.payment.service.PaymentQueryService;
import com.example.hhplus.concert.domain.user.dto.UserCommand.WithdrawUserWalletAmountCommand;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.service.UserCommandService;
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

  private final UserCommandService userCommandService;

  private final PaymentQueryService paymentQueryService;

  private final PaymentCommandService paymentCommandService;

  public List<ConcertSchedule> getReservableConcertSchedules(Long concertId) {
    var concert = concertQueryService.getConcert(new GetConcertByIdQuery(concertId));

    return concertQueryService.findReservableConcertSchedules(
        new FindReservableConcertSchedulesQuery(concert.getId()));
  }

  public List<ConcertSeat> getReservableConcertSeats(Long concertScheduleId) {
    var concertSchedule = concertQueryService.getConcertSchedule(
        new GetConcertScheduleByIdQuery(concertScheduleId));

    concertSchedule.validateReservationTime();

    return concertQueryService.findReservableConcertSeats(
        new FindReservableConcertSeatsQuery(concertScheduleId));
  }

  /*
   * @deprecated Pessimistic Locking을 사용한 예약 기능은 성능 이슈가 있어서 사용하지 않습니다.
   */
  @Deprecated(forRemoval = false)
  public Reservation reserveConcertSeatWithPessimisticLock(Long concertSeatId, Long userId) {
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

  public Reservation reserveConcertSeat(Long concertSeatId, Long userId) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    var concertSeat = concertQueryService.getConcertSeat(
        new GetConcertSeatByIdQuery(concertSeatId));

    var concertSchedule = concertQueryService.getConcertSchedule(
        new GetConcertScheduleByIdQuery(concertSeat.getConcertScheduleId()));

    concertSchedule.validateReservationTime();

    concertCommandService.reserveConcertSeat(
        new ReserveConcertSeatCommand(concertSeat.getId()));

    Long reservationId = concertCommandService.createReservation(
        new CreateReservationCommand(concertSeat.getId(), user.getId()));

    return concertQueryService.getReservation(new GetReservationByIdQuery(reservationId));
  }

  @Transactional
  public Payment payReservation(Long reservationId, Long userId) {
    var reservation = concertQueryService.getReservation(
        new GetReservationByIdWithLockQuery(reservationId));

    reservation.validateConfirmConditions(userId);

    var user = userQueryService.getUser(new GetUserByIdQuery(reservation.getUserId()));

    var concertSeat = concertQueryService.getConcertSeat(
        new GetConcertSeatByIdWithLockQuery(reservation.getConcertSeatId()));

    concertSeat.validateReserved();

    userCommandService.withDrawUserWalletAmount(
        new WithdrawUserWalletAmountCommand(user.getId(), concertSeat.getPrice()));

    concertCommandService.confirmReservation(new ConfirmReservationCommand(reservation.getId()));

    Long paymentId = paymentCommandService.createPayment(
        new CreatePaymentCommand(reservation.getId(), user.getId(), concertSeat.getPrice()));

    return paymentQueryService.getPayment(new GetPaymentByIdQuery(paymentId));
  }

  @Transactional
  public void expireReservations() {
    var expiredReservations = concertQueryService.findAllExpiredReservations(
        new FindAllExpiredReservationsWithLockQuery());

    if (expiredReservations.isEmpty()) {
      return;
    }

    var concertSeatIds = expiredReservations.stream()
        .map(Reservation::getConcertSeatId)
        .toList();
    var reservationIds = expiredReservations.stream()
        .map(Reservation::getId)
        .toList();

    concertCommandService.releaseConcertSeats(new ReleaseConcertSeatsByIdsCommand(concertSeatIds));
    concertCommandService.cancelReservations(new CancelReservationsByIdsCommand(reservationIds));
  }

}
