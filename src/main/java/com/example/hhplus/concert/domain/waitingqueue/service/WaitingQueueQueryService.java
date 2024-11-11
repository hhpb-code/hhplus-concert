package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
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

  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuid query) {
    return waitingQueueRepository.getWaitingQueue(new GetWaitingQueueByUuidParam(query.uuid()));
  }

  public WaitingQueueWithPosition getWaitingQueuePosition(
      GetWaitingQueuePositionByUuid query) {
    final WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueue(
        new GetWaitingQueueByUuidParam(query.uuid()));

    Integer position = waitingQueueRepository.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuidParam(waitingQueue.getUuid()));

    return new WaitingQueueWithPosition(
        waitingQueue.getUuid(),
        position
    );
  }
}
