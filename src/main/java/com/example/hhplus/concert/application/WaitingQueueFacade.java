package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetActiveTokenByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
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

  public String createWaitingQueueToken() {
    return waitingQueueCommandService.createWaitingQueue();
  }

  public WaitingQueueWithPosition getWaitingQueueWithPosition(String waitingQueueUuid) {
    return waitingQueueQueryService.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuid(waitingQueueUuid));
  }

  public void validateWaitingQueueProcessing(String waitingQueueUuid) {
    waitingQueueQueryService.getWaitingQueueProcessing(
        new GetActiveTokenByUuid(waitingQueueUuid));
  }

  public void activateWaitingQueues() {
    waitingQueueCommandService.activateWaitingQueues(
        new ActivateWaitingQueuesCommand(WaitingQueueConstants.ADD_PROCESSING_COUNT));
  }

}
