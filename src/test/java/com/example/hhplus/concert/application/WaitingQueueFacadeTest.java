package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import com.example.hhplus.concert.infra.redis.waitingqueue.WaitingQueueRedisRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
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
  private WaitingQueueRedisRepository waitingQueueRedisRepository;

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
  @DisplayName("대기열 토큰 생성")
  class CreateWaitingQueueToken {

    @Test
    @DisplayName("대기열 토큰 생성 실패 - concertId가 null인 경우")
    void shouldThrowBusinessExceptionWhenCreateWaitingQueueTokenWithNullConcertId() {
      // given
      final Long concertId = null;

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.createWaitingQueueToken(concertId);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("대기열 토큰 생성 성공")
    void shouldCreateWaitingQueueToken() {
      // given
      final Long concertId = 1L;

      // when
      final WaitingQueue waitingQueue = waitingQueueFacade.createWaitingQueueToken(concertId);

      // then
      assertThat(waitingQueue).isNotNull();
      assertThat(waitingQueue.getConcertId()).isEqualTo(concertId);
      assertThat(waitingQueue.getExpiredAt()).isNull();
      assertThat(waitingQueue.getUuid()).isNotNull();
      assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
      assertThat(waitingQueue.getCreatedAt()).isNotNull();
    }

  }

  @Nested
  @DisplayName("대기열 토큰 순서 조회")
  class GetWaitingQueuePosition {

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - waitingQueueTokenUuid가 null인 경우")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithNullToken() {
      // given
      final String waitingQueueTokenUuid = null;

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - waitingQueueTokenUuid가 빈 문자열인 경우")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithEmptyToken() {
      // given
      final String waitingQueueTokenUuid = "";

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - 존재하지 않는 토큰")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithNotExistsToken() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - 만료된 토큰(대기열 상태가 EXPIRED)")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithExpiredToken() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      waitingQueueRedisRepository.save(
          WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(1L)
              .status(WaitingQueueStatus.EXPIRED).expiredAt(LocalDateTime.now()).build());

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - 만료된 토큰(만료 시간이 현재 시간보다 이전)")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithExpiredTokenByExpiredAt() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      waitingQueueRedisRepository.save(
          WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(1L)
              .status(WaitingQueueStatus.WAITING).expiredAt(LocalDateTime.now()).build());

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 성공 - 대기열 상태가 PROCESSING")
    void shouldGetWaitingQueueWithPositionWhenStatusIsProcessing() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      final WaitingQueue waitingQueue = waitingQueueRedisRepository.save(
          WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(1L)
              .status(WaitingQueueStatus.PROCESSING).expiredAt(LocalDateTime.now().plusMinutes(1))
              .build());

      // when
      final WaitingQueueWithPosition result = waitingQueueFacade.getWaitingQueueWithPosition(
          waitingQueueTokenUuid);

      // then
      assertThat(result.concertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.uuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.status()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.createdAt()).isEqualTo(waitingQueue.getCreatedAt());
      assertThat(result.position()).isZero();

    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 성공 - 대기열 상태가 WAITING")
    void shouldGetWaitingQueueWithPositionWhenStatusIsWaiting() {
      // given
      waitingQueueRedisRepository.save(
          WaitingQueue.builder().uuid(UUID.randomUUID().toString()).concertId(1L)
              .status(WaitingQueueStatus.WAITING).
              build());

      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      final WaitingQueue waitingQueue = waitingQueueRedisRepository.save(
          WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(1L)
              .status(WaitingQueueStatus.WAITING).build());

      // when
      final WaitingQueueWithPosition result = waitingQueueFacade.getWaitingQueueWithPosition(
          waitingQueueTokenUuid);

      // then
      assertThat(result.concertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.uuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.status()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.createdAt()).isEqualTo(waitingQueue.getCreatedAt());
      assertThat(result.position()).isEqualTo(2);
    }

  }

  @Nested
  @DisplayName("대기열 활성 여부 검증")
  class validateWaitingQueueProcessingTest {

    @Nested
    @DisplayName("대기열 활성 여부 검증 by uuid")
    class validateWaitingQueueProcessingAndConcertIdTest {

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - waitingQueueTokenUuid가 null인 경우")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithNullToken() {
        // given
        final String waitingQueueTokenUuid = null;

        // when
        final CoreException result = assertThrows(CoreException.class, () -> {
          waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);
        });

        // then
        assertThat(result.getErrorType()).isEqualTo(
            ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - waitingQueueTokenUuid가 빈 문자열인 경우")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithEmptyToken() {
        // given
        final String waitingQueueTokenUuid = "";

        // when
        final CoreException result = assertThrows(CoreException.class, () -> {
          waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);
        });

        // then
        assertThat(result.getErrorType()).isEqualTo(
            ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 존재하지 않는 토큰")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithNotExistsToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;

        // when
        final CoreException result = assertThrows(CoreException.class, () -> {
          waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);
        });

        // then
        assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 EXPIRED")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithExpiredToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        waitingQueueRedisRepository.save(
            WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(concertId)
                .status(WaitingQueueStatus.EXPIRED).expiredAt(LocalDateTime.now()).build());

        // when
        final CoreException result = assertThrows(CoreException.class, () -> {
          waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);
        });

        // then
        assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 WAITING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithWaitingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        waitingQueueRedisRepository.save(
            WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(concertId)
                .status(WaitingQueueStatus.WAITING).build());

        // when
        final CoreException result = assertThrows(CoreException.class, () -> {
          waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);
        });

        // then
        assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.INVALID_STATUS);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 expireAt이 현재 시간보다 늦음")
      void shouldSuccessfullyValidateProcessing() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        waitingQueueRedisRepository.save(
            WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(concertId)
                .status(WaitingQueueStatus.PROCESSING).expiredAt(LocalDateTime.now()).build());

        // when
        final CoreException result = assertThrows(CoreException.class, () -> {
          waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);
        });

        // then
        assertThat(result.getErrorType()).isEqualTo(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);

      }

      @Test
      @DisplayName("대기열 활성 여부 검증 성공 - 대기열 상태가 PROCESSING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithProcessingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        final WaitingQueue waitingQueue = waitingQueueRedisRepository.save(
            WaitingQueue.builder().uuid(waitingQueueTokenUuid).concertId(concertId)
                .status(WaitingQueueStatus.PROCESSING).expiredAt(LocalDateTime.now().plusMinutes(1))
                .build());

        // when
        waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);

        // then
        assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
        assertThat(waitingQueue.getUuid()).isEqualTo(waitingQueueTokenUuid);
        assertThat(waitingQueue.getExpiredAt()).isAfter(LocalDateTime.now());
      }
    }


  }

  @Nested
  @DisplayName("대기열 활성화")
  class ActivateWaitingQueues {

    @Test
    @DisplayName("대기열 활성화 성공 - 대기열이 없는 경우")
    void shouldSuccessfullyActivateWaitingQueuesWhenNotExistsWaitingQueue() {
      // given

      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueRedisRepository.findAllWaitingQueues();
      assertThat(waitingQueues).isEmpty();
    }

    @Test
    @DisplayName("대기열 활성화 성공 - 대기열이 있는 경우")
    void shouldSuccessfullyActivateWaitingQueuesWhenExistsWaitingQueue() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();
      final int waitingQueueCount = WaitingQueueConstants.ADD_PROCESSING_COUNT + 10;
      IntStream.range(0, waitingQueueCount).forEach(i -> waitingQueueRedisRepository.save(
          WaitingQueue.builder().concertId(concertId).uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING).build()));

      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueRedisRepository.findAllWaitingQueues();
      assertThat(waitingQueues).hasSize(waitingQueueCount);
      for (int i = 0; i < waitingQueueCount; i++) {
        final WaitingQueue waitingQueue = waitingQueues.get(i);

        if (i < WaitingQueueConstants.ADD_PROCESSING_COUNT) {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
          assertThat(waitingQueue.getExpiredAt()).isAfter(LocalDateTime.now());
        } else {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
          assertThat(waitingQueue.getExpiredAt()).isNull();
        }
      }
    }
  }

}