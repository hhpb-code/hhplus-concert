package com.example.hhplus.concert.domain.concert.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("예약 내역 단위 테스트")
class ReservationTest {

  @Nested
  @DisplayName("예약 확정")
  class ConfirmTest {


    @Test
    @DisplayName("예약 확정 실패 - 이미 확정된 예약")
    void shouldThrowExceptionWhenAlreadyConfirmed() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.CONFIRMED)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          reservation::confirm);

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ALREADY_PAID.getMessage());
    }

    @Test
    @DisplayName("예약 확정 실패 - 이미 취소된 예약")
    void shouldThrowExceptionWhenAlreadyCanceled() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.CANCELED)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          reservation::confirm);

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ALREADY_CANCELED.getMessage());
    }

    @Test
    @DisplayName("예약 확정 성공")
    void shouldSuccessfullyConfirm() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.WAITING)
          .build();

      // when
      reservation.confirm();

      // then
      assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

  }

  @Nested
  @DisplayName("예약 취소")
  class CancelTest {

    @Test
    @DisplayName("예약 취소 실패 - 이미 취소된 예약")
    void shouldThrowExceptionWhenAlreadyCanceled() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.CANCELED)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          reservation::cancel);

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ALREADY_CANCELED.getMessage());
    }

    @Test
    @DisplayName("예약 취소 실패 - 이미 확정된 예약")
    void shouldThrowExceptionWhenAlreadyConfirmed() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.CONFIRMED)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          reservation::cancel);

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ALREADY_PAID.getMessage());
    }

    @Test
    @DisplayName("예약 취소 성공")
    void shouldSuccessfullyCancel() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.WAITING)
          .build();

      // when
      reservation.cancel();

      // then
      assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);
    }

  }

  @Nested
  @DisplayName("예약 확정 검증")
  class ValidateConfirmConditionsTest {

    @Test
    @DisplayName("예약 확정 검증 실패 - 사용자 불일치")
    void shouldThrowExceptionWhenUserNotMatched() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.WAITING)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          () -> reservation.validateConfirmConditions(2L));

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_USER_NOT_MATCHED.getMessage());
    }

    @Test
    @DisplayName("예약 확정 검증 실패 - 이미 취소된 예약")
    void shouldThrowExceptionWhenAlreadyCanceled() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.CANCELED)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          () -> reservation.validateConfirmConditions(1L));

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ALREADY_CANCELED.getMessage());
    }

    @Test
    @DisplayName("예약 확정 검증 실패 - 이미 확정된 예약")
    void shouldThrowExceptionWhenAlreadyConfirmed() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.CONFIRMED)
          .build();

      // when
      final Exception result = assertThrows(Exception.class,
          () -> reservation.validateConfirmConditions(1L));

      // then
      assertThat(result.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ALREADY_PAID.getMessage());
    }

    @Test
    @DisplayName("예약 확정 검증 성공")
    void shouldSuccessfullyValidateConfirmConditions() {
      // given
      final Reservation reservation = Reservation.builder()
          .concertSeatId(1L)
          .userId(1L)
          .status(ReservationStatus.WAITING)
          .build();

      // when
      reservation.validateConfirmConditions(1L);
    }
  }


}