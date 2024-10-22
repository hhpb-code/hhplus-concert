package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertConstants;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.domain.payment.model.Payment;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ReservationJpaRepository;
import com.example.hhplus.concert.infra.db.user.UserJpaRepository;
import com.example.hhplus.concert.infra.db.user.WalletJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("ConcertFacade 통합 테스트")
class ConcertFacadeTest {

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

  @Autowired
  private WalletJpaRepository walletJpaRepository;


  @BeforeEach
  void setUp() {
    concertScheduleJpaRepository.deleteAll();
    concertJpaRepository.deleteAll();
    concertSeatJpaRepository.deleteAll();
    reservationJpaRepository.deleteAll();
    userJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("예약 가능한 콘서트 일정 조회")
  class GetReservableConcertSchedulesTest {

    @Test
    @DisplayName("예약 가능한 콘서트 일정 조회 실패 - 콘서트가 존재하지 않음")
    void shouldThrowConcertNotFoundException() {
      // given
      final Long concertId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.getReservableConcertSchedules(concertId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_NOT_FOUND);
    }

    @Test
    @DisplayName("예약 가능한 콘서트 일정 조회 성공 - 일정 없음")
    void shouldSuccessfullyGetReservableConcertSchedules() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      // when
      final List<ConcertSchedule> result = concertFacade.getReservableConcertSchedules(concertId);

      // then
      assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("예약 가능한 콘서트 일정 조회 성공 - 일정 있음")
    void shouldSuccessfullyGetReservableConcertSchedulesWithSchedule() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final List<ConcertSchedule> concertSchedules =
          concertScheduleJpaRepository.saveAll(
              List.of(
                  ConcertSchedule.builder().concertId(concertId)
                      .concertAt(LocalDateTime.now().plusDays(1))
                      .reservationStartAt(LocalDateTime.now())
                      .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build(),
                  ConcertSchedule.builder().concertId(concertId)
                      .concertAt(LocalDateTime.now().plusDays(2))
                      .reservationStartAt(LocalDateTime.now())
                      .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build()
              ));

      // when
      final List<ConcertSchedule> result = concertFacade.getReservableConcertSchedules(concertId);

      // then
      assertThat(result).hasSize(concertSchedules.size());
      for (int i = 0; i < result.size(); i++) {
        final ConcertSchedule concertSchedule = result.get(i);
        final ConcertSchedule expected = concertSchedules.get(i);

        assertThat(concertSchedule.getId()).isNotNull();
        assertThat(concertSchedule.getConcertId()).isEqualTo(expected.getConcertId());
        assertThat(concertSchedule.getConcertAt()).isEqualTo(expected.getConcertAt());
        assertThat(concertSchedule.getReservationStartAt()).isEqualTo(
            expected.getReservationStartAt());
        assertThat(concertSchedule.getReservationEndAt()).isEqualTo(expected.getReservationEndAt());
      }
    }

  }

  @Nested
  @DisplayName("예약 가능한 좌석 조회")
  class GetReservableConcertSeatsTest {

    @Test
    @DisplayName("예약 가능한 좌석 조회 실패 - 콘서트 일정이 존재하지 않음")
    void shouldThrowConcertScheduleNotFoundException() {
      // given
      final Long concertScheduleId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.getReservableConcertSeats(concertScheduleId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
    }

    @Test
    @DisplayName("예약 가능한 좌석 조회 실패 - 예약 가능 시간이 아님")
    void shouldThrowReservationTimeException() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder().concertId(concertId)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now().plusMinutes(1))
              .reservationEndAt(LocalDateTime.now().plusMinutes(2)).build());
      final Long concertScheduleId = concertSchedule.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.getReservableConcertSeats(concertScheduleId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SCHEDULE_NOT_RESERVABLE);
    }

    @Test
    @DisplayName("예약 가능한 좌석 조회 성공 - 좌석 없음")
    void shouldSuccessfullyGetReservableConcertSeats() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder().concertId(concertId)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now())
              .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build());
      final Long concertScheduleId = concertSchedule.getId();
      concertSeatJpaRepository.save(ConcertSeat.builder().concertScheduleId(concertScheduleId)
          .number(1).price(100).isReserved(true).build());

      // when
      final List<ConcertSeat> result = concertFacade.getReservableConcertSeats(concertScheduleId);

      // then
      assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("예약 가능한 좌석 조회 성공 - 좌석 있음")
    void shouldSuccessfullyGetReservableConcertSeatsWithSeats() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder().concertId(concertId)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now())
              .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build());
      final Long concertScheduleId = concertSchedule.getId();

      final List<ConcertSeat> concertSeats = concertSeatJpaRepository.saveAll(
          List.of(
              ConcertSeat.builder().concertScheduleId(concertScheduleId).number(1).price(100)
                  .isReserved(false).build(),
              ConcertSeat.builder().concertScheduleId(concertScheduleId).number(2).price(200)
                  .isReserved(false).build()
          ));

      // when
      final List<ConcertSeat> result = concertFacade.getReservableConcertSeats(concertScheduleId);

      // then
      assertThat(result).hasSize(concertSeats.size());
      for (int i = 0; i < result.size(); i++) {
        final ConcertSeat concertSeat = result.get(i);
        final ConcertSeat expected = concertSeats.get(i);

        assertThat(concertSeat.getId()).isNotNull();
        assertThat(concertSeat.getConcertScheduleId()).isEqualTo(expected.getConcertScheduleId());
        assertThat(concertSeat.getNumber()).isEqualTo(expected.getNumber());
        assertThat(concertSeat.getPrice()).isEqualTo(expected.getPrice());
        assertThat(concertSeat.getIsReserved()).isEqualTo(expected.getIsReserved());
        assertThat(concertSeat.getCreatedAt()).isEqualTo(expected.getCreatedAt());
        assertThat(concertSeat.getUpdatedAt()).isEqualTo(expected.getUpdatedAt());
      }
    }

  }

  @Nested
  @DisplayName("콘서트 좌석 예약")
  class ReserveConcertSeatTest {

    @Test
    @DisplayName("콘서트 좌석 예약 실패 - 사용자가 존재하지 않음")
    void shouldThrowUserNotFoundException() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = 1L;

      // when
      final Exception result = assertThrows(Exception.class, () -> {
        concertFacade.reserveConcertSeat(concertSeatId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(ErrorType.User.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("콘서트 좌석 예약 실패 - 좌석이 존재하지 않음")
    void shouldThrowConcertSeatNotFoundException() {
      // given
      final Long concertSeatId = 1L;
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.reserveConcertSeat(concertSeatId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SEAT_NOT_FOUND);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 실패 - 예약 가능한 시간이 아님")
    void shouldThrowReservationTimeException() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder().concertId(concertId)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now().plusMinutes(1))
              .reservationEndAt(LocalDateTime.now().plusMinutes(2)).build());
      final Long concertScheduleId = concertSchedule.getId();

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(concertScheduleId).number(1).price(100)
              .isReserved(false).build());
      final Long concertSeatId = concertSeat.getId();
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.reserveConcertSeat(concertSeatId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SCHEDULE_NOT_RESERVABLE);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 실패 - 좌석이 이미 예약됨")
    void shouldThrowConcertSeatAlreadyReservedException() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder().concertId(concertId)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now())
              .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build());
      final Long concertScheduleId = concertSchedule.getId();

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(concertScheduleId).number(1).price(100)
              .isReserved(true).build());
      final Long concertSeatId = concertSeat.getId();
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.reserveConcertSeat(concertSeatId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SEAT_ALREADY_RESERVED);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 성공")
    void shouldSuccessfullyReserveConcertSeat() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
          ConcertSchedule.builder().concertId(concertId)
              .concertAt(LocalDateTime.now().plusDays(1))
              .reservationStartAt(LocalDateTime.now())
              .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build());
      final Long concertScheduleId = concertSchedule.getId();

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(concertScheduleId).number(1).price(100)
              .isReserved(false).build());
      final Long concertSeatId = concertSeat.getId();
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      // when
      final Reservation result = concertFacade.reserveConcertSeat(concertSeatId, userId);

      // then
      assertThat(result.getId()).isNotNull();
      assertThat(result.getConcertSeatId()).isEqualTo(concertSeatId);
      assertThat(result.getUserId()).isEqualTo(userId);
      assertThat(result.getStatus()).isEqualTo(ReservationStatus.WAITING);
      assertThat(result.getCreatedAt()).isNotNull();
      assertThat(result.getUpdatedAt()).isNull();

      final ConcertSeat updatedConcertSeat = concertSeatJpaRepository.findById(concertSeatId).get();

      assertThat(updatedConcertSeat.getIsReserved()).isTrue();
    }
  }


  @Nested
  @DisplayName("콘서트 좌석 예약 내역 결제 테스트")
  class PayReservationTest {

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 예약 내역이 존재하지 않음")
    void shouldThrowReservationNotFoundException() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.RESERVATION_NOT_FOUND);
    }


    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 이미 결제된 예약 내역")
    void shouldThrowReservationAlreadyPaidException() {
      // given
      final Long userId = 1L;
      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(1L).userId(userId)
              .reservedAt(LocalDateTime.now())
              .status(ReservationStatus.CONFIRMED)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.RESERVATION_ALREADY_PAID);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - userID가 다름")
    void shouldThrowUserIdNotMatchException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(userId).amount(100).build());

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(wallet.getAmount() - 1)
              .isReserved(true)
              .build());
      final Long concertSeatId = concertSeat.getId();

      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeatId).userId(userId)
              .reservedAt(LocalDateTime.now())
              .status(ReservationStatus.WAITING)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.payReservation(reservationId, userId + 1);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.RESERVATION_USER_NOT_MATCHED);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 사용자가 존재하지 않음")
    void shouldThrowUserNotFoundException() {
      // given
      final Long userId = 1L;
      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(1L).userId(userId).reservedAt(LocalDateTime.now())
              .status(ReservationStatus.WAITING)
              .build());
      final Long reservationId = reservation.getId();

      concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(100)
              .isReserved(true)
              .build());

      // when
      final Exception result = assertThrows(Exception.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(ErrorType.User.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 좌석이 존재하지 않음")
    void shouldThrowConcertSeatNotFoundException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(1L).userId(userId).reservedAt(LocalDateTime.now())
              .status(ReservationStatus.WAITING)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SEAT_NOT_FOUND);
    }


    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 잔액 부족")
    void shouldThrowInsufficientBalanceException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(userId).amount(100).build());

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(wallet.getAmount() + 1)
              .isReserved(true)
              .build());
      final Long concertSeatId = concertSeat.getId();

      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeatId).userId(userId)
              .reservedAt(LocalDateTime.now())
              .status(ReservationStatus.WAITING)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final Exception result = assertThrows(Exception.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(ErrorType.User.NOT_ENOUGH_BALANCE.getMessage());
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 예약 되지 않은 좌석")
    void shouldThrowConcertSeatNotReservedException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(100)
              .isReserved(false)
              .build());
      final Long concertSeatId = concertSeat.getId();

      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeatId).userId(userId)
              .reservedAt(LocalDateTime.now())
              .status(ReservationStatus.WAITING)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SEAT_NOT_RESERVED);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 실패 - 이미 취소된 예약 내역")
    void shouldThrowReservationAlreadyCanceledException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(userId).amount(100).build());

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(wallet.getAmount() - 1)
              .isReserved(true)
              .build());
      final Long concertSeatId = concertSeat.getId();

      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeatId).userId(userId)
              .reservedAt(LocalDateTime.now())
              .status(ReservationStatus.CANCELED)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.payReservation(reservationId, userId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.RESERVATION_ALREADY_CANCELED);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 결제 성공")
    void shouldSuccessfullyPayReservation() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long userId = user.getId();

      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(userId).amount(100).build());

      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(wallet.getAmount() - 1)
              .isReserved(true)
              .build());
      final Long concertSeatId = concertSeat.getId();

      final Reservation reservation = reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeatId).userId(userId)
              .reservedAt(LocalDateTime.now())
              .status(ReservationStatus.WAITING)
              .build());
      final Long reservationId = reservation.getId();

      // when
      final Payment result = concertFacade.payReservation(reservationId, userId);

      // then
      assertThat(result.getId()).isNotNull();
      assertThat(result.getReservationId()).isEqualTo(reservationId);
      assertThat(result.getAmount()).isEqualTo(concertSeat.getPrice());
      assertThat(result.getCreatedAt()).isNotNull();
      assertThat(result.getUpdatedAt()).isNull();

      final Wallet updatedWallet = walletJpaRepository.findById(wallet.getId()).get();
      assertThat(updatedWallet.getAmount()).isEqualTo(wallet.getAmount() - concertSeat.getPrice());

      final Reservation updatedReservation = reservationJpaRepository.findById(reservation.getId())
          .get();
      assertThat(updatedReservation.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

  }

  @Nested
  @DisplayName("콘서트 좌석 예약 내역 만료 처리 테스트")
  class ExpireReservationTest {

    @Test
    @DisplayName("콘서트 좌석 예약 내역 만료 처리 실패 - 좌석이 예약 상태가 아님")
    void shouldThrowConcertSeatNotReservedException() {
      // given
      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(100)
              .isReserved(false)
              .build());

      reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeat.getId()).userId(1L)
              .reservedAt(
                  LocalDateTime.now().minusMinutes(ConcertConstants.RESERVATION_EXPIRATION_MINUTES))
              .status(ReservationStatus.WAITING)
              .build());

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.expireReservations();
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_SEAT_NOT_RESERVED);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 만료 처리 성공 - 만료된 예약 내역이 없음")
    void shouldSuccessfullyExpireReservations() {
      // given
      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(100)
              .isReserved(true)
              .build());

      reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeat.getId()).userId(1L)
              .reservedAt(LocalDateTime.now()
                  .minusMinutes(ConcertConstants.RESERVATION_EXPIRATION_MINUTES - 1))
              .status(ReservationStatus.WAITING)
              .build());

      // when
      concertFacade.expireReservations();

      // then
      final List<Reservation> reservations = reservationJpaRepository.findAll();
      assertThat(reservations).hasSize(1);
      assertThat(reservations.get(0).getStatus()).isEqualTo(ReservationStatus.WAITING);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 내역 만료 처리 성공 - 만료된 예약 내역이 있음")
    void shouldSuccessfullyExpireReservationsWithExpiredReservations() {
      // given
      final ConcertSeat concertSeat = concertSeatJpaRepository.save(
          ConcertSeat.builder().concertScheduleId(1L).number(1).price(100)
              .isReserved(true)
              .build());

      reservationJpaRepository.save(
          Reservation.builder().concertSeatId(concertSeat.getId()).userId(1L)
              .reservedAt(LocalDateTime.now()
                  .minusMinutes(ConcertConstants.RESERVATION_EXPIRATION_MINUTES + 1))
              .status(ReservationStatus.WAITING)
              .build());

      // when
      concertFacade.expireReservations();

      // then
      final List<Reservation> reservations = reservationJpaRepository.findAll();
      assertThat(reservations).hasSize(1);
      assertThat(reservations.get(0).getStatus()).isEqualTo(ReservationStatus.CANCELED);

      final ConcertSeat updatedConcertSeat = concertSeatJpaRepository.findById(concertSeat.getId())
          .get();
      assertThat(updatedConcertSeat.getIsReserved()).isFalse();
    }
  }
}