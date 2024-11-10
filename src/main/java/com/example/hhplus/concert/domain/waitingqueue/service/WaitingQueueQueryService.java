package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidAndConcertIdParam;
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

    waitingQueue.validateNotExpired();

    Integer position = waitingQueueRepository.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuidAndConcertIdParam(waitingQueue.getUuid(),
            waitingQueue.getConcertId()));

    return new WaitingQueueWithPosition(
        waitingQueue.getConcertId(),
        waitingQueue.getUuid(),
        waitingQueue.getStatus(),
        waitingQueue.getExpiredAt(),
        waitingQueue.getCreatedAt(),
        position
    );
  }
}
