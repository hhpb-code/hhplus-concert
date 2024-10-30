package com.example.hhplus.concert.domain.concert.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CancelReservationsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ConfirmReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReleaseConcertSeatsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("콘서트 Command 단위 테스트")
class ConcertCommandTest {

  @Nested
  @DisplayName("콘서트 좌석 예약 Command")
  class ReserveConcertSeatCommandTest {

    @Test
    @DisplayName("콘서트 좌석 예약 Command 생성 실패 - concertSeatId가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdIsNull() {
      // given
      final Long concertSeatId = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ReserveConcertSeatCommand(concertSeatId));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 Command 생성 성공")
    void shouldSuccessfullyCreateReserveConcertSeatCommand() {
      // given
      final Long concertSeatId = 1L;

      // when
      final ReserveConcertSeatCommand result = new ReserveConcertSeatCommand(concertSeatId);

      // then
      assertThat(result).isNotNull();
      assertThat(result.concertSeatId()).isEqualTo(concertSeatId);
    }
  }

  @Nested
  @DisplayName("예약 생성 Command")
  class CreateReservationCommandTest {

    @Test
    @DisplayName("예약 생성 Command 생성 실패 - concertSeatId가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdIsNull() {
      // given
      final Long concertSeatId = null;
      final Long userId = 1L;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new CreateReservationCommand(concertSeatId, userId));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 생성 Command 생성 실패 - userId가 null인 경우")
    void shouldThrowExceptionWhenUserIdIsNull() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new CreateReservationCommand(concertSeatId, userId));

      // then
      assertThat(exception.getErrorType()).isEqualTo(ErrorType.User.USER_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 생성 Command 생성 성공")
    void shouldSuccessfullyCreateCreateReservationCommand() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = 1L;

      // when
      final CreateReservationCommand result = new CreateReservationCommand(concertSeatId, userId);

      // then
      assertThat(result).isNotNull();
      assertThat(result.concertSeatId()).isEqualTo(concertSeatId);
      assertThat(result.userId()).isEqualTo(userId);
    }
  }

  @Nested
  @DisplayName("예약 내역 확정 Command")
  class ConfirmReservationCommandTest {

    @Test
    @DisplayName("예약 내역 확정 Command 생성 실패 - reservationId가 null인 경우")
    void shouldThrowExceptionWhenReservationIdIsNull() {
      // given
      final Long reservationId = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ConfirmReservationCommand(reservationId));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 내역 확정 Command 생성 성공")
    void shouldSuccessfullyCreateConfirmReservationCommand() {
      // given
      final Long reservationId = 1L;

      // when
      final ConfirmReservationCommand result = new ConfirmReservationCommand(reservationId);

      // then
      assertThat(result).isNotNull();
      assertThat(result.reservationId()).isEqualTo(reservationId);
    }
  }

  @Nested
  @DisplayName("예약 만료 Command")
  class CancelReservationsByIdsCommandTest {

    @Test
    @DisplayName("예약 만료 Command 생성 실패 - reservationIds가 null인 경우")
    void shouldThrowExceptionWhenReservationIdsIsNull() {
      // given
      final List<Long> reservationIds = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new CancelReservationsByIdsCommand(reservationIds));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 만료 Command 생성 실패 - reservationIds가 비어있는 경우")
    void shouldThrowExceptionWhenReservationIdsIsEmpty() {
      // given
      final List<Long> reservationIds = List.of();

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new CancelReservationsByIdsCommand(reservationIds));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 만료 Command 생성 성공")
    void shouldSuccessfullyCreateCancelReservationsByIdsCommand() {
      // given
      final List<Long> reservationIds = List.of(1L, 2L, 3L);

      // when
      final CancelReservationsByIdsCommand result = new CancelReservationsByIdsCommand(
          reservationIds);

      // then
      assertThat(result).isNotNull();
      assertThat(result.reservationIds()).isEqualTo(reservationIds);
    }
  }

  @Nested
  @DisplayName("콘서트 좌석 해제 Command")
  class ReleaseConcertSeatsByIdsCommandTest {

    @Test
    @DisplayName("콘서트 좌석 해제 Command 생성 실패 - concertSeatIds가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdsIsNull() {
      // given
      final List<Long> concertSeatIds = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ReleaseConcertSeatsByIdsCommand(concertSeatIds));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 해제 Command 생성 실패 - concertSeatIds가 비어있는 경우")
    void shouldThrowExceptionWhenConcertSeatIdsIsEmpty() {
      // given
      final List<Long> concertSeatIds = List.of();

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ReleaseConcertSeatsByIdsCommand(concertSeatIds));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 해제 Command 생성 성공")
    void shouldSuccessfullyCreateReleaseConcertSeatsByIdsCommand() {
      // given
      final List<Long> concertSeatIds = List.of(1L, 2L, 3L);

      // when
      final ReleaseConcertSeatsByIdsCommand result = new ReleaseConcertSeatsByIdsCommand(
          concertSeatIds);

      // then
      assertThat(result).isNotNull();
      assertThat(result.concertSeatIds()).isEqualTo(concertSeatIds);
    }
  }
}
