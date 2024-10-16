package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import com.example.hhplus.concert.infra.db.waitingqueue.WaitingQueueJpaRepository;
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

@SpringBootTest
@DisplayName("WaitingQueueFacade 통합 테스트")
class WaitingQueueFacadeTest {

  @Autowired
  private WaitingQueueFacade waitingQueueFacade;

  @Autowired
  private WaitingQueueJpaRepository waitingQueueJpaRepository;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private ConcertSeatJpaRepository concertSeatJpaRepository;

  @BeforeEach
  void setUp() {
    waitingQueueJpaRepository.deleteAll();
    concertJpaRepository.deleteAll();
    concertScheduleJpaRepository.deleteAll();
    concertSeatJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("대기열 토큰 생성")
  class CreateWaitingQueueToken {

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
      assertThat(waitingQueue.getUpdatedAt()).isNull();
    }

  }

  @Nested
  @DisplayName("대기열 토큰 순서 조회")
  class GetWaitingQueuePosition {

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - 존재하지 않는 토큰")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithNotExistsToken() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();

      // when
      final BusinessException result = assertThrows(
          BusinessException.class, () -> {
            waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
          });

      // then
      assertThat(result.getErrorCode()).isEqualTo(
          WaitingQueueErrorCode.WAITING_QUEUE_NOT_FOUND);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - 만료된 토큰(대기열 상태가 EXPIRED)")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithExpiredToken() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      waitingQueueJpaRepository.save(WaitingQueue.builder()
          .uuid(waitingQueueTokenUuid)
          .concertId(1L)
          .status(WaitingQueueStatus.EXPIRED)
          .expiredAt(LocalDateTime.now())
          .build());

      // when
      final BusinessException result = assertThrows(
          BusinessException.class, () -> {
            waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
          });

      // then
      assertThat(result.getErrorCode()).isEqualTo(
          WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 실패 - 만료된 토큰(만료 시간이 현재 시간보다 이전)")
    void shouldThrowBusinessExceptionWhenGetWaitingQueuePositionWithExpiredTokenByExpiredAt() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      waitingQueueJpaRepository.save(WaitingQueue.builder()
          .uuid(waitingQueueTokenUuid)
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .expiredAt(LocalDateTime.now())
          .build());

      // when
      final BusinessException result = assertThrows(
          BusinessException.class, () -> {
            waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid);
          });

      // then
      assertThat(result.getErrorCode()).isEqualTo(
          WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 성공 - 대기열 상태가 PROCESSING")
    void shouldGetWaitingQueueWithPositionWhenStatusIsProcessing() {
      // given
      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      final WaitingQueue waitingQueue = waitingQueueJpaRepository.save(WaitingQueue.builder()
          .uuid(waitingQueueTokenUuid)
          .concertId(1L)
          .status(WaitingQueueStatus.PROCESSING)
          .expiredAt(LocalDateTime.now().plusMinutes(1))
          .build());

      // when
      final WaitingQueueWithPosition result = waitingQueueFacade
          .getWaitingQueueWithPosition(waitingQueueTokenUuid);

      // then
      assertThat(result.getId()).isEqualTo(waitingQueue.getId());
      assertThat(result.getConcertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.getUuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.getStatus()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.getCreatedAt()).isEqualTo(waitingQueue.getCreatedAt());
      assertThat(result.getUpdatedAt()).isEqualTo(waitingQueue.getUpdatedAt());
      assertThat(result.getPosition()).isZero();

    }

    @Test
    @DisplayName("대기열 토큰 순서 조회 성공 - 대기열 상태가 WAITING")
    void shouldGetWaitingQueueWithPositionWhenStatusIsWaiting() {
      // given
      waitingQueueJpaRepository.save(WaitingQueue.builder()
          .uuid(UUID.randomUUID().toString())
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .build());

      final String waitingQueueTokenUuid = UUID.randomUUID().toString();
      final WaitingQueue waitingQueue = waitingQueueJpaRepository.save(WaitingQueue.builder()
          .uuid(waitingQueueTokenUuid)
          .concertId(1L)
          .status(WaitingQueueStatus.WAITING)
          .build());

      // when
      final WaitingQueueWithPosition result = waitingQueueFacade
          .getWaitingQueueWithPosition(waitingQueueTokenUuid);

      // then
      assertThat(result.getId()).isEqualTo(waitingQueue.getId());
      assertThat(result.getConcertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.getUuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.getStatus()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.getCreatedAt()).isEqualTo(waitingQueue.getCreatedAt());
      assertThat(result.getUpdatedAt()).isEqualTo(waitingQueue.getUpdatedAt());
      assertThat(result.getPosition()).isEqualTo(1);
    }

  }

  @Nested
  @DisplayName("대기열 활성 여부 검증")
  class validateWaitingQueueProcessingTest {

    @Nested
    @DisplayName("대기열 활성 여부 검증 by concertId")
    class validateWaitingQueueProcessingAndConcertIdTest {

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 EXPIRED")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithExpiredToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(concertId)
            .status(WaitingQueueStatus.EXPIRED)
            .expiredAt(LocalDateTime.now())
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndConcertId(waitingQueueTokenUuid,
                  concertId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 WAITING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithWaitingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(concertId)
            .status(WaitingQueueStatus.WAITING)
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndConcertId(waitingQueueTokenUuid,
                  concertId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.INVALID_STATUS);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 expireAt이 현재 시간보다 늦음")
      void shouldSuccessfullyValidateProcessing() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(concertId)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now())
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndConcertId(waitingQueueTokenUuid,
                  concertId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);

      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - concertId가 일치하지 않음")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithNotMatchConcertId() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        final Long notMatchConcertId = 2L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(concertId)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndConcertId(waitingQueueTokenUuid,
                  notMatchConcertId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.INVALID_CONCERT_ID);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 성공 - 대기열 상태가 PROCESSING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithProcessingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long concertId = 1L;
        final WaitingQueue waitingQueue = waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(concertId)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build());

        // when
        waitingQueueFacade.validateWaitingQueueProcessingAndConcertId(waitingQueueTokenUuid,
            concertId);

        // then
        assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
        assertThat(waitingQueue.getUuid()).isEqualTo(waitingQueueTokenUuid);
        assertThat(waitingQueue.getExpiredAt()).isAfter(LocalDateTime.now());
      }
    }

    @Nested
    @DisplayName("대기열 활성 여부 검증 by scheduleId")
    class validateWaitingQueueProcessingAndScheduleIdTest {

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 EXPIRED")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithExpiredToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long scheduleId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.EXPIRED)
            .expiredAt(LocalDateTime.now())
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
                  scheduleId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 WAITING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithWaitingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long scheduleId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.WAITING)
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
                  scheduleId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.INVALID_STATUS);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 expireAt이 현재 시간보다 늦음")
      void shouldSuccessfullyValidateProcessing() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long scheduleId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now())
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
                  scheduleId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);

      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - concertId가 일치하지 않음")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithNotMatchConcertId() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long notMatchConcertId = 2L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build());
        final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
            ConcertSchedule.builder()
                .concertId(notMatchConcertId)
                .concertAt(LocalDateTime.now())
                .reservationStartAt(LocalDateTime.now())
                .reservationEndAt(LocalDateTime.now())
                .build());
        final Long scheduleId = concertSchedule.getId();

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
                  scheduleId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.INVALID_CONCERT_ID);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 성공 - 대기열 상태가 PROCESSING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithProcessingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final WaitingQueue waitingQueue = waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build());
        final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
            ConcertSchedule.builder()
                .concertId(1L)
                .concertAt(LocalDateTime.now())
                .reservationStartAt(LocalDateTime.now())
                .reservationEndAt(LocalDateTime.now())
                .build());
        final Long scheduleId = concertSchedule.getId();

        // when
        waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
            scheduleId);

        // then
        assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
        assertThat(waitingQueue.getUuid()).isEqualTo(waitingQueueTokenUuid);
        assertThat(waitingQueue.getExpiredAt()).isAfter(LocalDateTime.now());
      }

    }

    @Nested
    @DisplayName("대기열 활성 여부 검증 by seatId")
    class validateWaitingQueueProcessingAndSeatIdTest {

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 EXPIRED")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithExpiredToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long seatId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.EXPIRED)
            .expiredAt(LocalDateTime.now())
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndSeatId(waitingQueueTokenUuid,
                  seatId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 상태가 WAITING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithWaitingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long seatId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.WAITING)
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndSeatId(waitingQueueTokenUuid,
                  seatId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.INVALID_STATUS);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - 대기열 expireAt이 현재 시간보다 늦음")
      void shouldSuccessfullyValidateProcessing() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long seatId = 1L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now())
            .build());

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndSeatId(waitingQueueTokenUuid,
                  seatId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);

      }

      @Test
      @DisplayName("대기열 활성 여부 검증 실패 - concertId가 일치하지 않음")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithNotMatchConcertId() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final Long notMatchConcertId = 2L;
        waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build());
        final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
            ConcertSchedule.builder()
                .concertId(notMatchConcertId)
                .concertAt(LocalDateTime.now())
                .reservationStartAt(LocalDateTime.now())
                .reservationEndAt(LocalDateTime.now())
                .build());
        final ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.builder()
            .concertScheduleId(concertSchedule.getId())
            .number(1)
            .price(10000)
            .isReserved(false)
            .build());
        final Long seatId = concertSeat.getId();

        // when
        final BusinessException result = assertThrows(
            BusinessException.class, () -> {
              waitingQueueFacade.validateWaitingQueueProcessingAndSeatId(waitingQueueTokenUuid,
                  seatId);
            });

        // then
        assertThat(result.getErrorCode()).isEqualTo(
            WaitingQueueErrorCode.INVALID_CONCERT_ID);
      }

      @Test
      @DisplayName("대기열 활성 여부 검증 성공 - 대기열 상태가 PROCESSING")
      void shouldThrowBusinessExceptionWhenValidateProcessingWithProcessingToken() {
        // given
        final String waitingQueueTokenUuid = UUID.randomUUID().toString();
        final WaitingQueue waitingQueue = waitingQueueJpaRepository.save(WaitingQueue.builder()
            .uuid(waitingQueueTokenUuid)
            .concertId(1L)
            .status(WaitingQueueStatus.PROCESSING)
            .expiredAt(LocalDateTime.now().plusMinutes(1))
            .build());
        final ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(
            ConcertSchedule.builder()
                .concertId(1L)
                .concertAt(LocalDateTime.now())
                .reservationStartAt(LocalDateTime.now())
                .reservationEndAt(LocalDateTime.now())
                .build());
        final ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.builder()
            .concertScheduleId(concertSchedule.getId())
            .number(1)
            .price(10000)
            .isReserved(false)
            .build());
        final Long seatId = concertSeat.getId();

        // when
        waitingQueueFacade.validateWaitingQueueProcessingAndSeatId(waitingQueueTokenUuid,
            seatId);

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
    @DisplayName("대기열 활성화 성공 - 활성 자리가 없는 경우")
    void shouldSuccessfullyActivateWaitingQueuesWhenNotExistsAvailableSlots() {
      // given
      final Concert concert = concertJpaRepository.save(Concert.builder()
          .title("title")
          .description("description")
          .build());
      final Long concertId = concert.getId();

      IntStream.range(0, WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT)
          .forEach(i -> waitingQueueJpaRepository.save(WaitingQueue.builder()
              .concertId(concertId)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.PROCESSING)
              .expiredAt(LocalDateTime.now().plusMinutes(1))
              .build()));
      final int waitingQueueCount = 5;
      IntStream.range(0, waitingQueueCount)
          .forEach(i -> waitingQueueJpaRepository.save(WaitingQueue.builder()
              .concertId(concertId)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .build()));

      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findAll();
      assertThat(waitingQueues).hasSize(
          waitingQueueCount + WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT);

      for (int i = 0; i < waitingQueueCount; i++) {
        final WaitingQueue waitingQueue = waitingQueues.get(i);

        if (i < WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT) {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
          assertThat(waitingQueue.getExpiredAt()).isAfter(LocalDateTime.now());
        } else {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
          assertThat(waitingQueue.getExpiredAt()).isNull();
        }
      }
    }

    @Test
    @DisplayName("대기열 활성화 성공 - 대기열이 없는 경우")
    void shouldSuccessfullyActivateWaitingQueuesWhenNotExistsWaitingQueue() {
      // given

      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findAll();
      assertThat(waitingQueues).isEmpty();
    }

    @Test
    @DisplayName("대기열 활성화 성공 - 대기열이 있는 경우")
    void shouldSuccessfullyActivateWaitingQueuesWhenExistsWaitingQueue() {
      // given
      final Concert concert = concertJpaRepository.save(Concert.builder()
          .title("title")
          .description("description")
          .build());
      final Long concertId = concert.getId();
      final int waitingQueueCount = WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT + 5;
      IntStream.range(0, waitingQueueCount)
          .forEach(i -> waitingQueueJpaRepository.save(WaitingQueue.builder()
              .concertId(concertId)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .build()));

      // when
      waitingQueueFacade.activateWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findAll();
      assertThat(waitingQueues).hasSize(waitingQueueCount);
      for (int i = 0; i < waitingQueueCount; i++) {
        final WaitingQueue waitingQueue = waitingQueues.get(i);

        if (i < WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT) {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
          assertThat(waitingQueue.getExpiredAt()).isAfter(LocalDateTime.now());
        } else {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
          assertThat(waitingQueue.getExpiredAt()).isNull();
        }
      }
    }
  }

  @Nested
  @DisplayName("대기열 만료")
  class ExpireWaitingQueues {

    @Test
    @DisplayName("대기열 만료 성공 - 만료 대기열이 없는 경우")
    void shouldSuccessfullyExpireWaitingQueuesWhenNotExistsExpiredWaitingQueue() {
      // given

      // when
      waitingQueueFacade.expireWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findAll();
      assertThat(waitingQueues).isEmpty();
    }

    @Test
    @DisplayName("대기열 만료 성공 - 만료 대기열이 있는 경우")
    void shouldSuccessfullyExpireWaitingQueuesWhenExistsExpiredWaitingQueue() {
      // given
      final Concert concert = concertJpaRepository.save(Concert.builder()
          .title("title")
          .description("description")
          .build());
      final Long concertId = concert.getId();

      final int expiredWaitingQueueCount = 3;
      IntStream.range(0, expiredWaitingQueueCount)
          .forEach(i -> waitingQueueJpaRepository.save(WaitingQueue.builder()
              .concertId(concertId)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.PROCESSING)
              .expiredAt(LocalDateTime.now().minusMinutes(1))
              .build()));

      final int waitingQueueCount = 5;
      IntStream.range(0, waitingQueueCount)
          .forEach(i -> waitingQueueJpaRepository.save(WaitingQueue.builder()
              .concertId(concertId)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .build()));

      // when
      waitingQueueFacade.expireWaitingQueues();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findAll();
      assertThat(waitingQueues).hasSize(waitingQueueCount + expiredWaitingQueueCount);

      for (int i = 0; i < waitingQueueCount; i++) {
        final WaitingQueue waitingQueue = waitingQueues.get(i);

        if (i < expiredWaitingQueueCount) {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.EXPIRED);
          assertThat(waitingQueue.getExpiredAt()).isBefore(LocalDateTime.now());
        } else {
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
          assertThat(waitingQueue.getExpiredAt()).isNull();
        }
      }
    }

  }

}