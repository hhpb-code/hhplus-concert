package com.example.hhplus.concert.domain.waitingqueue.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 query 단위 테스트")
class WaitingQueueQueryTest {

  @Nested
  @DisplayName("대기열 조회 query By get")
  class GetWaitingQueueQueryTest {

    @Nested
    @DisplayName("대기열 조회 query By uuid")
    class GetWaitingQueueByUuidQueryTest {

      @Test
      @DisplayName("대기열 조회 query 생성 실패 - uuid가 null")
      void shouldThrowExceptionWhenUuidIsNull() {
        // given
        final String uuid = null;

        // when
        final CoreException exception = assertThrows(CoreException.class,
            () -> new GetWaitingQueueByUuid(uuid));

        // then
        assertThat(exception.getErrorType()).isEqualTo(
            ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }

      @Test
      @DisplayName("대기열 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetWaitingQueueByUuidQuery() {
        // given
        final String uuid = "uuid";

        // when
        final GetWaitingQueueByUuid query = new GetWaitingQueueByUuid(uuid);

        // then
        assertThat(query).isNotNull();
        assertThat(query.uuid()).isEqualTo(uuid);
      }
    }
  }

  @Nested
  @DisplayName("대기열 순서 조회 query")
  class GetWaitingQueuePositionByUuidQueryTest {

    @Test
    @DisplayName("대기열 순서 조회 query 생성 실패 - uuid가 null")
    void shouldThrowExceptionWhenUuidIsNull() {
      // given
      final String uuid = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new GetWaitingQueuePositionByUuid(uuid));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
    }

    @Test
    @DisplayName("대기열 순서 조회 query 생성 성공")
    void shouldSuccessfullyCreateGetWaitingQueuePositionByUuidQuery() {
      // given
      final String uuid = "uuid";

      // when
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);

      // then
      assertThat(query).isNotNull();
      assertThat(query.uuid()).isEqualTo(uuid);
    }
  }
}
