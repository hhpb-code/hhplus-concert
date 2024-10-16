package com.example.hhplus.concert.domain.concert.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
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
      final BusinessException result = assertThrows(BusinessException.class,
          reservation::confirm);

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.RESERVATION_ALREADY_PAID);
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
      final BusinessException result = assertThrows(BusinessException.class,
          reservation::confirm);

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.RESERVATION_ALREADY_CANCELED);
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

}