package com.example.hhplus.concert.domain.concert.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
      assertEquals(ConcertErrorCode.CONCERT_SEAT_ALREADY_RESERVED,
          businessException.getErrorCode());
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
      assertEquals(true, concertSeat.getIsReserved());
    }

  }

}