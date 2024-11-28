package com.example.hhplus.concert.domain.concert.service;

import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CancelReservationsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ConfirmReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReleaseConcertSeatsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllConcertSeatsByIdsWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.FindAllReservationsByIdsWithLockParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdParam;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetReservationByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ConcertCommandService {

  private final ConcertRepository concertRepository;

  public void reserveConcertSeat(ReserveConcertSeatCommand command) {
    ConcertSeat concertSeat = concertRepository.getConcertSeat(
        new GetConcertSeatByIdParam(command.concertSeatId()));

    concertSeat.reserve();

    concertRepository.saveConcertSeat(concertSeat);
  }

  public Long createReservation(CreateReservationCommand command) {
    Reservation reservation = Reservation.builder()
        .concertSeatId(command.concertSeatId())
        .userId(command.userId())
        .status(ReservationStatus.WAITING)
        .reservedAt(LocalDateTime.now())
        .build();

    return concertRepository.saveReservation(reservation).getId();
  }

  public void confirmReservation(ConfirmReservationCommand command) {
    Reservation reservation = concertRepository.getReservation(
        new GetReservationByIdWithLockParam(command.reservationId()));

    reservation.confirm();

    concertRepository.saveReservation(reservation);
  }

  public void cancelReservations(CancelReservationsByIdsCommand command) {
    List<Reservation> reservations = concertRepository.findAllReservations(
        new FindAllReservationsByIdsWithLockParam(command.reservationIds()));

    reservations.forEach(Reservation::cancel);

    concertRepository.saveAllReservations(reservations);
  }

  public void releaseConcertSeats(ReleaseConcertSeatsByIdsCommand command) {
    List<ConcertSeat> concertSeats = concertRepository.findAllConcertSeats(
        new FindAllConcertSeatsByIdsWithLockParam(command.concertSeatIds()));

    concertSeats.forEach(ConcertSeat::release);

    concertRepository.saveAllConcertSeats(concertSeats);
  }
}
