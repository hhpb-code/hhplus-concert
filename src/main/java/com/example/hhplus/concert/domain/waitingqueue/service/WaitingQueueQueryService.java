package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetActiveTokenByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class WaitingQueueQueryService {

  private final WaitingQueueRepository waitingQueueRepository;

  public WaitingQueueWithPosition getWaitingQueuePosition(
      GetWaitingQueuePositionByUuid query) {
    Long position = waitingQueueRepository.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuidParam(query.uuid()));

    return new WaitingQueueWithPosition(
        query.uuid(),
        position
    );
  }

  public WaitingQueue getWaitingQueueProcessing(GetActiveTokenByUuid query) {
    WaitingQueue waitingQueue = waitingQueueRepository.getActiveToken(
        new GetActiveTokenByUuid(query.uuid()));

    waitingQueue.validateProcessing();

    return waitingQueue;
  }
}
