package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.infra.db.waitingqueue.WaitingQueueJpaRepository;
import java.time.LocalDateTime;
import java.util.UUID;
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

  @BeforeEach
  void setUp() {
    waitingQueueJpaRepository.deleteAll();
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
      final BusinessException result = org.junit.jupiter.api.Assertions.assertThrows(
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
      final BusinessException result = org.junit.jupiter.api.Assertions.assertThrows(
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
      final BusinessException result = org.junit.jupiter.api.Assertions.assertThrows(
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

}