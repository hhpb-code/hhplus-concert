package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.CountWaitingQueueByConcertIdAndStatusQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.FindDistinctConcertIdsByStatusQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.CountWaitingQueueByConcertIdAndStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class WaitingQueueQueryService {

  private final WaitingQueueRepository waitingQueueRepository;

  public WaitingQueue getWaitingQueue(GetWaitingQueueByIdQuery query) {
    return waitingQueueRepository.getWaitingQueue(new GetWaitingQueueByIdParam(query.id()));
  }

  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuid query) {
    return waitingQueueRepository.getWaitingQueue(new GetWaitingQueueByUuidParam(query.uuid()));
  }

  @Transactional
  public WaitingQueueWithPosition getWaitingQueuePosition(
      GetWaitingQueuePositionByUuid query) {
    final WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueue(
        new GetWaitingQueueByUuidWithLockParam(query.uuid()));

    waitingQueue.validateNotExpired();

    if (waitingQueue.isProcessing()) {
      return new WaitingQueueWithPosition(
          waitingQueue.getId(),
          waitingQueue.getConcertId(),
          waitingQueue.getUuid(),
          waitingQueue.getStatus(),
          waitingQueue.getExpiredAt(),
          waitingQueue.getCreatedAt(),
          waitingQueue.getUpdatedAt(),
          0
      );
    }

    Integer position = waitingQueueRepository.getWaitingQueuePosition(
        new GetWaitingQueuePositionByIdAndConcertIdParam(waitingQueue.getId(),
            waitingQueue.getConcertId()));

    return new WaitingQueueWithPosition(
        waitingQueue.getId(),
        waitingQueue.getConcertId(),
        waitingQueue.getUuid(),
        waitingQueue.getStatus(),
        waitingQueue.getExpiredAt(),
        waitingQueue.getCreatedAt(),
        waitingQueue.getUpdatedAt(),
        position
    );
  }

  public List<Long> findDistinctConcertIds(FindDistinctConcertIdsByStatusQuery query) {
    return waitingQueueRepository.findDistinctConcertIdByStatus(
        new FindDistinctConcertIdsByStatusParam(query.status()));
  }

  public Integer countWaitingQueueByConcertIdAndStatus(
      CountWaitingQueueByConcertIdAndStatusQuery query) {
    return waitingQueueRepository.countByConcertIdAndStatus(
        new CountWaitingQueueByConcertIdAndStatusParam(
            query.concertId(), query.status()));
  }
}
