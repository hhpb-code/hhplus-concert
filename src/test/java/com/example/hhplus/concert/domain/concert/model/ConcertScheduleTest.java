package com.example.hhplus.concert.domain.concert.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ConcertSchedule 단위 테스트")
class ConcertScheduleTest {

  @Nested
  @DisplayName("예약 가능 여부 확인 테스트")
  class ValidateReservationTimeTest {

    @Test
    @DisplayName("예약 불가능한 경우 - 예약 시작 시간 이전")
    void shouldThrowExceptionWhenBeforeReservationStartAt() {
      // given
      var concertSchedule = ConcertSchedule.builder()
          .reservationStartAt(LocalDateTime.now().plusDays(1))
          .reservationEndAt(LocalDateTime.now().plusDays(2)).build();

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> concertSchedule.validateReservationTime());

      // then
      assertThat(
          exception.getErrorType().equals(ErrorType.Concert.CONCERT_SCHEDULE_NOT_RESERVABLE));
    }

    @Test
    @DisplayName("예약 불가능한 경우 - 예약 종료 시간 이후")
    void shouldThrowExceptionWhenAfterReservationEndAt() {
      // given
      var concertSchedule = ConcertSchedule.builder()
          .reservationStartAt(LocalDateTime.now().minusDays(2))
          .reservationEndAt(LocalDateTime.now().minusDays(1)).build();

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> concertSchedule.validateReservationTime());

      // then
      assertThat(
          exception.getErrorType().equals(ErrorType.Concert.CONCERT_SCHEDULE_NOT_RESERVABLE));
    }

    @Test
    @DisplayName("예약 가능한 경우")
    void shouldNotThrowExceptionWhenReservable() {
      // given
      var now = LocalDateTime.now();
      var concertSchedule = ConcertSchedule.builder().reservationStartAt(now)
          .reservationEndAt(now.plusSeconds(1)).build();

      // when
      concertSchedule.validateReservationTime();
    }
  }

}