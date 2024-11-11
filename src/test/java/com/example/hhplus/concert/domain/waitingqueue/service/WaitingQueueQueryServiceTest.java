package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import org.junit.jupiter.api.DisplayName;
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
  private WaitingQueueRepository waitingQueueRepository;


}