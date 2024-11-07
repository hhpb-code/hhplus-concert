package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.concert.service.ConcertQueryService;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueCommandService;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueFacade {

  private final WaitingQueueQueryService waitingQueueQueryService;

  private final WaitingQueueCommandService waitingQueueCommandService;

  private final ConcertQueryService concertQueryService;


  public WaitingQueue createWaitingQueueToken(Long concertId) {
    String uuid = waitingQueueCommandService.createWaitingQueue(
        new CreateWaitingQueueCommand(concertId));

    return waitingQueueQueryService.getWaitingQueue(new GetWaitingQueueByUuid(uuid));
  }

  public WaitingQueueWithPosition getWaitingQueueWithPosition(String waitingQueueUuid) {
    return waitingQueueQueryService.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuid(waitingQueueUuid));
  }

  public void validateWaitingQueueProcessing(String waitingQueueUuid) {
    WaitingQueue waitingQueue = waitingQueueQueryService.getWaitingQueue(
        new GetWaitingQueueByUuid(waitingQueueUuid));

    waitingQueue.validateProcessing();
  }

  public void activateWaitingQueues() {
    waitingQueueCommandService.activateWaitingQueues(
        new ActivateWaitingQueuesCommand(WaitingQueueConstants.ADD_PROCESSING_COUNT));
  }

}
