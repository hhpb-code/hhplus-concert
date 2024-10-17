package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
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

}