package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueCommandService;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WaitingQueueFacade {

  private final WaitingQueueQueryService waitingQueueQueryService;

  private final WaitingQueueCommandService waitingQueueCommandService;


  @Transactional
  public WaitingQueue createWaitingQueueToken(Long concertId) {
    Long waitingQueueId = waitingQueueCommandService.createWaitingQueue(
        new CreateWaitingQueueCommand(concertId));

    return waitingQueueQueryService.getWaitingQueue(new GetWaitingQueueByIdQuery(waitingQueueId));
  }

  @Transactional(readOnly = true)
  public WaitingQueueWithPosition getWaitingQueueWithPosition(String waitingQueueUuid) {
    return waitingQueueQueryService.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuid(waitingQueueUuid));
  }

}
