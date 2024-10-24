package com.example.hhplus.concert.domain.waitingqueue.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.CountWaitingQueueByConcertIdAndStatusQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.FindDistinctConcertIdsByStatusQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 query 단위 테스트")
class WaitingQueueQueryTest {

  @Nested
  @DisplayName("대기열 조회 query By get")
  class GetWaitingQueueQueryTest {

    @Nested
    @DisplayName("대기열 조회 query By id")
    class GetWaitingQueueByIdQueryTest {

      @Test
      @DisplayName("대기열 조회 query 생성 실패 - id가 null")
      void shouldThrowExceptionWhenIdIsNull() {
        // given
        final Long id = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetWaitingQueueByIdQuery(id));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.WaitingQueue.WAITING_QUEUE_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("대기열 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetWaitingQueueByIdQuery() {
        // given
        final Long id = 1L;

        // when
        final GetWaitingQueueByIdQuery query = new GetWaitingQueueByIdQuery(id);

        // then
        assertThat(query).isNotNull();
        assertThat(query.id()).isEqualTo(id);
      }
    }

    @Nested
    @DisplayName("대기열 조회 query By uuid")
    class GetWaitingQueueByUuidQueryTest {

      @Test
      @DisplayName("대기열 조회 query 생성 실패 - uuid가 null")
      void shouldThrowExceptionWhenUuidIsNull() {
        // given
        final String uuid = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetWaitingQueueByUuid(uuid));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY.getMessage());
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
      final Exception exception = assertThrows(Exception.class,
          () -> new GetWaitingQueuePositionByUuid(uuid));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY.getMessage());
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

  @Nested
  @DisplayName("대기열의 concert id 목록 조회 query By Find")
  class FindDistinctConcertIdsByStatusQueryTest {

    @Test
    @DisplayName("대기열의 concert id 목록 조회 query 생성 실패 - status가 null")
    void shouldThrowExceptionWhenStatusIsNull() {
      // given
      final WaitingQueueStatus status = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new FindDistinctConcertIdsByStatusQuery(status));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.WaitingQueue.WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("대기열의 concert id 목록 조회 query 생성 성공")
    void shouldSuccessfullyCreateFindConcertIdsByStatusQuery() {
      // given
      final WaitingQueueStatus status = WaitingQueueStatus.WAITING;

      // when
      final FindDistinctConcertIdsByStatusQuery query = new FindDistinctConcertIdsByStatusQuery(
          status);

      // then
      assertThat(query).isNotNull();
      assertThat(query.status()).isEqualTo(status);
    }
  }

  @Nested
  @DisplayName("대기열의 count query")
  class CountWaitingQueueByConcertIdAndStatusQueryTest {

    @Test
    @DisplayName("대기열의 count query 생성 실패 - concertId가 null")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;
      final WaitingQueueStatus status = WaitingQueueStatus.WAITING;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CountWaitingQueueByConcertIdAndStatusQuery(concertId, status));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL.getMessage());
    }


    @Test
    @DisplayName("대기열의 count query 생성 실패 - status가 null")
    void shouldThrowExceptionWhenStatusIsNull() {
      // given
      final Long concertId = 1L;
      final WaitingQueueStatus status = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CountWaitingQueueByConcertIdAndStatusQuery(concertId, status));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.WaitingQueue.WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("대기열의 count query 생성 성공")
    void shouldSuccessfullyCreateCountWaitingQueueByConcertIdAndStatusQuery() {
      // given
      final Long concertId = 1L;
      final WaitingQueueStatus status = WaitingQueueStatus.WAITING;

      // when
      final CountWaitingQueueByConcertIdAndStatusQuery query = new CountWaitingQueueByConcertIdAndStatusQuery(
          concertId, status);

      // then
      assertThat(query).isNotNull();
      assertThat(query.concertId()).isEqualTo(concertId);
      assertThat(query.status()).isEqualTo(status);
    }
  }
}
