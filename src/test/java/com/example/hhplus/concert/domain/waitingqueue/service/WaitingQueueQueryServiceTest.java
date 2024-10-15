package com.example.hhplus.concert.domain.waitingqueue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
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
    @DisplayName("대기열 순서 조회 실패 - 대기열이 만료됨")
    void shouldThrowExceptionWhenWaitingQueueIsExpired() {
      // given
      final String uuid = "uuid";
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);
      doReturn(WaitingQueue.builder().status(WaitingQueueStatus.EXPIRED).build())
          .when(waitingQueueReader)
          .getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> target.getWaitingQueuePosition(query));

      // then
      assertThat(result.getErrorCode()).isEqualTo(WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
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
          .status(WaitingQueueStatus.PROCESSING).build())
          .when(waitingQueueReader)
          .getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));

      // when
      final var result = target.getWaitingQueuePosition(query);

      // then
      assertThat(result.getId()).isEqualTo(waitingQueueId);
      assertThat(result.getConcertId()).isEqualTo(concertId);
      assertThat(result.getUuid()).isEqualTo(uuid);
      assertThat(result.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
      assertThat(result.getPosition()).isEqualTo(0);
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
          .status(WaitingQueueStatus.WAITING).build())
          .when(waitingQueueReader)
          .getWaitingQueue(new GetWaitingQueueByUuidWithLockParam(uuid));
      doReturn(1)
          .when(waitingQueueReader)
          .getWaitingQueuePosition(
              new GetWaitingQueuePositionByIdAndConcertIdParam(waitingQueueId, concertId));

      // when
      final var result = target.getWaitingQueuePosition(query);

      // then
      assertThat(result.getId()).isEqualTo(waitingQueueId);
      assertThat(result.getConcertId()).isEqualTo(concertId);
      assertThat(result.getUuid()).isEqualTo(uuid);
      assertThat(result.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
      assertThat(result.getPosition()).isEqualTo(1);
    }

  }

}