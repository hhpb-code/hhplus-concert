package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
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

}
