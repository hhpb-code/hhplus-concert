package com.example.hhplus.concert.domain.waitingqueue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 단위 테스트")
class WaitingQueueTest {

  @Nested
  @DisplayName("대기열 토큰 활성화 테스트")
  class ActivateTest {

    @Test
    @DisplayName("대기열 토큰 활성화 실패 - 이미 활성화된 토큰")
    void shouldThrowExceptionWhenActivateAlreadyActivatedToken() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.PROCESSING)
          .build();
      final LocalDateTime expiredAt = LocalDateTime.now()
          .plusMinutes(WaitingQueueConstants.WAITING_QUEUE_EXPIRE_MINUTES);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> waitingQueue.activate(expiredAt));

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.INVALID_STATUS);
    }

    @Test
    @DisplayName("대기열 토큰 활성화 실패 - 만료된 토큰")
    void shouldThrowExceptionWhenActivateExpiredToken() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .expiredAt(LocalDateTime.now().minusMinutes(1))
          .build();
      final LocalDateTime expiredAt = LocalDateTime.now()
          .plusMinutes(WaitingQueueConstants.WAITING_QUEUE_EXPIRE_MINUTES);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> waitingQueue.activate(expiredAt));

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.INVALID_STATUS);
    }

    @Test
    @DisplayName("대기열 토큰 활성화 실패 - 만료 시간이 현재 시간보다 빠름")
    void shouldThrowExceptionWhenActivateExpiredAtIsBeforeNow() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .build();
      final LocalDateTime expiredAt = LocalDateTime.now()
          .minusMinutes(1);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> waitingQueue.activate(expiredAt));

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.INVALID_EXPIRED_AT);
    }


    @Test
    @DisplayName("대기열 토큰 활성화 성공")
    void shouldSuccessfullyActivateToken() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .build();
      final LocalDateTime expiredAt = LocalDateTime.now()
          .plusMinutes(WaitingQueueConstants.WAITING_QUEUE_EXPIRE_MINUTES);

      // when
      waitingQueue.activate(expiredAt);

      // then
      assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
      assertThat(waitingQueue.getExpiredAt()).isEqualTo(expiredAt);
    }

  }

}