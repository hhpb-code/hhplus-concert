package com.example.hhplus.concert.domain.waitingqueue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("WaitingQueue 단위 테스트")
@ExtendWith(MockitoExtension.class)
class WaitingQueueTest {

  @Nested
  @DisplayName("대기열 활성 검증")
  class ValidateProcessingTest {

    @Test
    @DisplayName("대기열 활성 검증 실패 - 대기열 만료")
    void shouldFailValidateProcessingWhenWaitingQueueExpired() {
      // given
      WaitingQueue waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .expiredAt(LocalDateTime.now().minusMinutes(1))
          .build();

      // when
      CoreException result = assertThrows(CoreException.class, waitingQueue::validateProcessing);

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 활성 검증 성공")
    void shouldSuccessfullyValidateProcessing() {
      // given
      WaitingQueue waitingQueue = WaitingQueue.builder()
          .uuid("uuid")
          .expiredAt(LocalDateTime.now().plusMinutes(1))
          .build();

      // when
      waitingQueue.validateProcessing();

      // then
      assertThat(waitingQueue).isNotNull();
    }
  }

}