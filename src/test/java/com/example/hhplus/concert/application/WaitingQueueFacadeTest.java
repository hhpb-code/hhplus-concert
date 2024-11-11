package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.ActivateWaitingQueuesParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("WaitingQueueFacade 통합 테스트")
class WaitingQueueFacadeTest {

  @Autowired
  private WaitingQueueFacade waitingQueueFacade;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private ConcertSeatJpaRepository concertSeatJpaRepository;

  @Autowired
  private WaitingQueueRepository waitingQueueRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @BeforeEach
  void setUp() {
    concertJpaRepository.deleteAll();
    concertScheduleJpaRepository.deleteAll();
    concertSeatJpaRepository.deleteAll();

    redisTemplate.keys("*").forEach(redisTemplate::delete);
  }

  @Nested
  @DisplayName("대기 토큰 생성")
  class CreateWaitingQueueTokenTest {

    @Test
    @DisplayName("대기 토큰을 생성 성공")
    void shouldSuccessfullyCreateWaitingQueueToken() {
      // given
      // when
      final String result = waitingQueueFacade.createWaitingQueueToken();

      // then
      assertThat(result).isNotNull();
    }

  }

  @Nested
  @DisplayName("토큰 대기 번호 조회")
  class GetWaitingQueueWithPositionTest {

    @Test
    @DisplayName("토큰 대기 번호 조회 실패 - 존재하지 않는 토큰")
    void shouldThrowExceptionWhenGetWaitingQueueWithPositionWithNonExistToken() {
      // given
      final String waitingQueueUuid = "non-exist-waiting-queue-uuid";

      // when
      CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    @Test
    @DisplayName("토큰 대기 번호 조회 성공")
    void shouldSuccessfullyGetWaitingQueueWithPosition() {
      // given
      final String uuid = UUID.randomUUID().toString();
      waitingQueueRepository.addWaitingQueue(uuid);

      // when
      final WaitingQueueWithPosition result = waitingQueueFacade.getWaitingQueueWithPosition(uuid);

      // then
      assertThat(result.uuid()).isEqualTo(uuid);
      assertThat(result.position()).isEqualTo(1);
    }

  }

  @Nested
  @DisplayName("토큰 활성 여부 확인")
  class ValidateWaitingQueueProcessingTest {

    @Test
    @DisplayName("토큰 활성 여부 확인 실패 - 활성 토큰이 아닌 경우")
    void shouldThrowExceptionWhenValidateWaitingQueueProcessingWithNonActiveToken() {
      // given
      final String waitingQueueUuid = "non-active-waiting-queue-uuid";

      // when
      CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.ACTIVE_QUEUE_NOT_FOUND);
    }

    @Test
    @DisplayName("토큰 활성 여부 확인 실패 - 만료 된 토큰인 경우")
    void shouldThrowExceptionWhenValidateWaitingQueueProcessingWithExpiredToken() {
      // given
      final String waitingQueueUuid = UUID.randomUUID().toString();
      waitingQueueRepository.addWaitingQueue(waitingQueueUuid);
      waitingQueueRepository.activateWaitingQueues(
          new ActivateWaitingQueuesParam(1, 1, TimeUnit.MILLISECONDS));

      // when
      CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("토큰 활성 여부 확인 성공")
    void shouldSuccessfullyValidateWaitingQueueProcessing() {
      // given
      final String uuid = UUID.randomUUID().toString();
      waitingQueueRepository.addWaitingQueue(uuid);
      waitingQueueRepository.activateWaitingQueues(
          new ActivateWaitingQueuesParam(1, 1, TimeUnit.MINUTES));

      // when & then
      waitingQueueFacade.validateWaitingQueueProcessing(uuid);
    }

  }

  @Nested
  @DisplayName("대기 토큰 활성화")
  class ActivateWaitingQueuesTest {

    @Test
    @DisplayName("대기 토큰 활성화 성공 - 대기 토큰이 없는 경우")
    void shouldSuccessfullyActivateWaitingQueuesWhenNoWaitingQueues() {
      // given
      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> activeTokens =
          waitingQueueRepository.getAllActiveTokens();
      assertThat(activeTokens).isEmpty();
    }

    @Test
    @DisplayName("대기 토큰 활성화 성공")
    void shouldSuccessfullyActivateWaitingQueues() {
      // given
      final String uuid1 = UUID.randomUUID().toString();
      final String uuid2 = UUID.randomUUID().toString();
      final String uuid3 = UUID.randomUUID().toString();
      waitingQueueRepository.addWaitingQueue(uuid1);
      waitingQueueRepository.addWaitingQueue(uuid2);
      waitingQueueRepository.addWaitingQueue(uuid3);

      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> activeTokens =
          waitingQueueRepository.getAllActiveTokens();
      assertThat(activeTokens).hasSize(3);
    }

  }

}