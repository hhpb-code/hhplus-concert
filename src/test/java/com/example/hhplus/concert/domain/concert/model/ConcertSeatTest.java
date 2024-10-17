package com.example.hhplus.concert.domain.concert.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ConcertSeat 단위 테스트")
class ConcertSeatTest {

  @Nested
  @DisplayName("예약")
  class Reserve {

    @Test
    @DisplayName("예약 실패")
    void shouldThrowExceptionWhenReserve() {
      // given
      ConcertSeat concertSeat = ConcertSeat.builder()
          .isReserved(true)
          .build();

      // when
      BusinessException businessException = assertThrows(BusinessException.class,
          concertSeat::reserve);

      // then
      assertThat(businessException.getErrorCode()).isEqualTo(
          ConcertErrorCode.CONCERT_SEAT_ALREADY_RESERVED);
    }

    @Test
    @DisplayName("예약 성공")
    void shouldSuccessfullyReserve() {
      // given
      ConcertSeat concertSeat = ConcertSeat.builder()
          .isReserved(false)
          .build();

      // when
      concertSeat.reserve();

      // then
      assertThat(concertSeat.getIsReserved()).isTrue();
    }

  }

  @Nested
  @DisplayName("예약 해지")
  class Release {

    @Test
    @DisplayName("예약 해지 실패 - 이미 해지된 좌석")
    void shouldThrowExceptionWhenRelease() {
      // given
      ConcertSeat concertSeat = ConcertSeat.builder()
          .isReserved(false)
          .build();

      // when
      BusinessException businessException = assertThrows(BusinessException.class,
          concertSeat::release);

      // then
      assertThat(businessException.getErrorCode()).isEqualTo(
          ConcertErrorCode.CONCERT_SEAT_NOT_RESERVED);
    }

    @Test
    @DisplayName("예약 해지 성공")
    void shouldSuccessfullyRelease() {
      // given
      ConcertSeat concertSeat = ConcertSeat.builder()
          .isReserved(true)
          .build();

      // when
      concertSeat.release();

      // then
      assertThat(concertSeat.getIsReserved()).isFalse();
    }

  }

}