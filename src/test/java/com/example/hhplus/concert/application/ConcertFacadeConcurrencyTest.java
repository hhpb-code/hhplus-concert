package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ReservationJpaRepository;
import com.example.hhplus.concert.infra.db.user.UserJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("ConcertFacade 동시성 테스트")
class ConcertFacadeConcurrencyTest {

  @Autowired
  private ConcertFacade concertFacade;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private ConcertSeatJpaRepository concertSeatJpaRepository;

  @Autowired
  private ReservationJpaRepository reservationJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;


  @BeforeEach
  void setUp() {
    concertScheduleJpaRepository.deleteAll();
    concertJpaRepository.deleteAll();
    concertSeatJpaRepository.deleteAll();
    reservationJpaRepository.deleteAll();
    userJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("콘서트 좌석 예약 동시성 테스트")
  class ReserveConcertSeatConcurrencyTest {

    @Test
    @DisplayName("동시성 테스트 - 동일 좌석 동시 예약")
    void shouldSuccessfullyReserveConcertSeat() {
      // given
      final int threadCount = 10;
      ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder()
              .concertId(1L)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now().minusDays(1))
              .reservationEndAt(LocalDateTime.now().plusDays(1))
              .build()
      );

      ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder()
              .concertScheduleId(concertSchedule.getId())
              .number(1)
              .isReserved(false)
              .price(10000)
              .build()
      );

      List<User> users = IntStream.range(0, threadCount)
          .mapToObj(i -> userJpaRepository.save(User.builder().name("user" + i).build()))
          .toList();

      // when
      final List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
          .mapToObj(i -> CompletableFuture.runAsync(() -> {
            try {
              concertFacade.reserveConcertSeat(concertSeat.getId(), users.get(i).getId());
            } catch (BusinessException e) {
              if (e.getErrorCode().equals(ConcertErrorCode.CONCERT_SEAT_ALREADY_RESERVED)) {
                return;
              }

              throw e;
            }
          }))
          .toList();
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

      // then
      final ConcertSeat reservedConcertSeat = concertSeatJpaRepository.findById(concertSeat.getId())
          .get();
      assertThat(reservedConcertSeat.getIsReserved()).isTrue();

      final List<Reservation> reservations = reservationJpaRepository.findAll();
      assertThat(reservations).hasSize(1);

      final Reservation reservation = reservations.get(0);
      assertThat(reservation.getConcertSeatId()).isEqualTo(concertSeat.getId());
      assertThat(reservation.getUserId()).isNotNull();
      assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.WAITING);

    }
  }


}