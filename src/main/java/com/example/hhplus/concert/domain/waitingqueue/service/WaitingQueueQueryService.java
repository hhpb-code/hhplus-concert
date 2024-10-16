package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingQueueQueryService {

  private final WaitingQueueRepository waitingQueueReader;

  @Transactional(readOnly = true)
  public WaitingQueue getWaitingQueue(GetWaitingQueueByIdQuery query) {
    return waitingQueueReader.getWaitingQueue(new GetWaitingQueueByIdParam(query.id()));
  }

  @Transactional
  public WaitingQueueWithPosition getWaitingQueuePosition(
      GetWaitingQueuePositionByUuid query) {
    final WaitingQueue waitingQueue = waitingQueueReader.getWaitingQueue(
        new GetWaitingQueueByUuidWithLockParam(query.uuid()));

    if (waitingQueue.getStatus() == WaitingQueueStatus.EXPIRED) {
      throw new BusinessException(WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
    }

    final LocalDateTime expiredAt = waitingQueue.getExpiredAt();
    if (expiredAt != null && expiredAt.isBefore(LocalDateTime.now())) {
      throw new BusinessException(WaitingQueueErrorCode.WAITING_QUEUE_EXPIRED);
    }

    if (waitingQueue.getStatus() == WaitingQueueStatus.PROCESSING) {
      return new WaitingQueueWithPosition(waitingQueue, 0);
    }

    Integer position = waitingQueueReader.getWaitingQueuePosition(
        new GetWaitingQueuePositionByIdAndConcertIdParam(waitingQueue.getId(),
            waitingQueue.getConcertId()));

    return new WaitingQueueWithPosition(waitingQueue, position);
  }

}
