package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueCommandService;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueFacade {

  private final WaitingQueueQueryService waitingQueueQueryService;

  private final WaitingQueueCommandService waitingQueueCommandService;


  public WaitingQueue createWaitingQueueToken(Long concertId) {
    Long waitingQueueId = waitingQueueCommandService.createWaitingQueue(
        new CreateWaitingQueueCommand(concertId));

    return waitingQueueQueryService.getWaitingQueue(new GetWaitingQueueByIdQuery(waitingQueueId));
  }

}
