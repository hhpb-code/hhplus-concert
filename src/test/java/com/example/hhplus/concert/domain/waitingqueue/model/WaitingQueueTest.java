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

  @Nested
  @DisplayName("대기열 만료 검증 테스트")
  class ValidateNotExpiredTest {

    @Test
    @DisplayName("대기열 만료 검증 실패 - 만료된 토큰")
    void shouldThrowExceptionWhenValidateExpiredToken() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.EXPIRED)
          .build();

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          waitingQueue::validateNotExpired);

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 만료 검증 실패 - 만료 시간이 현재 시간보다 늦음")
    void shouldThrowExceptionWhenValidateExpiredAtIsAfterNow() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.PROCESSING)
          .expiredAt(LocalDateTime.now())
          .build();

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          waitingQueue::validateNotExpired);

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 만료 검증 성공")
    void shouldSuccessfullyValidateNotExpired() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .expiredAt(LocalDateTime.now().plusMinutes(1))
          .build();

      // when
      waitingQueue.validateNotExpired();

      // then
      assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
    }

  }

  @Nested
  @DisplayName("대기열 활성 여부 확인")
  class CheckProcessingTest {

    @Nested
    @DisplayName("대기열 활성 여부 확인 is")
    class IsProcessingTest {

      @Test
      @DisplayName("대기열 활성 여부 확인 false - 대기열 상태가 활성 상태가 아님")
      void shouldReturnFalseWhenStatusIsNotProcessing() {
        // given
        final var waitingQueue = WaitingQueue.builder()
            .uuid("uuid")
            .concertId(1L)
            .status(WaitingQueueStatus.WAITING)
            .build();

        // when
        final boolean result = waitingQueue.isProcessing();

        // then
        assertThat(result).isFalse();
      }

      @Test
      @DisplayName("대기열 활성 여부 확인 false - 만료 시간이 현재 시간보다 빠름")
      void shouldReturnFalseWhenExpiredAtIsBeforeNow() {
        // given
        final var waitingQueue = WaitingQueue.builder()
            .uuid("uuid")
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now())
            .build();

        // when
        final boolean result = waitingQueue.isProcessing();

        // then
        assertThat(result).isFalse();
      }

      @Test
      @DisplayName("대기열 활성 여부 확인 true - 대기열 상태가 활성 상태이고 만료 시간이 현재 시간보다 늦음")
      void shouldReturnTrueWhenStatusIsProcessingAndExpiredAtIsAfterNow() {
        // given
        final var waitingQueue = WaitingQueue.builder()
            .uuid("uuid")
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build();

        // when
        final boolean result = waitingQueue.isProcessing();

        // then
        assertThat(result).isTrue();
      }
    }

    @Nested
    @DisplayName("대기열 활성 여부 확인 validate")
    class ValidateProcessingTest {

      @Test
      @DisplayName("대기열 활성 여부 확인 실패 - 대기열 상태가 활성 상태가 아님")
      void shouldThrowExceptionWhenStatusIsNotProcessing() {
        // given
        final var waitingQueue = WaitingQueue.builder()
            .uuid("uuid")
            .concertId(1L)
            .status(WaitingQueueStatus.WAITING)
            .build();

        // when
        final BusinessException result = assertThrows(BusinessException.class,
            waitingQueue::validateProcessing);

        // then
        assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.INVALID_STATUS);
      }

      @Test
      @DisplayName("대기열 활성 여부 확인 실패 - 만료 시간이 현재 시간보다 빠름")
      void shouldThrowExceptionWhenExpiredAtIsBeforeNow() {
        // given
        final var waitingQueue = WaitingQueue.builder()
            .uuid("uuid")
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now())
            .build();

        // when
        final BusinessException result = assertThrows(BusinessException.class,
            waitingQueue::validateProcessing);

        // then
        assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
      }

      @Test
      @DisplayName("대기열 활성 여부 확인 성공 - 대기열 상태가 활성 상태이고 만료 시간이 현재 시간보다 늦음")
      void shouldSuccessfullyValidateProcessing() {
        // given
        final var waitingQueue = WaitingQueue.builder()
            .uuid("uuid")
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build();

        // when
        waitingQueue.validateProcessing();

        // then
        assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
      }
    }

  }

  @Nested
  @DisplayName("대기열 콘서트 ID 검증")
  class ValidateConcertIdTest {

    @Test
    @DisplayName("대기열 콘서트 ID 검증 실패 - 콘서트 ID가 일치하지 않음")
    void shouldThrowExceptionWhenConcertIdIsNotMatched() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .build();

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> waitingQueue.validateConcertId(2L));

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.INVALID_CONCERT_ID);
    }

    @Test
    @DisplayName("대기열 콘서트 ID 검증 성공 - 콘서트 ID가 일치함")
    void shouldSuccessfullyValidateConcertId() {
      // given
      final var waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .concertId(1L)
          .build();

      // when
      waitingQueue.validateConcertId(1L);

      // then
      assertThat(waitingQueue.getConcertId()).isEqualTo(1L);
    }

  }

}