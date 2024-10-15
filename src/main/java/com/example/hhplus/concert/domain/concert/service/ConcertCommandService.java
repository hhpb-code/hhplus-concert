package com.example.hhplus.concert.domain.concert.service;

import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertRepositoryParam.GetConcertSeatByIdWithLockParam;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.domain.concert.repository.ConcertRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertCommandService {

  private final ConcertRepository concertRepository;

  @Transactional
  public void reserveConcertSeat(ReserveConcertSeatCommand command) {
    ConcertSeat concertSeat = concertRepository.getConcertSeat(
        new GetConcertSeatByIdWithLockParam(command.concertSeatId()));

    concertSeat.reserve();

    concertRepository.saveConcertSeat(concertSeat);
  }

  @Transactional
  public Long createReservation(CreateReservationCommand command) {
    Reservation reservation = Reservation.builder()
        .concertSeatId(command.concertSeatId())
        .userId(command.userId())
        .status(ReservationStatus.WAITING)
        .reservedAt(LocalDateTime.now())
        .build();

    return concertRepository.saveReservation(reservation).getId();
  }

}