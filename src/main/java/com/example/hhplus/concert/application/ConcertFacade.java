package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CancelReservationsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ConfirmReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReleaseConcertSeatsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindAllExpiredReservationsWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSeatsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindUpcomingConcertsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.service.ConcertCommandService;
import com.example.hhplus.concert.domain.concert.service.ConcertQueryService;
import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.CreateOutboxEventCommand;
import com.example.hhplus.concert.domain.event.service.OutboxEventCommandService;
import com.example.hhplus.concert.domain.payment.dto.PaymentCommand.CreatePaymentCommand;
import com.example.hhplus.concert.domain.payment.dto.PaymentQuery.GetPaymentByIdQuery;
import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import com.example.hhplus.concert.domain.payment.model.Payment;
import com.example.hhplus.concert.domain.payment.service.PaymentCommandService;
import com.example.hhplus.concert.domain.payment.service.PaymentQueryService;
import com.example.hhplus.concert.domain.support.DistributedLockType;
import com.example.hhplus.concert.domain.support.annotation.DistributedLock;
import com.example.hhplus.concert.domain.user.dto.UserCommand.WithdrawUserWalletAmountCommand;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdQuery;
import com.example.hhplus.concert.domain.user.service.UserCommandService;
import com.example.hhplus.concert.domain.user.service.UserQueryService;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ExpireActivatedWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertFacade {

  private final ConcertQueryService concertQueryService;

  private final ConcertCommandService concertCommandService;

  private final UserQueryService userQueryService;

  private final UserCommandService userCommandService;

  private final PaymentQueryService paymentQueryService;

  private final PaymentCommandService paymentCommandService;

  private final WaitingQueueCommandService waitingQueueCommandService;

  private final OutboxEventCommandService outboxEventCommandService;

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

  @DistributedLock(type = DistributedLockType.USER_WALLET, keys = "userId")
  @Transactional
  public Payment payReservation(Long reservationId, Long userId, String uuid) {
    var reservation = concertQueryService.getReservation(
        new GetReservationByIdQuery(reservationId));

    reservation.validateConfirmConditions(userId);

    var user = userQueryService.getUser(new GetUserByIdQuery(reservation.getUserId()));

    var concertSeat = concertQueryService.getConcertSeat(
        new GetConcertSeatByIdQuery(reservation.getConcertSeatId()));

    concertSeat.validateReserved();

    var wallet = userQueryService.getWallet(new GetUserWalletByUserIdQuery(user.getId()));

    userCommandService.withDrawUserWalletAmount(
        new WithdrawUserWalletAmountCommand(wallet.getId(), concertSeat.getPrice()));

    Long paymentId = paymentCommandService.createPayment(
        new CreatePaymentCommand(reservation.getId(), user.getId(), concertSeat.getPrice()));

    waitingQueueCommandService.removeActiveToken(new ExpireActivatedWaitingQueueCommand(uuid));

    concertCommandService.confirmReservation(new ConfirmReservationCommand(reservation.getId()));

    outboxEventCommandService.createOutboxEvent(
        new CreateOutboxEventCommand(new PaymentSuccessEvent(paymentId)));

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

  public List<ConcertSchedule> getUpcomingConcertsAndSchedulesWithCache() {
    var concertSchedules = concertQueryService.findAllConcertSchedules(
        new FindUpcomingConcertsQuery(
            ConcertConstants.MINUTES_BEFORE_RESERVATION_START_AT));

    List<Long> concertIds = concertSchedules.stream()
        .map(ConcertSchedule::getConcertId)
        .distinct()
        .toList();

    for (Long concertId : concertIds) {
      concertQueryService.getConcert(new GetConcertByIdQuery(concertId));
    }

    for (ConcertSchedule concertSchedule : concertSchedules) {
      concertQueryService.getConcertSchedule(
          new GetConcertScheduleByIdQuery(concertSchedule.getId()));
    }

    return concertSchedules;
  }
}
