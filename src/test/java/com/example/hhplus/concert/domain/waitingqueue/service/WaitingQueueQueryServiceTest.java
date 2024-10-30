package com.example.hhplus.concert.domain.waitingqueue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("대기열 Query Service 단위 테스트")
class WaitingQueueQueryServiceTest {

  @InjectMocks
  private WaitingQueueQueryService target;

  @Mock
  private WaitingQueueRepository waitingQueueReader;


  @Nested
  @DisplayName("대기열 순서 조회")
  class GetWaitingQueuePosition {

    @Test
    @DisplayName("대기열 순서 조회 실패 - 대기열이 만료 상태")
    void shouldThrowExceptionWhenWaitingQueueIsExpired() {
      // given
      final String uuid = "uuid";
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);
      doReturn(WaitingQueue.builder().status(WaitingQueueStatus.EXPIRED).build()).when(
          waitingQueueReader).getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));

      // when
      final CoreException result = assertThrows(CoreException.class,
          () -> target.getWaitingQueuePosition(query));

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 순서 조회 성공 - 대기열 만료 시간이 지남")
    void shouldThrowExceptionWhenWaitingQueueIsExpiredAfterNow() {
      // given
      final String uuid = "uuid";
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);
      doReturn(WaitingQueue.builder().status(WaitingQueueStatus.PROCESSING)
          .expiredAt(LocalDateTime.now()).build()).when(waitingQueueReader)
          .getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));

      // when
      final CoreException result = assertThrows(CoreException.class,
          () -> target.getWaitingQueuePosition(query));

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 순서 조회 성공 - 대기열이 PROCESSING 상태")
    void shouldSuccessfullyGetWaitingQueuePositionWhenWaitingQueueIsProcessing() {
      // given
      final Long waitingQueueId = 1L;
      final Long concertId = 1L;
      final String uuid = "uuid";
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);
      doReturn(WaitingQueue.builder().id(waitingQueueId).concertId(concertId).uuid(uuid)
          .status(WaitingQueueStatus.PROCESSING).expiredAt(LocalDateTime.now().plusMinutes(1))
          .build()).when(waitingQueueReader)
          .getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));

      // when
      final var result = target.getWaitingQueuePosition(query);

      // then
      assertThat(result.id()).isEqualTo(waitingQueueId);
      assertThat(result.concertId()).isEqualTo(concertId);
      assertThat(result.uuid()).isEqualTo(uuid);
      assertThat(result.status()).isEqualTo(WaitingQueueStatus.PROCESSING);
      assertThat(result.position()).isEqualTo(0);
    }

    @Test
    @DisplayName("대기열 순서 조회 성공 - 대기열이 WAITING 상태")
    void shouldSuccessfullyGetWaitingQueuePositionWhenWaitingQueueIsWaiting() {
      // given
      final Long waitingQueueId = 1L;
      final Long concertId = 1L;
      final String uuid = "uuid";
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);
      doReturn(WaitingQueue.builder().id(waitingQueueId).concertId(concertId).uuid(uuid)
          .status(WaitingQueueStatus.WAITING).build()).when(waitingQueueReader)
          .getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));
      doReturn(1).when(waitingQueueReader).getWaitingQueuePosition(
          new GetWaitingQueuePositionByIdAndConcertIdParam(waitingQueueId, concertId));

      // when
      final var result = target.getWaitingQueuePosition(query);

      // then
      assertThat(result.id()).isEqualTo(waitingQueueId);
      assertThat(result.concertId()).isEqualTo(concertId);
      assertThat(result.uuid()).isEqualTo(uuid);
      assertThat(result.status()).isEqualTo(WaitingQueueStatus.WAITING);
      assertThat(result.position()).isEqualTo(1);
    }

  }

}